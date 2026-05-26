// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.monitor.subscribe;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.TimeGauge;
import io.micrometer.core.instrument.Timer;
import jakarta.inject.Named;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.monitor.converter.DurationToStringSerializer;
import org.springframework.scheduling.annotation.Scheduled;

@CustomLog
@Named
@RequiredArgsConstructor
public class SubscribeMetrics {

    static final String METRIC_DURATION = "hiero.mirror.monitor.subscribe.duration";

    static final String METRIC_E2E = "hiero.mirror.monitor.subscribe.e2e";

    static final String TAG_PROTOCOL = "protocol";

    static final String TAG_SCENARIO = "scenario";

    static final String TAG_SUBSCRIBER = "subscriber";

    private final Map<Scenario<?, ?>, TimeGauge> durationMetrics = new ConcurrentHashMap<>();

    private final Map<Scenario<?, ?>, Timer> latencyMetrics = new ConcurrentHashMap<>();

    private final MeterRegistry meterRegistry;

    private final SubscribeProperties subscribeProperties;

    public void onNext(SubscribeResponse response) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private TimeGauge newDurationGauge(Scenario<?, ?> scenario) {
        return TimeGauge.builder(METRIC_DURATION, scenario, TimeUnit.NANOSECONDS, s -> s.getElapsed().toNanos()).description("How long the subscriber has been running").tag(TAG_PROTOCOL, scenario.getProtocol().toString()).tag(TAG_SCENARIO, scenario.getName()).tag(TAG_SUBSCRIBER, String.valueOf(scenario.getId())).register(meterRegistry);
    }

    private Timer newLatencyTimer(Scenario<?, ?> scenario) {
        return Timer.builder(METRIC_E2E).description("The end to end transaction latency starting from publish and ending at receive").tag(TAG_PROTOCOL, scenario.getProtocol().toString()).tag(TAG_SCENARIO, scenario.getName()).tag(TAG_SUBSCRIBER, String.valueOf(scenario.getId())).register(meterRegistry);
    }

    @Scheduled(fixedDelayString = "${hiero.mirror.monitor.subscribe.statusFrequency:10000}")
    // Call to peek here is fine
    @SuppressWarnings("java:S3864")
    public void status() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void status(Scenario<?, ?> s) {
        String elapsed = DurationToStringSerializer.convert(s.getElapsed());
        log.info("{} scenario {} received {} responses in {} at {}/s. Errors: {}", s.getProtocol(), s, s.getCount(), elapsed, s.getRate(), s.getErrors());
    }
}
