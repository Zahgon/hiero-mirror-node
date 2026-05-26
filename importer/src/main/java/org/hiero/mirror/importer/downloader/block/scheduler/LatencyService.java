// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.downloader.block.scheduler;

import jakarta.inject.Named;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import lombok.AccessLevel;
import lombok.CustomLog;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.importer.downloader.block.BlockNode;
import org.hiero.mirror.importer.downloader.block.cutover.CutoverService;
import org.hiero.mirror.importer.reader.block.BlockStream;
import org.hiero.mirror.importer.reader.block.BlockStreamReader;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * A latency service to measure a selection of block nodes' streaming latency in background. The latency measuring tasks
 * are scheduled in fixed delay, and limited to up to 1 + backlog at a time.
 */
@CustomLog
@Named
@RequiredArgsConstructor
public final class LatencyService implements AutoCloseable {

    private final BlockStreamReader blockStreamReader;

    private final CutoverService cutoverService;

    private final LatencyServiceProperties latencyServiceProperties;

    @Getter(lazy = true, value = AccessLevel.PRIVATE)
    private final ThreadPoolExecutor executor = createExecutor();

    private final AtomicLong generation = new AtomicLong();

    private final List<Future<?>> results = new CopyOnWriteArrayList<>();

    private final List<Task> tasks = new CopyOnWriteArrayList<>();

    @Override
    public void close() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void cancelAll() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set the block nodes to asynchronously measure latency for
     *
     * @param nodes - Block nodes
     */
    void setNodes(final Collection<BlockNode> nodes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Scheduled(fixedDelayString = "#{@latencyServiceProperties.getFrequency().toMillis()}")
    public void schedule() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private ThreadPoolExecutor createExecutor() {
        // a single-thread threadpool executor with a blocking queue to holding the backlog, to schedule 1 running
        // + backlog pending scheduled tasks
        return new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(latencyServiceProperties.getBacklog()));
    }

    @RequiredArgsConstructor
    private final class Task implements Runnable {

        private final long bornGeneration;

        private final BlockNode node;

        private long lastMeasuredBlockNumber = -1;

        private int skipped = 0;

        @Override
        public void run() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private boolean measureLatency(final BlockStream blockStream, final String blockNode) {
            final var blockFile = blockStreamReader.read(blockStream);
            node.getLatency().record(Utils.getLatency(blockFile, blockStream));
            return true;
        }
    }
}
