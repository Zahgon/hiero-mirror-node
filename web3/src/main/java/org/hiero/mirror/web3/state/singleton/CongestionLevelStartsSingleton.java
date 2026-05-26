// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.state.singleton;

import static com.hedera.node.app.throttle.schemas.V0490CongestionThrottleSchema.CONGESTION_LEVEL_STARTS_STATE_ID;
import com.hedera.hapi.node.state.congestion.CongestionLevelStarts;
import com.hedera.node.app.throttle.CongestionThrottleService;
import jakarta.inject.Named;

@Named
final class CongestionLevelStartsSingleton implements SingletonState<CongestionLevelStarts> {

    @Override
    public int getStateId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getServiceName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CongestionLevelStarts get() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
