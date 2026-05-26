// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.migration;

import com.google.common.base.Stopwatch;
import jakarta.inject.Named;
import org.flywaydb.core.api.MigrationVersion;
import org.hiero.mirror.importer.ImporterProperties;
import org.hiero.mirror.importer.repository.AccountBalanceFileRepository;
import org.hiero.mirror.importer.repository.RecordFileRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.transaction.support.TransactionTemplate;

@Named
public class TokenAccountBalanceMigration extends AbstractTimestampInfoMigration {

    private static final String UPDATE_TOKEN_ACCOUNT_SQL = """
        with token_balance_snapshot as (
          select distinct on (account_id, token_id) *
          from token_balance
          where consensus_timestamp <= :snapshotTimestamp and consensus_timestamp > :snapshotTimestamp - 2592000000000000
          order by account_id, token_id, consensus_timestamp desc
        ),
        token_transfer as (
            select account_id, token_id, sum(amount) as amount
            from token_transfer tt
            where consensus_timestamp > :fromTimestamp and consensus_timestamp <= :toTimestamp
            group by account_id, token_id
        ),
        initial_balance as (
          select tb.account_id, coalesce(ta.associated, true) as associated, tb.token_id,
            case
                when ta.associated is false then 0
                else coalesce(tt.amount + tb.balance, tb.balance, 0)
            end as balance,
            :toTimestamp as balance_timestamp
          from token_balance_snapshot tb
          left join token_account ta on ta.account_id = tb.account_id and ta.token_id = tb.token_id
          left join token_transfer tt on tt.token_id = tb.token_id and tt.account_id = tb.account_id
        )
        insert into token_account (account_id, associated, balance, balance_timestamp, created_timestamp, timestamp_range, token_id)
        select account_id, associated, balance, balance_timestamp, 0, '[0,)', token_id
        from initial_balance
        on conflict (account_id, token_id) do update
        set balance = excluded.balance,
          balance_timestamp = excluded.balance_timestamp;
        """;

    public TokenAccountBalanceMigration(ObjectProvider<AccountBalanceFileRepository> accountBalanceFileRepositoryProvider, ImporterProperties importerProperties, ObjectProvider<NamedParameterJdbcOperations> jdbcOperationsProvider, ObjectProvider<RecordFileRepository> recordFileRepositoryProvider, ObjectProvider<TransactionTemplate> transactionTemplateProvider) {
        super(accountBalanceFileRepositoryProvider, importerProperties.getMigration(), jdbcOperationsProvider, recordFileRepositoryProvider, transactionTemplateProvider);
    }

    @Override
    public String getDescription() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected MigrationVersion getMinimumVersion() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void doMigrate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
