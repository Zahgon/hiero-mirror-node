// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.state;

import com.hedera.hapi.node.base.AccountID;
import com.hedera.hapi.node.state.primitives.ProtoBytes;
import com.hedera.hapi.node.state.token.Account;
import com.hedera.pbj.runtime.io.buffer.Bytes;
import jakarta.inject.Named;
import java.util.Map;
import org.hiero.mirror.web3.common.ContractCallContext;
import org.hiero.mirror.web3.state.keyvalue.AccountReadableKVState;
import org.hiero.mirror.web3.state.keyvalue.AliasesReadableKVState;

@Named
public class AliasedAccountCacheManager {

    public void putAccountAlias(final Bytes accountAlias, final AccountID accountID) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void putAccountNum(final AccountID accountID, final Account account) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private Map<Object, Object> getReadCache(final int readStateId) {
        return ContractCallContext.get().getReadCacheState(readStateId);
    }
}
