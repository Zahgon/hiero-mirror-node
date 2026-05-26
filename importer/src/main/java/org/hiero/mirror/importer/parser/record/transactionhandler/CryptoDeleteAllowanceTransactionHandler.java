// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.transactionhandler;

import com.google.common.collect.Range;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.domain.token.Nft;
import org.hiero.mirror.common.domain.transaction.RecordItem;
import org.hiero.mirror.common.domain.transaction.Transaction;
import org.hiero.mirror.common.domain.transaction.TransactionType;
import org.hiero.mirror.importer.parser.contractlog.ApproveAllowanceIndexedContractLog;
import org.hiero.mirror.importer.parser.contractlog.SyntheticContractLogService;
import org.hiero.mirror.importer.parser.record.entity.EntityListener;

@Named
@RequiredArgsConstructor
class CryptoDeleteAllowanceTransactionHandler extends AbstractTransactionHandler {

    private final EntityListener entityListener;

    private final SyntheticContractLogService syntheticContractLogService;

    @Override
    public TransactionType getType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void doUpdateTransaction(Transaction transaction, RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
