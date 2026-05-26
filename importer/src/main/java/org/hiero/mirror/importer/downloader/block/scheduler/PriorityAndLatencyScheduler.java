// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.downloader.block.scheduler;

import io.micrometer.core.instrument.MeterRegistry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;
import lombok.Value;
import org.hiero.mirror.importer.downloader.block.BlockNode;
import org.hiero.mirror.importer.downloader.block.BlockNodeDiscoveryService;
import org.hiero.mirror.importer.downloader.block.ManagedChannelBuilderProvider;
import org.hiero.mirror.importer.downloader.block.StreamProperties;
import org.jspecify.annotations.Nullable;

final class PriorityAndLatencyScheduler extends AbstractLatencyAwareScheduler {

    private final AtomicReference<TreeMap<Integer, PriorityGroup>> priorityGroups = new AtomicReference<>(new TreeMap<>());

    PriorityAndLatencyScheduler(final BlockNodeDiscoveryService blockNodeDiscoveryService, final ManagedChannelBuilderProvider channelBuilderProvider, final LatencyService latencyService, final MeterRegistry meterRegistry, final SchedulerProperties schedulerProperties, final StreamProperties streamProperties) {
        super(blockNodeDiscoveryService, channelBuilderProvider, latencyService, meterRegistry, schedulerProperties, streamProperties);
    }

    @Override
    protected Iterator<BlockNode> getNodeGroupIterator() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected Iterator<BlockNode> getOrderedNodes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void setNodes(final List<BlockNode> blockNodes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Value
    private static class PriorityGroup {

        private final List<BlockNode> nodes;

        private final int priority;

        PriorityGroup(final int priority) {
            this.priority = priority;
            this.nodes = new ArrayList<>();
        }

        PriorityGroup sort() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        Iterator<BlockNode> getIterator() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
