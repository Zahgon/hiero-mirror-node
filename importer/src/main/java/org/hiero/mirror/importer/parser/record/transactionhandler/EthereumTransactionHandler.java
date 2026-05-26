// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.transactionhandler;

import com.hederahashgraph.api.proto.java.ResponseCodeEnum;
import jakarta.inject.Named;
import java.math.BigInteger;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.hiero.mirror.common.domain.contract.ContractResult;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.domain.transaction.EthereumTransaction;
import org.hiero.mirror.common.domain.transaction.RecordFile;
import org.hiero.mirror.common.domain.transaction.RecordItem;
import org.hiero.mirror.common.domain.transaction.Transaction;
import org.hiero.mirror.common.domain.transaction.TransactionType;
import org.hiero.mirror.common.util.DomainUtils;
import org.hiero.mirror.importer.parser.record.entity.EntityListener;
import org.hiero.mirror.importer.parser.record.entity.EntityProperties;
import org.hiero.mirror.importer.parser.record.ethereum.EthereumTransactionParser;
import org.hiero.mirror.importer.service.ContractBytecodeService;
import org.hiero.mirror.importer.util.Utility;

@Named
@RequiredArgsConstructor
final class EthereumTransactionHandler extends AbstractTransactionHandler {

    private final ContractBytecodeService contractBytecodeService;

    private final EntityListener entityListener;

    private final EntityProperties entityProperties;

    private final EthereumTransactionParser ethereumTransactionParser;

    /**
     * Attempts to extract the contract ID from the ethereumTransaction.
     *
     * @param recordItem to check
     * @return The contract ID associated with this ethereum transaction call
     */
    @Override
    public EntityId getEntity(RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public TransactionType getType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void updateContractResult(ContractResult contractResult, RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void doUpdateTransaction(Transaction transaction, RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void updateAccountNonce(RecordItem recordItem, EthereumTransaction ethereumTransaction) {
        if (!entityProperties.getPersist().isTrackNonce()) {
            return;
        }
        var transactionRecord = recordItem.getTransactionRecord();
        if (!transactionRecord.hasContractCallResult() && !transactionRecord.hasContractCreateResult()) {
            return;
        }
        var functionResult = transactionRecord.hasContractCreateResult() ? transactionRecord.getContractCreateResult() : transactionRecord.getContractCallResult();
        var senderId = EntityId.of(functionResult.getSenderId());
        if (EntityId.isEmpty(senderId)) {
            return;
        }
        Long nonce = null;
        if (functionResult.hasSignerNonce()) {
            nonce = functionResult.getSignerNonce().getValue();
        } else if (recordItem.getHapiVersion().isLessThan(RecordFile.HAPI_VERSION_0_47_0)) {
            var status = transactionRecord.getReceipt().getStatus();
            if (!recordItem.isSuccessful() && status != ResponseCodeEnum.CONTRACT_REVERT_EXECUTED && status != ResponseCodeEnum.MAX_CHILD_RECORDS_EXCEEDED) {
                return;
            }
            // Increment the nonce for backwards compatibility
            nonce = ethereumTransaction.getNonce() + 1;
        }
        if (nonce != null) {
            var entity = senderId.toEntity();
            entity.setEthereumNonce(nonce);
            // Don't trigger a history row
            entity.setTimestampRange(null);
            entityListener.onEntity(entity);
            recordItem.addEntityId(senderId);
        }
    }
}
