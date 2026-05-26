// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.transactionhandler;

import static org.hiero.mirror.common.util.DomainUtils.toBytes;
import com.google.common.collect.Range;
import jakarta.inject.Named;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.domain.token.Nft;
import org.hiero.mirror.common.domain.token.Token;
import org.hiero.mirror.common.domain.transaction.RecordItem;
import org.hiero.mirror.common.domain.transaction.Transaction;
import org.hiero.mirror.common.domain.transaction.TransactionType;
import org.hiero.mirror.importer.parser.record.entity.EntityListener;
import org.hiero.mirror.importer.parser.record.entity.EntityProperties;
import org.hiero.mirror.importer.util.Utility;

@CustomLog
@Named
@RequiredArgsConstructor
class TokenMintTransactionHandler extends AbstractTransactionHandler {

    private final EntityListener entityListener;

    private final EntityProperties entityProperties;

    @Override
    public EntityId getEntity(RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public TransactionType getType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void doUpdateTransaction(Transaction transaction, RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
