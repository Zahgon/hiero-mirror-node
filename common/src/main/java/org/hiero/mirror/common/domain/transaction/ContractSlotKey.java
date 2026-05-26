// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.common.domain.transaction;

import com.google.protobuf.ByteString;
import com.hederahashgraph.api.proto.java.ContractID;
import com.hederahashgraph.api.proto.java.HookId;
import org.jspecify.annotations.Nullable;

/**
 * Unified key for both contract storage and hook storage. For regular contract storage: contractId is set, hookId is
 * null. For hook storage: contractId is null, hookId is set
 */
public record ContractSlotKey(ContractSlotId slotId, ByteString key) {

    /**
     * Helper method to get contractId from slotId (may be null for hook storage)
     */
    @Nullable
    public ContractID contractId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Helper method to get hookId from slotId (may be null for contract storage)
     */
    @Nullable
    public HookId hookId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
