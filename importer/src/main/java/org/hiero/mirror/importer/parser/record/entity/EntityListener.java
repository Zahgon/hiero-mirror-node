// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.entity;

import java.util.Collection;
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

/**
 * Handlers for items parsed during processing of record stream.
 */
public interface EntityListener {

    default boolean isEnabled() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onAssessedCustomFee(AssessedCustomFee assessedCustomFee) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onContract(Contract contract) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onContractAction(ContractAction contractAction) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onContractLog(ContractLog contractLog) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onContractResult(ContractResult contractResult) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onContractStateChange(ContractStateChange contractStateChange) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onContractTransactions(Collection<ContractTransaction> contractTransactions) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onCryptoAllowance(CryptoAllowance cryptoAllowance) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onCustomFee(CustomFee customFee) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onCryptoTransfer(CryptoTransfer cryptoTransfer) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onEntity(Entity entity) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onEntityTransactions(Collection<EntityTransaction> entityTransactions) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onEthereumTransaction(EthereumTransaction ethereumTransaction) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onFileData(FileData fileData) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onHook(Hook hook) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onHookStorageChange(HookStorageChange storageChange) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onLedger(Ledger ledger) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onLiveHash(LiveHash liveHash) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onNetworkFreeze(NetworkFreeze networkFreeze) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onNetworkStake(NetworkStake networkStake) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onNft(Nft nft) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onNftAllowance(NftAllowance nftAllowance) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onNode(Node node) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onNodeStake(NodeStake nodeStake) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onPrng(Prng prng) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onRegisteredNode(RegisteredNode registeredNode) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onSchedule(Schedule schedule) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onStakingRewardTransfer(StakingRewardTransfer stakingRewardTransfer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onToken(Token token) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onTokenAccount(TokenAccount tokenAccount) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onTokenAirdrop(TokenAirdrop tokenAirdrop) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onTokenAllowance(TokenAllowance tokenAllowance) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onTokenTransfer(TokenTransfer tokenTransfer) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onTopic(Topic topic) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onTopicMessage(TopicMessage topicMessage) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onTransaction(Transaction transaction) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void onTransactionSignature(TransactionSignature transactionSignature) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
