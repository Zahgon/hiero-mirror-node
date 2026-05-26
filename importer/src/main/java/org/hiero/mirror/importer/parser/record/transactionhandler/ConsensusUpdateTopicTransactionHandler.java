// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.transactionhandler;

import com.google.common.collect.Range;
import com.hederahashgraph.api.proto.java.ConsensusUpdateTopicTransactionBody;
import com.hederahashgraph.api.proto.java.FixedCustomFee;
import com.hederahashgraph.api.proto.java.Timestamp;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;
import org.hiero.mirror.common.domain.entity.Entity;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.domain.entity.EntityType;
import org.hiero.mirror.common.domain.token.CustomFee;
import org.hiero.mirror.common.domain.token.FixedFee;
import org.hiero.mirror.common.domain.topic.Topic;
import org.hiero.mirror.common.domain.transaction.RecordItem;
import org.hiero.mirror.common.domain.transaction.TransactionType;
import org.hiero.mirror.common.util.DomainUtils;
import org.hiero.mirror.importer.domain.EntityIdService;
import org.hiero.mirror.importer.parser.record.entity.EntityListener;
import org.hiero.mirror.importer.util.Utility;

@Named
class ConsensusUpdateTopicTransactionHandler extends AbstractEntityCrudTransactionHandler {

    ConsensusUpdateTopicTransactionHandler(EntityIdService entityIdService, EntityListener entityListener) {
        super(entityIdService, entityListener, TransactionType.CONSENSUSUPDATETOPIC);
    }

    @Override
    public EntityId getEntity(RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void doUpdateEntity(Entity entity, RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void updateCustomFee(List<FixedCustomFee> fixedCustomFees, RecordItem recordItem, long topicId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void updateTopic(long consensusTimestamp, long topicId, ConsensusUpdateTopicTransactionBody transactionBody) {
        var adminKey = transactionBody.hasAdminKey() ? transactionBody.getAdminKey().toByteArray() : null;
        // The fee exempt key list is not cleared in the database if it's an empty list, instead, importer would
        // serialize the protobuf message with an empty key list. The reader should understand that semantically
        // an empty list is the same as no fee exempt key list.
        var feeExemptKeyList = transactionBody.hasFeeExemptKeyList() ? transactionBody.getFeeExemptKeyList().toByteArray() : null;
        var feeScheduleKey = transactionBody.hasFeeScheduleKey() ? transactionBody.getFeeScheduleKey().toByteArray() : null;
        var submitKey = transactionBody.hasSubmitKey() ? transactionBody.getSubmitKey().toByteArray() : null;
        var topic = Topic.builder().adminKey(adminKey).id(topicId).feeExemptKeyList(feeExemptKeyList).feeScheduleKey(feeScheduleKey).submitKey(submitKey).timestampRange(Range.atLeast(consensusTimestamp)).build();
        entityListener.onTopic(topic);
    }
}
