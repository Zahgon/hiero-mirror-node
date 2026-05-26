// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.downloader.block;

import jakarta.inject.Named;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import lombok.CustomLog;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.common.domain.StreamType;
import org.hiero.mirror.common.domain.transaction.BlockSourceType;
import org.hiero.mirror.importer.downloader.block.cutover.CutoverService;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;

@CustomLog
@Named
@Primary
final class CompositeBlockSource implements BlockSource {

    private final SourceHealth blockFileSourceHealth;

    private final BlockNodeDiscoveryService blockNodeDiscoveryService;

    private final SourceHealth blockNodeSubscriberSourceHealth;

    private final AtomicReference<SourceHealth> current;

    private final CutoverService cutoverService;

    private final BlockProperties properties;

    CompositeBlockSource(final BlockFileSource blockFileSource, final BlockNodeDiscoveryService blockNodeDiscoveryService, final BlockNodeSubscriber blockNodeSubscriber, final CutoverService cutoverService, final BlockProperties properties) {
        this.blockFileSourceHealth = new SourceHealth(blockFileSource, BlockSourceType.FILE);
        this.blockNodeDiscoveryService = blockNodeDiscoveryService;
        this.blockNodeSubscriberSourceHealth = new SourceHealth(blockNodeSubscriber, BlockSourceType.BLOCK_NODE);
        this.current = new AtomicReference<>(blockNodeSubscriberSourceHealth);
        this.cutoverService = cutoverService;
        this.properties = properties;
    }

    @Override
    @Scheduled(fixedDelayString = "#{@blockProperties.getFrequency().toMillis()}")
    public void get() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private SourceHealth getSourceHealth() {
        return switch(properties.getSourceType()) {
            case AUTO ->
                {
                    if (blockNodeDiscoveryService.getBlockNodes().isEmpty()) {
                        yield blockFileSourceHealth;
                    }
                    if (!current.get().isHealthy()) {
                        var sourceHealth = current.get() == blockNodeSubscriberSourceHealth ? blockFileSourceHealth : blockNodeSubscriberSourceHealth;
                        current.set(sourceHealth);
                    }
                    yield current.get();
                }
            case BLOCK_NODE ->
                blockNodeSubscriberSourceHealth;
            case FILE ->
                blockFileSourceHealth;
        };
    }

    @Getter
    @RequiredArgsConstructor
    private static class SourceHealth {

        private final AtomicInteger errors = new AtomicInteger();

        private final BlockSource source;

        private final BlockSourceType type;

        boolean isHealthy() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void onError() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void reset() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
