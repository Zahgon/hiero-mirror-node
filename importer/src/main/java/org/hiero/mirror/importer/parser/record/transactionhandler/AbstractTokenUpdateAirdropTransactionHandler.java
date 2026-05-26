// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.transactionhandler;

import com.google.common.collect.Range;
import com.hederahashgraph.api.proto.java.PendingAirdropId;
import com.hederahashgraph.api.proto.java.TokenID;
import java.util.List;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.domain.token.TokenAccount;
import org.hiero.mirror.common.domain.token.TokenAirdrop;
import org.hiero.mirror.common.domain.token.TokenAirdropStateEnum;
import org.hiero.mirror.common.domain.transaction.RecordItem;
import org.hiero.mirror.common.domain.transaction.Transaction;
import org.hiero.mirror.common.domain.transaction.TransactionType;
import org.hiero.mirror.importer.domain.EntityIdService;
import org.hiero.mirror.importer.parser.record.entity.EntityListener;
import org.hiero.mirror.importer.parser.record.entity.EntityProperties;
import org.hiero.mirror.importer.util.Utility;

@RequiredArgsConstructor
abstract class AbstractTokenUpdateAirdropTransactionHandler extends AbstractTransactionHandler {

    private final EntityIdService entityIdService;

    private final EntityListener entityListener;

    private final EntityProperties entityProperties;

    private final Function<RecordItem, List<PendingAirdropId>> extractor;

    private final TokenAirdropStateEnum state;

    private final TransactionType type;

    @Override
    public TransactionType getType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void doUpdateTransaction(Transaction transaction, RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void associateTokenAccount(EntityId token, EntityId receiver, long consensusTimestamp) {
        var tokenAccount = new TokenAccount();
        tokenAccount.setAccountId(receiver.getId());
        tokenAccount.setAssociated(true);
        tokenAccount.setAutomaticAssociation(false);
        tokenAccount.setBalance(0L);
        tokenAccount.setBalanceTimestamp(consensusTimestamp);
        tokenAccount.setClaim(true);
        tokenAccount.setCreatedTimestamp(consensusTimestamp);
        tokenAccount.setTimestampLower(consensusTimestamp);
        tokenAccount.setTokenId(token.getId());
        entityListener.onTokenAccount(tokenAccount);
    }
}
