// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.state.singleton;

import static com.hedera.node.app.service.entityid.impl.schemas.V0490EntityIdSchema.ENTITY_ID_STATE_ID;
import com.hedera.hapi.node.state.common.EntityNumber;
import com.hedera.node.app.service.entityid.EntityIdService;
import com.hedera.node.config.data.HederaConfig;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.web3.common.ContractCallContext;
import org.hiero.mirror.web3.evm.properties.EvmProperties;
import org.hiero.mirror.web3.repository.EntityRepository;

@Named
@RequiredArgsConstructor
@SuppressWarnings("deprecation")
final class EntityIdSingleton implements SingletonState<EntityNumber> {

    private final EntityRepository entityRepository;

    private final EvmProperties evmProperties;

    @Override
    public int getStateId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getServiceName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public EntityNumber get() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
