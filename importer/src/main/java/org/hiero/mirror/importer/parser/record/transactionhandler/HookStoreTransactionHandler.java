// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.transactionhandler;

import jakarta.inject.Named;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.domain.transaction.RecordItem;
import org.hiero.mirror.common.domain.transaction.Transaction;
import org.hiero.mirror.common.domain.transaction.TransactionType;
import org.hiero.mirror.importer.domain.EntityIdService;
import org.jspecify.annotations.NullMarked;

@Named
@NullMarked
@RequiredArgsConstructor
@CustomLog
final class HookStoreTransactionHandler extends AbstractTransactionHandler {

    private final EvmHookStorageHandler hookHandler;

    private final EntityIdService entityIdService;

    @Override
    public TransactionType getType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public EntityId getEntity(RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void doUpdateTransaction(Transaction transaction, RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
