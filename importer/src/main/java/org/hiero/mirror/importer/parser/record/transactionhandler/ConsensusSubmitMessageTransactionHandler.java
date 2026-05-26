// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.transactionhandler;

import static org.hiero.mirror.common.util.DomainUtils.toBytes;
import static org.hiero.mirror.importer.util.Utility.DEFAULT_RUNNING_HASH_VERSION;
import com.hederahashgraph.api.proto.java.ConsensusMessageChunkInfo;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.domain.topic.TopicMessage;
import org.hiero.mirror.common.domain.transaction.RecordItem;
import org.hiero.mirror.common.domain.transaction.Transaction;
import org.hiero.mirror.common.domain.transaction.TransactionType;
import org.hiero.mirror.importer.parser.record.entity.EntityListener;
import org.hiero.mirror.importer.parser.record.entity.EntityProperties;
import org.hiero.mirror.importer.util.Utility;

@Named
@RequiredArgsConstructor
final class ConsensusSubmitMessageTransactionHandler extends AbstractTransactionHandler {

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

    /**
     * Add common entity ids for a ConsensusSubmitMessage transaction. Note the main entity id (the topic id the message
     * is submitted to) is skipped since it's already tracked in topic_message table
     *
     * @param transaction
     * @param recordItem
     */
    @Override
    protected void addCommonEntityIds(Transaction transaction, RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void doUpdateTransaction(Transaction transaction, RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
