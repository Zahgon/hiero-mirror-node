// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.transactionhandler;

import jakarta.inject.Named;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.domain.transaction.RecordItem;
import org.hiero.mirror.common.domain.transaction.TransactionType;

@Named
class CryptoDeleteLiveHashTransactionHandler extends AbstractTransactionHandler {

    @Override
    public TransactionType getType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @SuppressWarnings("deprecation")
    public EntityId getEntity(RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
