// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.migration;

import com.google.common.base.Stopwatch;
import jakarta.inject.Named;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.api.MigrationVersion;
import org.hiero.mirror.common.converter.WeiBarTinyBarConverter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

@Named
@RequiredArgsConstructor()
public class ConvertEthereumTransactionValueMigration extends AbstractJavaMigration {

    private static final String SELECT_NON_NULL_VALUE_SQL = "select consensus_timestamp, value " + "from ethereum_transaction " + "where value is not null and length(value) > 0 " + "order by consensus_timestamp";

    private static final String SET_TINYBAR_VALUE_SQL = "update ethereum_transaction " + "set value = :value " + "where consensus_timestamp = :consensusTimestamp";

    private final ObjectProvider<NamedParameterJdbcOperations> jdbcOperationsProvider;

    @Override
    public String getDescription() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public MigrationVersion getVersion() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void doMigrate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
