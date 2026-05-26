// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.transactionhandler;

import static org.hiero.mirror.common.domain.transaction.RecordFile.HAPI_VERSION_0_27_0;
import static org.hiero.mirror.common.util.DomainUtils.EVM_ADDRESS_LENGTH;
import com.google.protobuf.ByteString;
import jakarta.inject.Named;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.hiero.mirror.common.domain.entity.Entity;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.domain.entity.EntityType;
import org.hiero.mirror.common.domain.transaction.RecordItem;
import org.hiero.mirror.common.domain.transaction.Transaction;
import org.hiero.mirror.common.domain.transaction.TransactionType;
import org.hiero.mirror.common.util.DomainUtils;
import org.hiero.mirror.importer.domain.EntityIdService;
import org.hiero.mirror.importer.parser.record.entity.EntityListener;
import org.hiero.mirror.importer.util.Utility;

@Named
class CryptoCreateTransactionHandler extends AbstractEntityCrudTransactionHandler {

    private final EVMHookHandler evmHookHandler;

    CryptoCreateTransactionHandler(EntityIdService entityIdService, EntityListener entityListener, EVMHookHandler evmHookHandler) {
        super(entityIdService, entityListener, TransactionType.CRYPTOCREATEACCOUNT);
        this.evmHookHandler = evmHookHandler;
    }

    @Override
    public EntityId getEntity(RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void doUpdateTransaction(Transaction transaction, RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @SuppressWarnings({ "deprecation", "java:S1874" })
    protected void doUpdateEntity(Entity entity, RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void updateStakingInfo(RecordItem recordItem, Entity entity) {
        if (recordItem.getHapiVersion().isLessThan(HAPI_VERSION_0_27_0)) {
            return;
        }
        var transactionBody = recordItem.getTransactionBody().getCryptoCreateAccount();
        entity.setDeclineReward(transactionBody.getDeclineReward());
        switch(transactionBody.getStakedIdCase()) {
            case STAKEDID_NOT_SET ->
                {
                    return;
                }
            case STAKED_NODE_ID ->
                entity.setStakedNodeId(transactionBody.getStakedNodeId());
            case STAKED_ACCOUNT_ID ->
                {
                    var accountId = EntityId.of(transactionBody.getStakedAccountId());
                    entity.setStakedAccountId(accountId.getId());
                    recordItem.addEntityId(accountId);
                }
        }
        entity.setStakePeriodStart(Utility.getEpochDay(recordItem.getConsensusTimestamp()));
    }
}
