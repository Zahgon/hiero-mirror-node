// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.migration;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.hiero.mirror.common.domain.balance.AccountBalanceFile;
import org.hiero.mirror.common.domain.transaction.RecordFile;
import org.hiero.mirror.importer.exception.ImporterException;
import org.hiero.mirror.importer.parser.balance.BalanceStreamFileListener;
import org.hiero.mirror.importer.repository.AccountBalanceFileRepository;
import org.hiero.mirror.importer.repository.RecordFileRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

abstract class TimeSensitiveBalanceMigration extends RepeatableMigration implements BalanceStreamFileListener, TransactionSynchronization {

    private static final long EXECUTED = -1L;

    private static final long NO_BALANCE_FILE = 0L;

    private final ObjectProvider<AccountBalanceFileRepository> accountBalanceFileRepositoryProvider;

    private final ObjectProvider<RecordFileRepository> recordFileRepositoryProvider;

    private final AtomicLong firstConsensusTimestamp = new AtomicLong(NO_BALANCE_FILE);

    protected TimeSensitiveBalanceMigration(Map<String, MigrationProperties> migrationPropertiesMap, ObjectProvider<AccountBalanceFileRepository> accountBalanceFileRepositoryProvider, ObjectProvider<RecordFileRepository> recordFileRepositoryProvider) {
        super(migrationPropertiesMap);
        this.accountBalanceFileRepositoryProvider = accountBalanceFileRepositoryProvider;
        this.recordFileRepositoryProvider = recordFileRepositoryProvider;
    }

    @Override
    public void onEnd(AccountBalanceFile accountBalanceFile) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void afterCommit() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
