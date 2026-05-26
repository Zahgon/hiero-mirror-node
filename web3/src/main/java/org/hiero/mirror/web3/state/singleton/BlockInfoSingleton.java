// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.state.singleton;

import static com.hedera.node.app.records.schemas.V0490BlockRecordSchema.BLOCKS_STATE_ID;
import com.hedera.hapi.node.state.blockrecords.BlockInfo;
import com.hedera.node.app.records.BlockRecordService;
import com.hedera.pbj.runtime.io.buffer.Bytes;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.web3.common.ContractCallContext;
import org.hiero.mirror.web3.state.Utils;

@Named
@RequiredArgsConstructor
final class BlockInfoSingleton implements SingletonState<BlockInfo> {

    @Override
    public int getStateId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getServiceName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public BlockInfo get() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
