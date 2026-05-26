// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.monitor.subscribe.grpc;

import com.hedera.hashgraph.sdk.TopicId;
import com.hedera.hashgraph.sdk.TopicMessage;
import com.hedera.hashgraph.sdk.TopicMessageQuery;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import java.time.Instant;
import org.hiero.mirror.monitor.AbstractScenario;
import org.hiero.mirror.monitor.ScenarioProtocol;

class GrpcSubscription extends AbstractScenario<GrpcSubscriberProperties, TopicMessage> {

    GrpcSubscription(int id, GrpcSubscriberProperties properties) {
        super(id, properties);
    }

    @Override
    public ScenarioProtocol getProtocol() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    TopicMessageQuery getTopicMessageQuery() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onNext(TopicMessage topicResponse) {
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
