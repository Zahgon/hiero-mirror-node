// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.transactionhandler;

import com.hederahashgraph.api.proto.java.HookCall;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import org.hiero.mirror.common.domain.hook.AbstractHook;
import org.jspecify.annotations.NullMarked;

/**
 * Collects hook IDs for different execution phases and provides methods to build the final execution queue.
 * <p>
 * This record encapsulates the three types of hook executions as specified in HIP-1195: 1. allowExecHookIds - PreTx
 * hooks (HBAR, Token, NFT transfers) 2. allowPreExecHookIds - Pre hooks from PrePostTx (HBAR, Token, NFT transfers) 3.
 * allowPostExecHookIds - Post hooks from PrePostTx (HBAR, Token, NFT transfers)
 * <p>
 * The execution order is: allowExecHookIds → allowPreExecHookIds → allowPostExecHookIds
 */
@NullMarked
record HookExecutionCollector(List<AbstractHook.Id> allowExecHookIds, List<AbstractHook.Id> allowPreExecHookIds, List<AbstractHook.Id> allowPostExecHookIds) {

    /**
     * Creates a new HookExecutionCollector with empty lists.
     */
    static HookExecutionCollector create() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Adds a hook ID to the allowExecHookIds list (PreTx hooks).
     *
     * @param hookCall the hook call to add
     * @param ownerId  the owner ID for the hook
     */
    void addAllowExecHook(HookCall hookCall, long ownerId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Adds a hook ID to both allowPreExecHookIds and allowPostExecHookIds lists (PrePostTx hooks).
     *
     * @param hookCall the hook to add
     * @param ownerId  the owner ID for the hook
     */
    void addPrePostExecHook(HookCall hookCall, long ownerId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Builds the final hook execution queue in the correct order: allowExecHookIds → allowPreExecHookIds →
     * allowPostExecHookIds
     *
     * @return ArrayDeque containing all hook IDs in execution order
     */
    ArrayDeque<AbstractHook.Id> buildExecutionQueue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
