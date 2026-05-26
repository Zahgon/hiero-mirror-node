// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.state.singleton;

import static com.hedera.node.app.records.schemas.V0490BlockRecordSchema.RUNNING_HASHES_STATE_ID;
import com.hedera.hapi.node.state.blockrecords.RunningHashes;
import com.hedera.node.app.records.BlockRecordService;
import com.hedera.pbj.runtime.io.buffer.Bytes;
import jakarta.inject.Named;
import org.hiero.mirror.web3.common.ContractCallContext;

@Named
final class RunningHashesSingleton implements SingletonState<RunningHashes> {

    @Override
    public int getStateId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getServiceName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public RunningHashes get() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
