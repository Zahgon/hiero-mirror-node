// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.migration;

import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.Data;
import lombok.Getter;
import org.flywaydb.core.api.MigrationVersion;
import org.hiero.mirror.importer.ImporterProperties;
import org.hiero.mirror.importer.config.Owner;
import org.hiero.mirror.importer.db.DBProperties;
import org.hiero.mirror.importer.parser.record.ethereum.EthereumTransactionParser;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.transaction.support.TransactionTemplate;

@Named
final class ConvertEthereumTransactionToWeiBarMigration extends AsyncJavaMigration<Long> {

    private static final String CREATE_PROGRESS_TABLE_SQL = """
        create table if not exists convert_ethereum_transaction_weibar_progress (
          last_consensus_timestamp bigint not null
        )
        """;

    private static final String DROP_PROGRESS_TABLE_SQL = "drop table if exists convert_ethereum_transaction_weibar_progress";

    private static final String INSERT_PROGRESS_SQL = "insert into convert_ethereum_transaction_weibar_progress (last_consensus_timestamp) values (?)";

    private static final String SELECT_PROGRESS_SQL = "select min(last_consensus_timestamp) from convert_ethereum_transaction_weibar_progress";

    private static final String SELECT_TRANSACTIONS_SQL = """
        select consensus_timestamp, data, payer_account_id
        from ethereum_transaction
        where consensus_timestamp < :consensusTimestamp
        order by consensus_timestamp desc
        limit 5000
        """;

    private static final BeanPropertyRowMapper<TransactionRow> TRANSACTION_ROW_MAPPER = BeanPropertyRowMapper.newInstance(TransactionRow.class);

    private static final String UPDATE_SQL = "update ethereum_transaction set gas_price = ?, " + "max_fee_per_gas = ?, max_priority_fee_per_gas = ?, " + "value = ? where consensus_timestamp = ? and payer_account_id = ?";

    private static final Map<Boolean, MigrationVersion> MINIMUM_VERSION = Map.of(Boolean.FALSE, MigrationVersion.fromVersion("1.119.0"), Boolean.TRUE, MigrationVersion.fromVersion("2.24.0"));

    private final ObjectProvider<EthereumTransactionParser> ethereumTransactionParserProvider;

    private final boolean v2;

    private volatile long initialConsensusTimestamp = Long.MAX_VALUE;

    @Getter(lazy = true)
    private final TransactionOperations transactionOperations = transactionOperations();

    ConvertEthereumTransactionToWeiBarMigration(DBProperties dbProperties, Environment environment, ImporterProperties importerProperties, ObjectProvider<EthereumTransactionParser> ethereumTransactionParserProvider, @Owner ObjectProvider<JdbcOperations> ownerJdbcOperationsProvider) {
        super(importerProperties.getMigration(), ownerJdbcOperationsProvider, dbProperties.getSchema());
        this.v2 = environment.acceptsProfiles(Profiles.of("v2"));
        this.ethereumTransactionParserProvider = ethereumTransactionParserProvider;
    }

    @Override
    public String getDescription() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @NonNull
    @Override
    protected Long getInitial() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected MigrationVersion getMinimumVersion() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private TransactionOperations transactionOperations() {
        var jdbcTemplate = (JdbcTemplate) getJdbcOperations();
        var transactionManager = new DataSourceTransactionManager(Objects.requireNonNull(jdbcTemplate.getDataSource()));
        return new TransactionTemplate(transactionManager);
    }

    @Override
    protected boolean performSynchronousSteps() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @NonNull
    @Override
    protected Optional<Long> migratePartial(Long lastConsensusTimestamp) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void batchUpdate(List<TransactionUpdate> updates) {
        getJdbcOperations().batchUpdate(UPDATE_SQL, updates, updates.size(), (ps, update) -> {
            ps.setBytes(1, update.gasPrice());
            ps.setBytes(2, update.maxFeePerGas());
            ps.setBytes(3, update.maxPriorityFeePerGas());
            ps.setBytes(4, update.value());
            ps.setLong(5, update.consensusTimestamp());
            ps.setLong(6, update.payerAccountId());
        });
    }

    @Data
    private static class TransactionRow {

        private long consensusTimestamp;

        private byte[] data;

        private long payerAccountId;
    }

    private record TransactionUpdate(long consensusTimestamp, long payerAccountId, byte[] gasPrice, byte[] maxFeePerGas, byte[] maxPriorityFeePerGas, byte[] value) {
    }
}
