// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.migration;

import jakarta.inject.Named;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.CustomLog;
import lombok.Getter;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.configuration.Configuration;
import org.hiero.mirror.importer.ImporterProperties;
import org.hiero.mirror.importer.config.Owner;
import org.hiero.mirror.importer.db.DBProperties;
import org.hiero.mirror.importer.parser.record.entity.EntityProperties;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.transaction.support.TransactionTemplate;

@CustomLog
@Named
@Profile("!v2")
public class FixCryptoAllowanceAmountMigration extends AsyncJavaMigration<Long> {

    // Process at most 1 day worth approved crypto transfers in each iteration
    static final long INTERVAL = Duration.ofDays(1).toNanos();

    private static final String DROP_MIGRATION_TABLE_SQL = "drop table if exists crypto_allowance_migration";

    private static final String GET_END_TIMESTAMP_SQL = """
        select (select lower(timestamp_range) from crypto_allowance_migration where owner = 0 and spender = 0)
        """;

    private static final String INIT_MIGRATION_TABLE_SQL = """
        begin;
        
        create table crypto_allowance_migration (like crypto_allowance, primary key (owner, spender));
        
        -- Run the following two inserts in the same statement so data in record_file table and crypto_allowance
        -- table is consistent
        with sentinel as (
          insert into crypto_allowance_migration (amount, amount_granted, owner, payer_account_id, spender, timestamp_range)
          select 0, 0, 0, 0, 0, int8range(consensus_end, null)
          from record_file
          order by consensus_end desc
          limit 1
        )
        insert into crypto_allowance_migration (amount, amount_granted, owner, payer_account_id, spender, timestamp_range)
        select
          amount_granted - amount, -- the spent amount tracked by importer as of consensus_end
          amount_granted,
          owner,
          payer_account_id,
          spender,
          timestamp_range
        from crypto_allowance
        -- don't need to fix either allowance with 0 remaining amount or allowance revoke
        where amount <> 0 and amount_granted <> 0;
        
        commit;
        """;

    private static final String MERGE_AMOUNT_SQL = """
        lock table crypto_allowance in access exclusive mode;
        lock table crypto_allowance_history in access exclusive mode;
        
        update crypto_allowance ca
        set amount = greatest(ca.amount + m.amount, 0)
        from crypto_allowance_migration m
        where m.owner = ca.owner and m.spender = ca.spender and lower(m.timestamp_range) = lower(ca.timestamp_range);
        
        update crypto_allowance_history h
        set amount = greatest(h.amount + m.amount, 0)
        from crypto_allowance_migration m
        where m.owner = h.owner and m.spender = h.spender and lower(m.timestamp_range) = lower(h.timestamp_range);
        """;

    private static final String UPDATE_MIGRATION_AMOUNT_SQL = """
        with approved_debit as (
          select sum(t.amount) as amount_spent, entity_id as owner, t.payer_account_id as spender
          from crypto_transfer t
          join crypto_allowance_migration m
          -- join and make sure don't include approve transfers not using this allowance
            on m.owner = t.entity_id and m.spender = t.payer_account_id and lower(m.timestamp_range) < t.consensus_timestamp
          where t.amount < 0 and consensus_timestamp > :fromTimestamp and consensus_timestamp <= :toTimestamp
            and is_approval is true
          group by t.entity_id, t.payer_account_id
        )
        update crypto_allowance_migration m
        set amount = amount + amount_spent
        from approved_debit a
        where m.owner = a.owner and m.spender = a.spender
        """;

    private static final String UPDATE_SENTINEL_TIMESTAMP_SQL = """
        update crypto_allowance_migration
        set timestamp_range = int8range(:timestamp, null)
        where owner = 0 and spender = 0;
        """;

    @Getter(lazy = true)
    private final long earliestTimestamp = earliestTimestamp();

    private final EntityProperties entityProperties;

    @Getter(lazy = true)
    private final TransactionOperations transactionOperations = transactionOperations();

    public FixCryptoAllowanceAmountMigration(DBProperties dbProperties, EntityProperties entityProperties, ImporterProperties importerProperties, @Owner ObjectProvider<JdbcOperations> jdbcOperationsProvider) {
        super(importerProperties.getMigration(), jdbcOperationsProvider, dbProperties.getSchema());
        this.entityProperties = entityProperties;
    }

    @Override
    protected Long getInitial() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getDescription() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected MigrationVersion getMinimumVersion() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @NonNull
    @Override
    protected Optional<Long> migratePartial(Long last) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected boolean skipMigration(Configuration configuration) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private long earliestTimestamp() {
        try {
            // It's safe to use the earliest timestamp as the exclusive min timestamp, because it's impossible to grant
            // and spend the allowance at the same time
            Long timestamp = getJdbcOperations().queryForObject("select min(lower(timestamp_range)) from crypto_allowance_migration", Long.class);
            if (timestamp == null) {
                log.warn("The table crypto_allowance_migration is empty, use Long.MAX_VALUE as the earliest timestamp");
                timestamp = Long.MAX_VALUE;
            }
            log.info("The earliest timestamp to back fill crypto allowance amount is {}", timestamp);
            return timestamp;
        } catch (DataAccessException ex) {
            log.warn("Unable to get earliest timestamp from table crypto_allowance_migration, use Long.MAX_VALUE", ex);
            return Long.MAX_VALUE;
        }
    }

    private boolean isMigrationTableExists() {
        try {
            getJdbcOperations().execute("select 'crypto_allowance_migration'::regclass");
            return true;
        } catch (DataAccessException ex) {
            log.warn("Table crypto_allowance_migration doesn't exist");
            return false;
        }
    }

    private TransactionOperations transactionOperations() {
        var jdbcTemplate = (JdbcTemplate) getJdbcOperations();
        var transactionManager = new DataSourceTransactionManager(Objects.requireNonNull(jdbcTemplate.getDataSource()));
        return new TransactionTemplate(transactionManager);
    }
}
