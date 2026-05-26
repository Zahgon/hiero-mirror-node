// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.monitor.subscribe.rest;

import lombok.Getter;
import lombok.Value;
import org.hiero.mirror.monitor.AbstractScenario;
import org.hiero.mirror.monitor.ScenarioProtocol;
import org.hiero.mirror.monitor.publish.PublishResponse;
import org.hiero.mirror.rest.model.TransactionByIdResponse;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.Exceptions;
import reactor.core.publisher.Sinks;

@Getter
@Value
class RestSubscription extends AbstractScenario<RestSubscriberProperties, TransactionByIdResponse> {

    private final Sinks.Many<PublishResponse> sink;

    RestSubscription(int id, RestSubscriberProperties properties) {
        super(id, properties);
        sink = Sinks.many().multicast().directBestEffort();
    }

    @Override
    public ScenarioProtocol getProtocol() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onError(Throwable t) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
