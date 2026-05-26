// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.downloader.block.transformer;

import com.hederahashgraph.api.proto.java.ContractID;
import com.hederahashgraph.api.proto.java.TransactionReceipt;
import org.hiero.mirror.common.domain.transaction.StateChangeContext;
import org.hiero.mirror.common.util.DomainUtils;

abstract class AbstractContractTransformer extends AbstractBlockTransactionTransformer {

    void resolveEvmAddress(ContractID contractId, TransactionReceipt.Builder receiptBuilder, StateChangeContext stateChangeContext) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
