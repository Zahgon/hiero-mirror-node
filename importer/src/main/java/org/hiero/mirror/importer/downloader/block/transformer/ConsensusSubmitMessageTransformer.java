// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.downloader.block.transformer;

import static org.hiero.mirror.importer.util.Utility.DEFAULT_RUNNING_HASH_VERSION;
import jakarta.inject.Named;
import org.hiero.mirror.common.domain.transaction.TransactionType;
import org.hiero.mirror.common.util.DomainUtils;
import org.hiero.mirror.importer.util.Utility;

@Named
final class ConsensusSubmitMessageTransformer extends AbstractBlockTransactionTransformer {

    @Override
    protected void doTransform(BlockTransactionTransformation blockTransactionTransformation) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public TransactionType getType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
