// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.monitor.publish;

import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.hiero.mirror.monitor.AbstractScenario;
import org.hiero.mirror.monitor.ScenarioProtocol;

@SuppressWarnings("java:S2160")
public class PublishScenario extends AbstractScenario<PublishScenarioProperties, PublishResponse> {

    private final String memo;

    public PublishScenario(PublishScenarioProperties properties) {
        super(1, properties);
        String hostname = Objects.requireNonNullElse(System.getenv("HOSTNAME"), "unknown");
        this.memo = String.format("Monitor %s on %s", properties.getName(), hostname);
    }

    public String getMemo() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public ScenarioProtocol getProtocol() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onError(Throwable throwable) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
