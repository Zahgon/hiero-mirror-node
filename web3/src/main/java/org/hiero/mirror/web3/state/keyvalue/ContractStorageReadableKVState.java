// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.state.keyvalue;

import static com.hedera.node.app.service.contract.impl.schemas.V0490ContractSchema.STORAGE_STATE_ID;
import static org.hiero.mirror.common.util.DomainUtils.leftPadBytes;
import com.hedera.hapi.node.state.contract.SlotKey;
import com.hedera.hapi.node.state.contract.SlotValue;
import com.hedera.node.app.service.contract.ContractService;
import com.hedera.pbj.runtime.io.buffer.Bytes;
import com.hedera.services.utils.EntityIdUtils;
import jakarta.inject.Named;
import org.apache.tuweni.bytes.Bytes32;
import org.hiero.mirror.web3.common.ContractCallContext;
import org.hiero.mirror.web3.service.ContractStateService;
import org.jspecify.annotations.NonNull;

@Named
final class ContractStorageReadableKVState extends AbstractReadableKVState<SlotKey, SlotValue> {

    public static final int STATE_ID = STORAGE_STATE_ID;

    private final ContractStateService contractStateService;

    protected ContractStorageReadableKVState(final ContractStateService contractStateService) {
        super(ContractService.NAME, STORAGE_STATE_ID);
        this.contractStateService = contractStateService;
    }

    @Override
    protected SlotValue readFromDataSource(@NonNull SlotKey slotKey) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getServiceName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
