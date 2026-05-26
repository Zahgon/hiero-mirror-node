// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.transactionhandler;

import static com.hederahashgraph.api.proto.java.ContractCreateTransactionBody.InitcodeSourceCase.INITCODE;
import static org.hiero.mirror.common.domain.transaction.RecordFile.HAPI_VERSION_0_27_0;
import com.hedera.services.stream.proto.ContractBytecode;
import com.hederahashgraph.api.proto.java.Key;
import jakarta.inject.Named;
import java.util.List;
import lombok.CustomLog;
import org.hiero.mirror.common.domain.contract.Contract;
import org.hiero.mirror.common.domain.contract.ContractResult;
import org.hiero.mirror.common.domain.entity.Entity;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.domain.entity.EntityType;
import org.hiero.mirror.common.domain.transaction.RecordItem;
import org.hiero.mirror.common.domain.transaction.Transaction;
import org.hiero.mirror.common.domain.transaction.TransactionType;
import org.hiero.mirror.common.util.DomainUtils;
import org.hiero.mirror.importer.domain.EntityIdService;
import org.hiero.mirror.importer.parser.record.entity.EntityListener;
import org.hiero.mirror.importer.parser.record.entity.EntityProperties;
import org.hiero.mirror.importer.service.ContractInitcodeService;
import org.hiero.mirror.importer.util.Utility;

@CustomLog
@Named
class ContractCreateTransactionHandler extends AbstractEntityCrudTransactionHandler {

    private final ContractInitcodeService contractInitcodeService;

    private final EntityProperties entityProperties;

    private final EVMHookHandler evmHookHandler;

    ContractCreateTransactionHandler(ContractInitcodeService contractInitcodeService, EntityIdService entityIdService, EntityListener entityListener, EntityProperties entityProperties, EVMHookHandler evmHookHandler) {
        super(entityIdService, entityListener, TransactionType.CONTRACTCREATEINSTANCE);
        this.contractInitcodeService = contractInitcodeService;
        this.entityProperties = entityProperties;
        this.evmHookHandler = evmHookHandler;
    }

    @Override
    public EntityId getEntity(RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
     * Insert contract results even for failed transactions since they could fail during execution, and we want to
     * know how much gas was used and the call result regardless.
     */
    @Override
    public void doUpdateTransaction(Transaction transaction, RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @SuppressWarnings({ "deprecation", "java:S1874" })
    protected void doUpdateEntity(Entity entity, RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void createContract(RecordItem recordItem, Entity entity) {
        var transactionBody = recordItem.getTransactionBody().getContractCreateInstance();
        var contract = new Contract();
        contract.setId(entity.getId());
        if (transactionBody.hasFileID()) {
            var fileId = EntityId.of(transactionBody.getFileID());
            contract.setFileId(fileId);
            recordItem.addEntityId(fileId);
        }
        var contractId = recordItem.getTransactionRecord().getReceipt().getContractID();
        ContractBytecode contractBytecode = null;
        for (var sidecar : recordItem.getSidecarRecords()) {
            if (sidecar.hasBytecode() && !sidecar.getMigration()) {
                var bytecode = sidecar.getBytecode();
                if (contractId.equals(bytecode.getContractId())) {
                    contractBytecode = bytecode;
                    contract.setRuntimeBytecode(DomainUtils.toBytes(bytecode.getRuntimeBytecode()));
                    break;
                }
            }
        }
        contract.setInitcode(contractInitcodeService.get(contractBytecode, recordItem));
        // for child transactions FileID is located in parent ContractCreate/EthereumTransaction types
        // and initcode is located in the sidecar
        updateChildFromParent(contract, recordItem);
        entityListener.onContract(contract);
    }

    private void updateStakingInfo(RecordItem recordItem, Entity contract) {
        if (recordItem.getHapiVersion().isLessThan(HAPI_VERSION_0_27_0)) {
            return;
        }
        var transactionBody = recordItem.getTransactionBody().getContractCreateInstance();
        contract.setDeclineReward(transactionBody.getDeclineReward());
        switch(transactionBody.getStakedIdCase()) {
            case STAKEDID_NOT_SET:
                return;
            case STAKED_NODE_ID:
                contract.setStakedNodeId(transactionBody.getStakedNodeId());
                break;
            case STAKED_ACCOUNT_ID:
                var accountId = EntityId.of(transactionBody.getStakedAccountId());
                contract.setStakedAccountId(accountId.getId());
                recordItem.addEntityId(accountId);
                break;
        }
        contract.setStakePeriodStart(Utility.getEpochDay(recordItem.getConsensusTimestamp()));
    }

    @Override
    public void updateContractResult(ContractResult contractResult, RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void updateChildFromParent(Contract contract, RecordItem recordItem) {
        if (!recordItem.isChild() || recordItem.getParent() == null) {
            return;
        }
        // Parents may be either ContractCreate or EthereumTransaction
        var parentRecordItem = recordItem.getParent();
        var type = TransactionType.of(parentRecordItem.getTransactionType());
        switch(type) {
            case CONTRACTCREATEINSTANCE ->
                updateChildFromContractCreateParent(contract, parentRecordItem);
            case ETHEREUMTRANSACTION ->
                updateChildFromEthereumTransactionParent(contract, parentRecordItem);
            default ->
                {
                    // no-op
                }
        }
    }

    private void updateChildFromContractCreateParent(Contract contract, RecordItem recordItem) {
        var transactionBody = recordItem.getTransactionBody().getContractCreateInstance();
        switch(transactionBody.getInitcodeSourceCase()) {
            case FILEID:
                if (contract.getFileId() == null) {
                    var fileId = EntityId.of(transactionBody.getFileID());
                    contract.setFileId(fileId);
                    recordItem.addEntityId(fileId);
                }
                break;
            case INITCODE:
                if (contract.getInitcode() == null) {
                    contract.setInitcode(DomainUtils.toBytes(transactionBody.getInitcode()));
                }
                break;
            default:
                Utility.handleRecoverableError("Invalid InitcodeSourceCase {} at {}", transactionBody.getInitcodeSourceCase(), recordItem.getConsensusTimestamp());
                break;
        }
    }

    private void updateChildFromEthereumTransactionParent(Contract contract, RecordItem recordItem) {
        var body = recordItem.getTransactionBody().getEthereumTransaction();
        // use callData FileID if present
        if (body.hasCallData() && contract.getFileId() == null) {
            var fileId = EntityId.of(body.getCallData());
            contract.setFileId(fileId);
            recordItem.addEntityId(fileId);
            return;
        }
        if (contract.getInitcode() == null && recordItem.getEthereumTransaction() != null) {
            contract.setInitcode(recordItem.getEthereumTransaction().getCallData());
        }
    }
}
