// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.retention;

import com.google.common.base.Stopwatch;
import jakarta.inject.Named;
import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import lombok.CustomLog;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.Strings;
import org.hiero.mirror.common.domain.transaction.RecordFile;
import org.hiero.mirror.importer.repository.RecordFileRepository;
import org.hiero.mirror.importer.repository.RetentionRepository;
import org.hiero.mirror.importer.util.Utility;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.support.TransactionOperations;

@CustomLog
@Named
@RequiredArgsConstructor
public class RetentionJob {

    private final RecordFileRepository recordFileRepository;

    private final RetentionProperties retentionProperties;

    private final Collection<RetentionRepository> retentionRepositories;

    private final TransactionOperations transactionOperations;

    @Scheduled(fixedDelayString = "#{@retentionProperties.getFrequency().toMillis()}", initialDelay = 120_000)
    public synchronized void prune() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void prune(RecordFileIterator iterator) {
        var counters = iterator.getCounters();
        long countBefore = counters.values().stream().reduce(0L, Long::sum);
        var stopwatch = iterator.getStopwatch();
        var next = iterator.next();
        long endTimestamp = next.getConsensusEnd();
        transactionOperations.executeWithoutResult(t -> retentionRepositories.forEach(repository -> {
            String table = getTableName(repository);
            if (retentionProperties.shouldPrune(table)) {
                long count = repository.prune(endTimestamp);
                counters.merge(table, count, Long::sum);
            }
        }));
        long countAfter = counters.values().stream().reduce(0L, Long::sum);
        long count = countAfter - countBefore;
        long elapsed = stopwatch.elapsed(TimeUnit.SECONDS);
        long rate = elapsed > 0 ? countAfter / elapsed : 0L;
        log.info("Pruned {} entries on or before {} in {} at {}/s", count, toInstant(endTimestamp), stopwatch, rate);
    }

    private String getTableName(RetentionRepository repository) {
        Class<?> targetClass = repository.getClass().getInterfaces()[0];
        String className = ClassUtils.getSimpleName(targetClass);
        return Utility.toSnakeCase(Strings.CS.removeEnd(className, "Repository"));
    }

    private Instant toInstant(long nanos) {
        return Instant.ofEpochSecond(0L, nanos);
    }

    @Data
    private class RecordFileIterator implements Iterator<RecordFile> {

        private final Map<String, Long> counters = new TreeMap<>();

        private final RecordFile max;

        private final Stopwatch stopwatch = Stopwatch.createStarted();

        private RecordFile current;

        public boolean hasNext() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public RecordFile next() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
