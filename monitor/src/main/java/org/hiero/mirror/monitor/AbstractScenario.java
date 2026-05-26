// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.monitor;

import com.google.common.base.Stopwatch;
import com.google.common.base.Throwables;
import com.google.common.collect.ConcurrentHashMultiset;
import com.google.common.collect.Multiset;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.step.StepLong;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.math3.util.Precision;
import org.hiero.mirror.monitor.subscribe.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class AbstractScenario<P extends ScenarioProperties, T> implements Scenario<P, T> {

    // 20s measured in milliseconds
    private static final long UPDATE_INTERVAL = 20_000L;

    @EqualsAndHashCode.Include
    protected final int id;

    @EqualsAndHashCode.Include
    protected final P properties;

    protected final AtomicLong counter = new AtomicLong(0L);

    protected final Multiset<String> errors = ConcurrentHashMultiset.create();

    protected final StepLong intervalCounter = new StepLong(Clock.SYSTEM, UPDATE_INTERVAL);

    protected final AtomicReference<T> last = new AtomicReference<>();

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected final Stopwatch stopwatch = Stopwatch.createStarted();

    @Override
    public long getCount() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Duration getElapsed() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Map<String, Integer> getErrors() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Optional<T> getLast() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public double getRate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public ScenarioStatus getStatus() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isRunning() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onComplete() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onError(Throwable throwable) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onNext(T response) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
