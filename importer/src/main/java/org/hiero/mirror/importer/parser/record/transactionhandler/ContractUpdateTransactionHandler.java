// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.transactionhandler;

import static com.hederahashgraph.api.proto.java.ContractUpdateTransactionBody.StakedIdCase.STAKEDID_NOT_SET;
import static org.hiero.mirror.common.domain.transaction.RecordFile.HAPI_VERSION_0_27_0;
import com.hederahashgraph.api.proto.java.ContractID;
import jakarta.inject.Named;
import lombok.CustomLog;
import org.hiero.mirror.common.domain.entity.AbstractEntity;
import org.hiero.mirror.common.domain.entity.Entity;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.domain.entity.EntityType;
import org.hiero.mirror.common.domain.transaction.RecordItem;
import org.hiero.mirror.common.domain.transaction.TransactionType;
import org.hiero.mirror.common.util.DomainUtils;
import org.hiero.mirror.importer.domain.EntityIdService;
import org.hiero.mirror.importer.parser.record.entity.EntityListener;
import org.hiero.mirror.importer.util.Utility;

@CustomLog
@Named
class ContractUpdateTransactionHandler extends AbstractEntityCrudTransactionHandler {

    private final EVMHookHandler evmHookHandler;

    ContractUpdateTransactionHandler(EntityIdService entityIdService, EntityListener entityListener, EVMHookHandler evmHookHandler) {
        super(entityIdService, entityListener, TransactionType.CONTRACTUPDATEINSTANCE);
        this.evmHookHandler = evmHookHandler;
    }

    /**
     * First attempts to extract the contract ID from the receipt, which was populated in HAPI 0.23 for contract update.
     * Otherwise, falls back to checking the transaction body which may contain an EVM address. In case of partial
     * mirror nodes, it's possible the database does not have the mapping for that EVM address in the body, hence the
     * need for prioritizing the receipt.
     *
     * @param recordItem to check
     * @return The contract ID associated with this contract transaction
     */
    @Override
    public EntityId getEntity(RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // We explicitly ignore the updated fileID field since nodes do not allow changing the bytecode after create
    @Override
    @SuppressWarnings({ "deprecation", "java:S1874" })
    protected void doUpdateEntity(Entity entity, RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void updateStakingInfo(RecordItem recordItem, Entity entity) {
        if (recordItem.getHapiVersion().isLessThan(HAPI_VERSION_0_27_0)) {
            return;
        }
        var transactionBody = recordItem.getTransactionBody().getContractUpdateInstance();
        if (transactionBody.hasDeclineReward()) {
            entity.setDeclineReward(transactionBody.getDeclineReward().getValue());
        }
        switch(transactionBody.getStakedIdCase()) {
            case STAKEDID_NOT_SET:
                break;
            case STAKED_NODE_ID:
                entity.setStakedNodeId(transactionBody.getStakedNodeId());
                entity.setStakedAccountId(AbstractEntity.ACCOUNT_ID_CLEARED);
                break;
            case STAKED_ACCOUNT_ID:
                var accountId = EntityId.of(transactionBody.getStakedAccountId());
                entity.setStakedAccountId(accountId.getId());
                entity.setStakedNodeId(AbstractEntity.NODE_ID_CLEARED);
                recordItem.addEntityId(accountId);
                break;
        }
        // If the stake node id or the decline reward value has changed, we start a new stake period.
        if (transactionBody.getStakedIdCase() != STAKEDID_NOT_SET || transactionBody.hasDeclineReward()) {
            entity.setStakePeriodStart(Utility.getEpochDay(recordItem.getConsensusTimestamp()));
        }
    }
}
