// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.transactionhandler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.common.domain.entity.Entity;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.domain.entity.EntityOperation;
import org.hiero.mirror.common.domain.transaction.RecordItem;
import org.hiero.mirror.common.domain.transaction.Transaction;
import org.hiero.mirror.common.domain.transaction.TransactionType;
import org.hiero.mirror.importer.domain.EntityIdService;
import org.hiero.mirror.importer.parser.record.entity.EntityListener;

@RequiredArgsConstructor
abstract class AbstractEntityCrudTransactionHandler extends AbstractTransactionHandler {

    protected final EntityIdService entityIdService;

    protected final EntityListener entityListener;

    @Getter
    private final TransactionType type;

    @Override
    protected final void updateEntity(Transaction transaction, RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected abstract void doUpdateEntity(Entity entity, RecordItem recordItem);
}
