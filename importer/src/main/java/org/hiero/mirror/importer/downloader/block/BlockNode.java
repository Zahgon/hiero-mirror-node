// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.downloader.block;

import static com.hedera.hapi.block.stream.protoc.BlockItem.ItemCase.BLOCK_HEADER;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Range;
import com.hedera.hapi.block.stream.protoc.BlockItem;
import io.grpc.CallOptions;
import io.grpc.ManagedChannel;
import io.grpc.stub.BlockingClientCall;
import io.grpc.stub.ClientCalls;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import lombok.CustomLog;
import lombok.Getter;
import org.hiero.block.api.protoc.BlockEnd;
import org.hiero.block.api.protoc.BlockItemSet;
import org.hiero.block.api.protoc.BlockNodeServiceGrpc;
import org.hiero.block.api.protoc.BlockStreamSubscribeServiceGrpc;
import org.hiero.block.api.protoc.ServerStatusRequest;
import org.hiero.block.api.protoc.SubscribeStreamRequest;
import org.hiero.block.api.protoc.SubscribeStreamResponse;
import org.hiero.mirror.common.domain.StreamType;
import org.hiero.mirror.common.domain.transaction.BlockFile;
import org.hiero.mirror.importer.downloader.block.scheduler.Latency;
import org.hiero.mirror.importer.exception.BlockStreamException;
import org.hiero.mirror.importer.reader.block.BlockStream;
import org.hiero.mirror.importer.util.Utility;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@CustomLog
@NullMarked
public final class BlockNode implements AutoCloseable, Comparable<BlockNode> {

    public static final Comparator<BlockNode> LATENCY_COMPARATOR = Comparator.comparing(BlockNode::getLatency).thenComparing(b -> b.getProperties().getHost()).thenComparing(b -> b.getProperties().getPort()).thenComparing(b -> b.getProperties().isRequiresTls());

    static final String ERROR_METRIC_NAME = "hiero.mirror.importer.stream.error";

    private static final Comparator<BlockNode> COMPARATOR = Comparator.comparing(BlockNode::getProperties);

    private static final Range<Long> EMPTY_BLOCK_RANGE = Range.closedOpen(0L, 0L);

    private static final ServerStatusRequest SERVER_STATUS_REQUEST = ServerStatusRequest.getDefaultInstance();

    private final ManagedChannel channel;

    private final AtomicInteger errors = new AtomicInteger();

    private final Consumer<BlockingClientCall<?, ?>> grpcBufferDisposer;

    private final String name;

    @Getter
    private final Latency latency = new Latency();

    @Getter
    private final BlockNodeProperties properties;

    private final AtomicReference<Instant> readmitTime = new AtomicReference<>(Instant.now());

    private final StreamProperties streamProperties;

    private final Counter errorsMetric;

    @Getter
    private boolean active = true;

    public BlockNode(final ManagedChannelBuilderProvider channelBuilderProvider, final Consumer<BlockingClientCall<?, ?>> grpcBufferDisposer, final MeterRegistry meterRegistry, final BlockNodeProperties properties, final StreamProperties streamProperties) {
        final int maxInboundMessageSize = (int) streamProperties.getMaxStreamResponseSize().toBytes();
        this.channel = channelBuilderProvider.get(properties.getHost(), properties.getPort(), properties.isRequiresTls()).maxInboundMessageSize(maxInboundMessageSize).build();
        this.grpcBufferDisposer = grpcBufferDisposer;
        this.name = String.format("BlockNode(%s)", properties.getEndpoint());
        this.properties = properties;
        this.streamProperties = streamProperties;
        this.errorsMetric = Counter.builder(ERROR_METRIC_NAME).description("The number of errors that occurred while streaming from a particular block node.").tag("type", StreamType.BLOCK.toString()).tag("block_node", properties.getEndpoint()).register(meterRegistry);
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Range<Long> getBlockRange() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void streamBlocks(final long blockNumber, @Nullable final Long endBlockNumber, final BiFunction<BlockStream, String, Boolean> onBlockStream, final Duration timeout) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int compareTo(final BlockNode other) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public BlockNode tryReadmit(final boolean force) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * If number of failed connections surpass maxAttempts a readmit time(cooldown period)
     * is enforced before the specific node can be called again
     */
    private void onError() {
        errorsMetric.increment();
        if (errors.incrementAndGet() >= streamProperties.getMaxSubscribeAttempts()) {
            active = false;
            errors.set(0);
            readmitTime.set(Instant.now().plus(streamProperties.getReadmitDelay()));
            log.warn("Marking connection to {} as inactive after {} attempts", this, streamProperties.getMaxSubscribeAttempts());
        }
    }

    private final class BlockAssembler {

        private final BiFunction<BlockStream, String, Boolean> blockStreamConsumer;

        private final long endBlockNumber;

        private final List<List<BlockItem>> pending = new ArrayList<>();

        private final Stopwatch stopwatch;

        private final Duration timeout;

        private long loadStart;

        private int pendingCount = 0;

        BlockAssembler(final BiFunction<BlockStream, String, Boolean> blockStreamConsumer, final long endBlockNumber, final Duration timeout) {
            this.blockStreamConsumer = blockStreamConsumer;
            this.endBlockNumber = endBlockNumber;
            this.stopwatch = Stopwatch.createUnstarted();
            this.timeout = timeout;
        }

        void onBlockItemSet(final BlockItemSet blockItemSet) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        Boolean onEndOfBlock(final BlockEnd blockEnd) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        long timeout() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private void append(final List<BlockItem> blockItems, final BlockItem.ItemCase firstItemCase) {
            if (firstItemCase == BLOCK_HEADER && !pending.isEmpty()) {
                throw new BlockStreamException("Received block items of a new block while the previous block is still pending");
            } else if (firstItemCase != BLOCK_HEADER && pending.isEmpty()) {
                throw new BlockStreamException("Incorrect first block item case " + firstItemCase);
            }
            pending.add(blockItems);
            pendingCount += blockItems.size();
            if (pendingCount > streamProperties.getMaxBlockItems()) {
                throw new BlockStreamException(String.format("Too many block items in a pending block: received %d, limit %d", pendingCount, streamProperties.getMaxBlockItems()));
            }
        }
    }
}
