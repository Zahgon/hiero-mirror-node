// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.downloader.block.scheduler;

import io.micrometer.core.instrument.MeterRegistry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.hiero.mirror.common.domain.transaction.BlockFile;
import org.hiero.mirror.importer.downloader.block.BlockNode;
import org.hiero.mirror.importer.downloader.block.BlockNodeDiscoveryService;
import org.hiero.mirror.importer.downloader.block.ManagedChannelBuilderProvider;
import org.hiero.mirror.importer.downloader.block.StreamProperties;
import org.hiero.mirror.importer.exception.BlockStreamException;
import org.hiero.mirror.importer.reader.block.BlockStream;
import org.jspecify.annotations.Nullable;

abstract class AbstractLatencyAwareScheduler extends AbstractScheduler {

    protected final LatencyService latencyService;

    protected final SchedulerProperties schedulerProperties;

    protected final List<BlockNode> candidates = new CopyOnWriteArrayList<>();

    protected final AtomicReference<@Nullable BlockNode> current = new AtomicReference<>();

    protected final AtomicLong lastScheduledTime = new AtomicLong(0);

    private volatile long lastPostProcessingLatency;

    AbstractLatencyAwareScheduler(final BlockNodeDiscoveryService blockNodeDiscoveryService, final ManagedChannelBuilderProvider channelBuilderProvider, final LatencyService latencyService, final MeterRegistry meterRegistry, final SchedulerProperties schedulerProperties, final StreamProperties streamProperties) {
        super(blockNodeDiscoveryService, channelBuilderProvider, meterRegistry, streamProperties);
        this.latencyService = latencyService;
        this.schedulerProperties = schedulerProperties;
    }

    @Override
    public ScheduledBlockNode getNode(final long blockNumber) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean shouldReschedule(final BlockFile blockFile, final BlockStream blockStream) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected abstract Iterator<BlockNode> getNodeGroupIterator();

    private Collection<BlockNode> getCandidates() {
        final var iter = getNodeGroupIterator();
        final var active = current.get();
        final var candidates = new ArrayList<BlockNode>();
        while (iter.hasNext()) {
            var node = iter.next();
            if (node == active) {
                continue;
            }
            candidates.add(node);
        }
        return Collections.unmodifiableCollection(candidates);
    }
}
