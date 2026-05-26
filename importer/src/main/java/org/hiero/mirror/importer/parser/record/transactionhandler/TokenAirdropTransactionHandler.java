// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.transactionhandler;

import com.google.common.collect.Range;
import com.hederahashgraph.api.proto.java.TokenID;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.domain.token.TokenAirdrop;
import org.hiero.mirror.common.domain.token.TokenAirdropStateEnum;
import org.hiero.mirror.common.domain.transaction.RecordItem;
import org.hiero.mirror.common.domain.transaction.Transaction;
import org.hiero.mirror.common.domain.transaction.TransactionType;
import org.hiero.mirror.importer.parser.record.entity.EntityListener;
import org.hiero.mirror.importer.parser.record.entity.EntityProperties;

@Named
@RequiredArgsConstructor
class TokenAirdropTransactionHandler extends AbstractTransactionHandler {

    private final EntityListener entityListener;

    private final EntityProperties entityProperties;

    @Override
    protected void doUpdateTransaction(Transaction transaction, RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public TransactionType getType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
