// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Stopwatch;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.hiero.mirror.common.domain.StreamFile;
import org.hiero.mirror.importer.exception.HashMismatchException;
import org.hiero.mirror.importer.repository.StreamFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public abstract class AbstractStreamFileParser<T extends StreamFile<?>> implements StreamFileParser<T> {

    public static final String STREAM_PARSE_DURATION_METRIC_NAME = "hiero.mirror.importer.parse.duration";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected final MeterRegistry meterRegistry;

    protected final ParserProperties parserProperties;

    protected final StreamFileListener<T> streamFileListener;

    protected final StreamFileRepository<T, Long> streamFileRepository;

    private final AtomicReference<T> last;

    private final Timer parseDurationMetricFailure;

    private final Timer parseDurationMetricSuccess;

    private final Timer parseLatencyMetric;

    private final Timer totalDurationMetric;

    protected AbstractStreamFileParser(MeterRegistry meterRegistry, ParserProperties parserProperties, StreamFileListener<T> streamFileListener, StreamFileRepository<T, Long> streamFileRepository) {
        this.last = new AtomicReference<>();
        this.meterRegistry = meterRegistry;
        this.parserProperties = parserProperties;
        this.streamFileListener = streamFileListener;
        this.streamFileRepository = streamFileRepository;
        // Metrics
        Timer.Builder parseDurationTimerBuilder = Timer.builder(STREAM_PARSE_DURATION_METRIC_NAME).description("The duration in seconds it took to parse the file and store it in the database").tag("type", parserProperties.getStreamType().toString());
        parseDurationMetricFailure = parseDurationTimerBuilder.tag("success", "false").register(meterRegistry);
        parseDurationMetricSuccess = parseDurationTimerBuilder.tag("success", "true").register(meterRegistry);
        parseLatencyMetric = Timer.builder("hiero.mirror.importer.parse.latency").description("The difference in ms between the consensus time of the last transaction in the file " + "and the time at which the file was processed successfully").tag("type", parserProperties.getStreamType().toString()).register(meterRegistry);
        this.totalDurationMetric = Timer.builder("hiero.mirror.importer.duration").description("The total amount of time the importer took to download and ingest a stream file").tag("type", parserProperties.getStreamType().toString()).register(meterRegistry);
    }

    @VisibleForTesting
    public void clear() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public ParserProperties getProperties() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @SuppressWarnings("java:S2139")
    public void parse(T streamFile) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @SuppressWarnings("java:S2139")
    public void parse(List<T> streamFiles) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected abstract void doParse(T streamFile);

    protected final T getLast() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void doFlush(T streamFile) {
        streamFileListener.onEnd(streamFile);
        last.set(streamFile);
        streamFile.clear();
    }

    private boolean shouldParse(T previous, T current) {
        if (!parserProperties.isEnabled()) {
            return false;
        }
        if (previous == null) {
            return true;
        }
        var name = current.getName();
        if (previous.getConsensusEnd() >= current.getConsensusStart()) {
            log.warn("Skipping existing stream file {}", name);
            return false;
        }
        var actualHash = current.getPreviousHash();
        var expectedHash = previous.getHash();
        // Verify hash chain
        if (previous.getType().isChained() && !expectedHash.contentEquals(actualHash)) {
            throw new HashMismatchException(name, expectedHash, actualHash, getClass().getSimpleName());
        }
        return true;
    }
}
