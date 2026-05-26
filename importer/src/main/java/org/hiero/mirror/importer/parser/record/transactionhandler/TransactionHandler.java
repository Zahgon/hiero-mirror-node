// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.transactionhandler;

import com.hederahashgraph.api.proto.java.ContractFunctionResult;
import org.hiero.mirror.common.domain.contract.ContractResult;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.domain.transaction.RecordItem;
import org.hiero.mirror.common.domain.transaction.Transaction;
import org.hiero.mirror.common.domain.transaction.TransactionType;
import org.hiero.mirror.common.util.DomainUtils;

/**
 * TransactionHandler interface abstracts the logic for processing different kinds for transactions. For each
 * transaction type, there exists an unique implementation of TransactionHandler which encapsulates all logic specific
 * to processing of that transaction type. A single {@link com.hederahashgraph.api.proto.java.Transaction} and its
 * associated info (TransactionRecord, deserialized TransactionBody, etc) are all encapsulated together in a single
 * {@link RecordItem}. Hence, most functions of this interface require RecordItem as a parameter.
 */
public interface TransactionHandler {

    /**
     * @return main entity associated with this transaction
     */
    default EntityId getEntity(RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * @return the transaction type associated with this handler
     */
    TransactionType getType();

    /**
     * Update fields of the ContractResult's (domain) when the source can be in the transaction body and / or
     * in the ContractCallResult / ContractCreateResult in transaction record
     */
    default void updateContractResult(ContractResult contractResult, RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Override to update fields of the Transaction's (domain) fields.
     */
    default void updateTransaction(Transaction transaction, RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
