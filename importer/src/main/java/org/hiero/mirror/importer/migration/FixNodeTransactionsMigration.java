// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.migration;

import com.hederahashgraph.api.proto.java.TransactionRecord;
import io.hypersistence.utils.hibernate.type.range.guava.PostgreSQLGuavaRangeType;
import jakarta.inject.Named;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.SneakyThrows;
import org.flywaydb.core.api.MigrationVersion;
import org.hiero.mirror.common.domain.node.Node;
import org.hiero.mirror.common.domain.transaction.RecordItem;
import org.hiero.mirror.common.domain.transaction.TransactionType;
import org.hiero.mirror.importer.ImporterProperties;
import org.hiero.mirror.importer.config.Owner;
import org.hiero.mirror.importer.parser.record.transactionhandler.AbstractNodeTransactionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

@Named
public class FixNodeTransactionsMigration extends ConfigurableJavaMigration {

    // Earliest consensus timestamp to consider
    private static final long LOWER_TIMESTAMP = 1733961600000000000L;

    private static final String DROP_DATA_SQL = """
        truncate node;
        truncate node_history;
        """;

    private static final String NODE_TRANSACTIONS_SQL = """
        select transaction_bytes, transaction_record_bytes
        from transaction
        where consensus_timestamp >= ? and type in (54, 55, 56)
        order by consensus_timestamp asc;
        """;

    private static final String INSERT_SQL = """
        insert into %s (node_id, created_timestamp, deleted, admin_key, timestamp_range)
        values (?, ?, ?, ?, ?::int8range);
        """;

    private final ObjectProvider<JdbcOperations> jdbcOperationsProvider;

    private final ObjectProvider<AbstractNodeTransactionHandler> nodeTransactionHandlers;

    private final Map<TransactionType, AbstractNodeTransactionHandler> nodeTransactionHandlerMap = new EnumMap<>(TransactionType.class);

    private final boolean v2;

    FixNodeTransactionsMigration(Environment environment, ObjectProvider<AbstractNodeTransactionHandler> nodeTransactionHandlers, ImporterProperties importerProperties, @Owner ObjectProvider<JdbcOperations> jdbcOperationsProvider) {
        super(importerProperties.getMigration());
        this.v2 = environment.acceptsProfiles(Profiles.of("v2"));
        this.nodeTransactionHandlers = nodeTransactionHandlers;
        this.jdbcOperationsProvider = jdbcOperationsProvider;
    }

    @Override
    protected void doMigrate() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private Node recordItemToNode(RecordItem recordItem) {
        var type = TransactionType.of(recordItem.getTransactionType());
        var handler = nodeTransactionHandlerMap.computeIfAbsent(type, t -> nodeTransactionHandlers.stream().filter(h -> h.getType().equals(type)).findFirst().orElseThrow(() -> new IllegalArgumentException("No handler found for transaction type: " + t)));
        return handler.parseNode(recordItem);
    }

    @SneakyThrows
    private RecordItem toRecordItem(NodeTransaction transaction) {
        var protoTransaction = com.hederahashgraph.api.proto.java.Transaction.parseFrom(transaction.transactionBytes());
        var protoRecord = TransactionRecord.parseFrom(transaction.transactionRecordBytes());
        return RecordItem.builder().transaction(protoTransaction).transactionRecord(protoRecord).build();
    }

    @Override
    public MigrationVersion getVersion() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getDescription() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private List<RecordItem> getRecordItems() {
        return jdbcOperationsProvider.getObject().query(NODE_TRANSACTIONS_SQL, new DataClassRowMapper<>(NodeTransaction.class), LOWER_TIMESTAMP).stream().map(this::toRecordItem).toList();
    }

    private Node mergeNode(Node previous, Node current) {
        if (previous != null) {
            previous.setTimestampUpper(current.getTimestampLower());
            current.setCreatedTimestamp(previous.getCreatedTimestamp());
            if (current.getAdminKey() == null) {
                current.setAdminKey(previous.getAdminKey());
            }
        }
        return current;
    }

    private record NodeTransaction(byte[] transactionBytes, byte[] transactionRecordBytes) {
    }
}
