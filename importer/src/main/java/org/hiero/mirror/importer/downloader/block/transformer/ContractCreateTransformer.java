// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.downloader.block.transformer;

import com.hedera.hapi.block.stream.output.protoc.TransactionOutput;
import com.hedera.hapi.block.stream.output.protoc.TransactionOutput.TransactionCase;
import jakarta.inject.Named;
import org.hiero.mirror.common.domain.transaction.BlockTransaction;
import org.hiero.mirror.common.domain.transaction.TransactionType;

@Named
final class ContractCreateTransformer extends AbstractBlockTransactionTransformer {

    @Override
    public TransactionType getType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected EvmTransactionInfo getEvmTransactionInfo(BlockTransaction blockTransaction) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
