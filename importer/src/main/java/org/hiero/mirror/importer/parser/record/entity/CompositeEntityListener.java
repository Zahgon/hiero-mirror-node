// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.entity;

import jakarta.inject.Named;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.common.domain.addressbook.NetworkStake;
import org.hiero.mirror.common.domain.addressbook.NodeStake;
import org.hiero.mirror.common.domain.contract.Contract;
import org.hiero.mirror.common.domain.contract.ContractAction;
import org.hiero.mirror.common.domain.contract.ContractLog;
import org.hiero.mirror.common.domain.contract.ContractResult;
import org.hiero.mirror.common.domain.contract.ContractStateChange;
import org.hiero.mirror.common.domain.contract.ContractTransaction;
import org.hiero.mirror.common.domain.entity.CryptoAllowance;
import org.hiero.mirror.common.domain.entity.Entity;
import org.hiero.mirror.common.domain.entity.EntityTransaction;
import org.hiero.mirror.common.domain.entity.NftAllowance;
import org.hiero.mirror.common.domain.entity.TokenAllowance;
import org.hiero.mirror.common.domain.file.FileData;
import org.hiero.mirror.common.domain.hook.Hook;
import org.hiero.mirror.common.domain.hook.HookStorageChange;
import org.hiero.mirror.common.domain.node.Node;
import org.hiero.mirror.common.domain.node.RegisteredNode;
import org.hiero.mirror.common.domain.schedule.Schedule;
import org.hiero.mirror.common.domain.token.CustomFee;
import org.hiero.mirror.common.domain.token.Nft;
import org.hiero.mirror.common.domain.token.Token;
import org.hiero.mirror.common.domain.token.TokenAccount;
import org.hiero.mirror.common.domain.token.TokenAirdrop;
import org.hiero.mirror.common.domain.token.TokenTransfer;
import org.hiero.mirror.common.domain.topic.Topic;
import org.hiero.mirror.common.domain.topic.TopicMessage;
import org.hiero.mirror.common.domain.transaction.AssessedCustomFee;
import org.hiero.mirror.common.domain.transaction.CryptoTransfer;
import org.hiero.mirror.common.domain.transaction.EthereumTransaction;
import org.hiero.mirror.common.domain.transaction.LiveHash;
import org.hiero.mirror.common.domain.transaction.NetworkFreeze;
import org.hiero.mirror.common.domain.transaction.Prng;
import org.hiero.mirror.common.domain.transaction.StakingRewardTransfer;
import org.hiero.mirror.common.domain.transaction.Transaction;
import org.hiero.mirror.common.domain.transaction.TransactionSignature;
import org.hiero.mirror.common.domain.tss.Ledger;
import org.hiero.mirror.importer.exception.ImporterException;
import org.jspecify.annotations.NullMarked;
import org.springframework.context.annotation.Primary;

@CustomLog
@Named
@NullMarked
@Primary
@RequiredArgsConstructor
public class CompositeEntityListener implements EntityListener {

    private final List<EntityListener> entityListeners;

    private <T> void onEach(BiConsumer<EntityListener, T> consumer, T t) {
        for (int i = 0; i < entityListeners.size(); ++i) {
            var entityListener = entityListeners.get(i);
            if (entityListener.isEnabled()) {
                consumer.accept(entityListener, t);
            }
        }
    }

    @Override
    public void onAssessedCustomFee(AssessedCustomFee assessedCustomFee) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onContract(Contract contract) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onContractAction(ContractAction contractAction) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onContractLog(ContractLog contractLog) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onContractResult(ContractResult contractResult) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onContractStateChange(ContractStateChange contractStateChange) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onContractTransactions(Collection<ContractTransaction> contractTransactions) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onCryptoAllowance(CryptoAllowance cryptoAllowance) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onCryptoTransfer(CryptoTransfer cryptoTransfer) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onCustomFee(CustomFee customFee) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onEntity(Entity entity) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onEntityTransactions(Collection<EntityTransaction> entityTransactions) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onEthereumTransaction(EthereumTransaction ethereumTransaction) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onFileData(FileData fileData) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onHook(Hook hook) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onHookStorageChange(HookStorageChange storageChange) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onLedger(final Ledger ledger) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onLiveHash(LiveHash liveHash) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onNetworkFreeze(NetworkFreeze networkFreeze) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onNetworkStake(NetworkStake networkStake) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onNft(Nft nft) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onNftAllowance(NftAllowance nftAllowance) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onNode(Node node) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onNodeStake(NodeStake nodeStake) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onPrng(Prng prng) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onRegisteredNode(RegisteredNode registeredNode) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onSchedule(Schedule schedule) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onStakingRewardTransfer(StakingRewardTransfer stakingRewardTransfer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onToken(Token token) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onTokenAccount(TokenAccount tokenAccount) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onTokenAirdrop(TokenAirdrop tokenAirdrop) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onTokenAllowance(TokenAllowance tokenAllowance) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onTokenTransfer(TokenTransfer tokenTransfer) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onTopic(Topic topic) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onTopicMessage(TopicMessage topicMessage) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onTransaction(Transaction transaction) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onTransactionSignature(TransactionSignature transactionSignature) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
