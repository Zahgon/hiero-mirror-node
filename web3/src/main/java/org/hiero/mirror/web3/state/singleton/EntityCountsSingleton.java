// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.state.singleton;

import static com.hedera.node.app.service.entityid.impl.schemas.V0590EntityIdSchema.ENTITY_COUNTS_STATE_ID;
import com.hedera.hapi.node.state.entity.EntityCounts;
import com.hedera.node.app.service.entityid.EntityIdService;
import jakarta.inject.Named;

@Named
final class EntityCountsSingleton implements SingletonState<EntityCounts> {

    @Override
    public int getStateId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getServiceName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public EntityCounts get() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
