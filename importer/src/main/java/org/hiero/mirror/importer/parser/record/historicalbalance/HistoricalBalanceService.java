// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.historicalbalance;

import static org.hiero.mirror.importer.parser.AbstractStreamFileParser.STREAM_PARSE_DURATION_METRIC_NAME;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Range;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.inject.Named;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.AccessLevel;
import lombok.CustomLog;
import lombok.Getter;
import org.hiero.mirror.common.domain.StreamType;
import org.hiero.mirror.common.domain.SystemEntity;
import org.hiero.mirror.common.domain.balance.AccountBalanceFile;
import org.hiero.mirror.common.domain.entity.EntityType;
import org.hiero.mirror.common.domain.transaction.RecordFile;
import org.hiero.mirror.importer.db.TimePartitionService;
import org.hiero.mirror.importer.domain.StreamFilename;
import org.hiero.mirror.importer.domain.StreamFilename.FileType;
import org.hiero.mirror.importer.exception.InvalidDatasetException;
import org.hiero.mirror.importer.exception.ParserException;
import org.hiero.mirror.importer.parser.record.RecordFileParsedEvent;
import org.hiero.mirror.importer.parser.record.RecordFileParser;
import org.hiero.mirror.importer.repository.AccountBalanceFileRepository;
import org.hiero.mirror.importer.repository.AccountBalanceRepository;
import org.hiero.mirror.importer.repository.EntityRepository;
import org.hiero.mirror.importer.repository.RecordFileRepository;
import org.hiero.mirror.importer.repository.TokenBalanceRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.TransactionTemplate;

@CustomLog
@Named
public class HistoricalBalanceService {

    private static final String ACCOUNT_BALANCE_TABLE_NAME = "account_balance";

    @Getter(AccessLevel.PACKAGE)
    private final AtomicBoolean treasuryExists = new AtomicBoolean(false);

    private final AccountBalanceFileRepository accountBalanceFileRepository;

    private final AccountBalanceRepository accountBalanceRepository;

    private final HistoricalBalanceProperties properties;

    private final RecordFileRepository recordFileRepository;

    private final AtomicBoolean running = new AtomicBoolean(false);

    private final SystemEntity systemEntity;

    private final TimePartitionService timePartitionService;

    private final TokenBalanceRepository tokenBalanceRepository;

    private final TransactionTemplate transactionTemplate;

    private final EntityRepository entityRepository;

    // metrics
    private final Timer generateDurationMetricFailure;

    private final Timer generateDurationMetricSuccess;

    @SuppressWarnings("java:S107")
    public HistoricalBalanceService(AccountBalanceFileRepository accountBalanceFileRepository, AccountBalanceRepository accountBalanceRepository, MeterRegistry meterRegistry, PlatformTransactionManager platformTransactionManager, HistoricalBalanceProperties properties, RecordFileRepository recordFileRepository, SystemEntity systemEntity, TimePartitionService timePartitionService, TokenBalanceRepository tokenBalanceRepository, EntityRepository entityRepository) {
        this.accountBalanceFileRepository = accountBalanceFileRepository;
        this.accountBalanceRepository = accountBalanceRepository;
        this.properties = properties;
        this.recordFileRepository = recordFileRepository;
        this.systemEntity = systemEntity;
        this.timePartitionService = timePartitionService;
        this.tokenBalanceRepository = tokenBalanceRepository;
        this.entityRepository = entityRepository;
        // Set repeatable read isolation level and transaction timeout
        this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
        this.transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        this.transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        this.transactionTemplate.setTimeout((int) properties.getTransactionTimeout().toSeconds());
        // metrics
        var timer = Timer.builder(STREAM_PARSE_DURATION_METRIC_NAME).tag("type", StreamType.BALANCE.toString());
        generateDurationMetricFailure = timer.tag("success", "false").register(meterRegistry);
        generateDurationMetricSuccess = timer.tag("success", "true").register(meterRegistry);
    }

    /**
     * Listens on {@link RecordFileParsedEvent} and generate historical balance at configured frequency.
     *
     * @param event The record file parsed event published by {@link RecordFileParser}
     */
    @Async
    @TransactionalEventListener
    public void onRecordFileParsed(RecordFileParsedEvent event) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private Optional<Long> getMaxConsensusTimestamp(long timestamp) {
        var partitions = timePartitionService.getOverlappingTimePartitions(ACCOUNT_BALANCE_TABLE_NAME, timestamp, timestamp);
        if (partitions.isEmpty()) {
            throw new InvalidDatasetException(String.format("No account_balance partition found for timestamp %s", timestamp));
        }
        long treasuryAccountId = systemEntity.treasuryAccount().getId();
        var partitionRange = partitions.getFirst().getTimestampRange();
        return accountBalanceRepository.getMaxConsensusTimestampInRange(partitionRange.lowerEndpoint(), partitionRange.upperEndpoint(), treasuryAccountId);
    }

    private boolean shouldGenerate(long consensusEnd) {
        return properties.isEnabled() && accountBalanceFileRepository.findLatest().map(AccountBalanceFile::getConsensusTimestamp).or(() -> recordFileRepository.findFirst().map(RecordFile::getConsensusEnd).map(timestamp -> timestamp + properties.getInitialDelay().toNanos())).filter(lastTimestamp -> consensusEnd - lastTimestamp >= properties.getMinFrequency().toNanos()).isPresent();
    }

    private void checkTreasuryAccount() {
        if (treasuryExists.compareAndSet(false, true)) {
            var treasuryAccountEntityId = systemEntity.treasuryAccount();
            try {
                if (!entityRepository.existsById(treasuryAccountEntityId.getId())) {
                    var savedTreasuryAccount = entityRepository.save(treasuryAccountEntityId.toEntity().toBuilder().balance(0L).createdTimestamp(0L).declineReward(true).deleted(false).memo("Mirror node created synthetic treasury account").type(EntityType.ACCOUNT).timestampRange(Range.atLeast(0L)).build());
                    log.info("Created sentinel treasury account: {}", savedTreasuryAccount);
                }
            } catch (Exception e) {
                log.error("Failed to auto create treasury account {}", treasuryAccountEntityId);
                treasuryExists.set(false);
                throw e;
            }
        }
    }
}
