// SPDX-License-Identifier: Apache-2.0
package com.hedera.hapi.node.state.token;

import static java.util.Objects.requireNonNull;
import com.hedera.hapi.node.base.AccountID;
import com.hedera.hapi.node.base.Key;
import com.hedera.hapi.node.base.NftID;
import com.hedera.hapi.node.base.PendingAirdropId;
import com.hedera.hapi.node.base.TokenID;
import com.hedera.pbj.runtime.Codec;
import com.hedera.pbj.runtime.JsonCodec;
import com.hedera.pbj.runtime.OneOf;
import com.hedera.pbj.runtime.io.buffer.Bytes;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Copied PBJ model from the Hedera Services with added Supplier fields for lazy loading. This model will be deleted as part of future enhancement.
 *
 * Representation of a Hedera Token Service account entity in the network Merkle tree.
 * <p>
 * As with all network entities, account has a unique entity number represented as shard.realm.X.
 * X can be an alias public key or an EVM address or a number.
 *
 * @param accountId <b>(1)</b> The unique entity id of the account.,
 * @param alias <b>(2)</b> The alias to use for this account, if any.,
 * @param key <b>(3)</b> (Optional) The key to be used to sign transactions from the account, if any.
 *            This key will not be set for hollow accounts until the account is finalized.
 *            This key should be set on all the accounts, except for immutable accounts (0.0.800 and 0.0.801).,
 * @param expirationSecond <b>(4)</b> The expiration time of the account, in seconds since the epoch.,
 * @param tinybarBalanceSupplier <b>(5)</b> The balance of the account, in tiny-bars wrapped in a supplier.,
 * @param memo <b>(6)</b> An optional description of the account with UTF-8 encoding up to 100 bytes.,
 * @param deleted <b>(7)</b> A boolean marking if the account has been deleted.,
 * @param stakedToMe <b>(8)</b> The amount of hbars staked to the account.,
 * @param stakePeriodStart <b>(9)</b> If this account stakes to another account, its value will be -1. It will
 *                         be set to the time when the account starts staking to a node.,
 * @param stakedId <b>(10, 11)</b> ID of the account or node to which this account is staking.,
 * @param declineReward <b>(12)</b> A boolean marking if the account declines rewards.,
 * @param receiverSigRequired <b>(13)</b> A boolean marking if the account requires a receiver signature.,
 * @param headTokenId <b>(14)</b> The token ID of the head of the linked list from token relations map for the account.,
 * @param headNftId <b>(15)</b> The NftID of the head of the linked list from unique tokens map for the account.,
 * @param headNftSerialNumber <b>(16)</b> The serial number of the head NftID of the linked list from unique tokens map for the account.,
 * @param numberOwnedNftsSupplier <b>(17)</b> The number of NFTs owned by the account wrapped in a supplier.,
 * @param maxAutoAssociations <b>(18)</b> The maximum number of tokens that can be auto-associated with the account.,
 * @param usedAutoAssociations <b>(19)</b> The number of used auto-association slots.,
 * @param numberAssociationsSupplier <b>(20)</b> The number of tokens associated with the account wrapped in a supplier. This number is used for
 *  *                           fee calculation during renewal of the account.,
 * @param smartContract <b>(21)</b> A boolean marking if the account is a smart contract.,
 * @param numberPositiveBalancesSupplier <b>(22)</b> The number of tokens with a positive balance associated with the account wrapped in a supplier.
 *  *                               If the account has positive balance in a token, it can not be deleted.,
 * @param ethereumNonce <b>(23)</b> The nonce of the account, used for Ethereum interoperability.,
 * @param stakeAtStartOfLastRewardedPeriod <b>(24)</b> The amount of hbars staked to the account at the start of the last rewarded period.,
 * @param autoRenewAccountId <b>(25)</b> (Optional) The id of an auto-renew account, in the same shard and realm as the account, that
 *                           has signed a transaction allowing the network to use its balance to automatically extend the account's
 *                           expiration time when it passes.,
 * @param autoRenewSeconds <b>(26)</b> The number of seconds the network should automatically extend the account's expiration by, if the
 *                         account has a valid auto-renew account, and is not deleted upon expiration.
 *                         If this is not provided in an allowed range on account creation, the transaction will fail with INVALID_AUTO_RENEWAL_PERIOD.
 *                         The default values for the minimum period and maximum period are 30 days and 90 days, respectively.,
 * @param contractKvPairsNumber <b>(27)</b> If this account is a smart-contract, number of key-value pairs stored on the contract.
 *                              This is used to determine the storage rent for the contract.,
 * @param cryptoAllowancesSupplier <b>(28)</b> (Optional) List of crypto allowances approved by the account in a supplier.
 *  *                         It contains account number for which the allowance is approved to and
 *  *                         the amount approved for that account.,
 * @param approveForAllNftAllowancesSupplier <b>(29)</b> (Optional) List of non-fungible token allowances approved for all by the account in a supplier.
 *  *                                   It contains account number approved for spending all serial numbers for the given
 *  *                                   NFT token number using approved_for_all flag.
 *  *                                   Allowances for a specific serial number is stored in the NFT itself in state.,
 * @param tokenAllowancesSupplier <b>(30)</b> (Optional) List of fungible token allowances approved by the account in a supplier.
 *  *                        It contains account number for which the allowance is approved to and  the token number.
 *  *                        It also contains and the amount approved for that account.,
 * @param numberTreasuryTitles <b>(31)</b> The number of tokens for which this account is treasury,
 * @param expiredAndPendingRemoval <b>(32)</b> A flag indicating if the account is expired and pending removal.
 *                                 Only the entity expiration system task toggles this flag when it reaches this account
 *                                 and finds it expired. Before setting the flag the system task checks if the account has
 *                                 an auto-renew account with balance. This is done to prevent a zero-balance account with a funded
 *                                 auto-renew account from being treated as expired in the interval between its expiration
 *                                 and the time the system task actually auto-renews it.,
 * @param firstContractStorageKey <b>(33)</b> The first key in the doubly-linked list of this contract's storage mappings;
 *                                It will be null if if the account is not a contract or the contract has no storage mappings.,
 * @param headPendingAirdropId <b>(34)</b> A pending airdrop ID at the head of the linked list for this account
 *                             from the account airdrops map.<br/>
 *                             The account airdrops are connected by including the "next" and "previous"
 *                             `PendingAirdropID` in each `AccountAirdrop` message.
 *                             <p>
 *                             This value SHALL NOT be empty if this account is "sender" for any
 *                             pending airdrop, and SHALL be empty otherwise.
 * @param numberPendingAirdrops <b>(35)</b> A number of pending airdrops.
 *                              <p>
 *                              This count SHALL be used to calculate rent _without_ walking the linked
 *                              list of pending airdrops associated to this account via the
 *                              `head_pending_airdrop_id` field.</p><br/>
 *                              This value MUST be updated for every airdrop, clam, or cancel transaction
 *                              that designates this account as a receiver.<br/>
 *                              This number MUST always match the count of entries in the "list"
 *                              identified by `head_pending_airdrop_id`.
 * @param numberHooksInUse <b>(36)</b> The number of hooks currently in use on this account.
 * @param firstHookId <b>(37)</b> If the account has more than zero hooks in use, the id of the first hook in its
 *                    doubly-linked list of hooks.
 * @param numberLambdaStorageSlots <b>(38)</b> The number of storage slots in use by this account's lambdas.
 */
public record Account(@Nullable AccountID accountId, @Nonnull Bytes alias, @Nullable Key key, long expirationSecond, Supplier<Long> tinybarBalanceSupplier, @Nonnull String memo, boolean deleted, long stakedToMe, long stakePeriodStart, OneOf<StakedIdOneOfType> stakedId, boolean declineReward, boolean receiverSigRequired, @Nullable TokenID headTokenId, @Nullable NftID headNftId, long headNftSerialNumber, Supplier<Long> numberOwnedNftsSupplier, int maxAutoAssociations, int usedAutoAssociations, Supplier<Integer> numberAssociationsSupplier, boolean smartContract, Supplier<Integer> numberPositiveBalancesSupplier, long ethereumNonce, long stakeAtStartOfLastRewardedPeriod, @Nullable AccountID autoRenewAccountId, long autoRenewSeconds, int contractKvPairsNumber, @Nonnull Supplier<List<AccountCryptoAllowance>> cryptoAllowancesSupplier, @Nonnull Supplier<List<AccountApprovalForAllAllowance>> approveForAllNftAllowancesSupplier, @Nonnull Supplier<List<AccountFungibleTokenAllowance>> tokenAllowancesSupplier, int numberTreasuryTitles, boolean expiredAndPendingRemoval, @Nonnull Bytes firstContractStorageKey, @Nullable PendingAirdropId headPendingAirdropId, long numberPendingAirdrops, long numberHooksInUse, long firstHookId, long numberLambdaStorageSlots) {

    /**
     * Protobuf codec for reading and writing in protobuf format
     */
    public static final Codec<Account> PROTOBUF = new com.hedera.hapi.node.state.token.codec.AccountProtoCodec();

    /**
     * JSON codec for reading and writing in JSON format
     */
    public static final JsonCodec<Account> JSON = new com.hedera.hapi.node.state.token.codec.AccountJsonCodec();

    /**
     * Default instance with all fields set to default values
     */
    public static final Account DEFAULT = newBuilder().build();

    private static final Supplier<Long> DEFAULT_LONG_SUPPLIER = () -> 0L;

    private static final Supplier<Integer> DEFAULT_INTEGER_SUPPLIER = () -> 0;

    public Account(AccountID accountId, Bytes alias, Key key, long expirationSecond, long tinybarBalance, String memo, boolean deleted, long stakedToMe, long stakePeriodStart, OneOf<Account.StakedIdOneOfType> stakedId, boolean declineReward, boolean receiverSigRequired, TokenID headTokenId, NftID headNftId, long headNftSerialNumber, long numberOwnedNfts, int maxAutoAssociations, int usedAutoAssociations, int numberAssociations, boolean smartContract, int numberPositiveBalances, long ethereumNonce, long stakeAtStartOfLastRewardedPeriod, AccountID autoRenewAccountId, long autoRenewSeconds, int contractKvPairsNumber, List<AccountCryptoAllowance> cryptoAllowances, List<AccountApprovalForAllAllowance> approveForAllNftAllowances, List<AccountFungibleTokenAllowance> tokenAllowances, int numberTreasuryTitles, boolean expiredAndPendingRemoval, Bytes firstContractStorageKey, PendingAirdropId headPendingAirdropId, long numberPendingAirdrops, long numberHooksInUse, long firstHookId, long numberLambdaStorageSlots) {
        this(accountId, alias, key, expirationSecond, () -> tinybarBalance, memo, deleted, stakedToMe, stakePeriodStart, stakedId, declineReward, receiverSigRequired, headTokenId, headNftId, headNftSerialNumber, () -> numberOwnedNfts, maxAutoAssociations, usedAutoAssociations, () -> numberAssociations, smartContract, () -> numberPositiveBalances, ethereumNonce, stakeAtStartOfLastRewardedPeriod, autoRenewAccountId, autoRenewSeconds, contractKvPairsNumber, () -> cryptoAllowances, () -> approveForAllNftAllowances, () -> tokenAllowances, numberTreasuryTitles, expiredAndPendingRemoval, firstContractStorageKey, headPendingAirdropId, numberPendingAirdrops, numberHooksInUse, firstHookId, numberLambdaStorageSlots);
    }

    /**
     * Return a new builder for building a model object. This is just a shortcut for <code>new Model.Builder()</code>.
     *
     * @return a new builder
     */
    public static Builder newBuilder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Override the default hashCode method for
     * all other objects to make hashCode
     */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Override the default equals method for
     */
    @Override
    public boolean equals(Object that) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convenience method to check if the accountId has a value
     *
     * @return true of the accountId has a value
     */
    public boolean hasAccountId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for accountId if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if accountId is null
     * @return the value for accountId if it has a value, or else returns the default value
     */
    public AccountID accountIdOrElse(@Nonnull final AccountID defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for accountId if it has a value, or else throws an NPE.
     * value for the type.
     *
     * @return the value for accountId if it has a value
     * @throws NullPointerException if accountId is null
     */
    @Nonnull
    public AccountID accountIdOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes the supplied {@link Consumer} if, and only if, the accountId has a value
     *
     * @param ifPresent the {@link Consumer} to execute
     */
    public void ifAccountId(@Nonnull final Consumer<AccountID> ifPresent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convenience method to check if the key has a value
     *
     * @return true of the key has a value
     */
    public boolean hasKey() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for key if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if key is null
     * @return the value for key if it has a value, or else returns the default value
     */
    public Key keyOrElse(@Nonnull final Key defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for key if it has a value, or else throws an NPE.
     * value for the type.
     *
     * @return the value for key if it has a value
     * @throws NullPointerException if key is null
     */
    @Nonnull
    public Key keyOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes the supplied {@link Consumer} if, and only if, the key has a value
     *
     * @param ifPresent the {@link Consumer} to execute
     */
    public void ifKey(@Nonnull final Consumer<Key> ifPresent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convenience method to check if the headTokenId has a value
     *
     * @return true of the headTokenId has a value
     */
    public boolean hasHeadTokenId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for headTokenId if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if headTokenId is null
     * @return the value for headTokenId if it has a value, or else returns the default value
     */
    public TokenID headTokenIdOrElse(@Nonnull final TokenID defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for headTokenId if it has a value, or else throws an NPE.
     * value for the type.
     *
     * @return the value for headTokenId if it has a value
     * @throws NullPointerException if headTokenId is null
     */
    @Nonnull
    public TokenID headTokenIdOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes the supplied {@link Consumer} if, and only if, the headTokenId has a value
     *
     * @param ifPresent the {@link Consumer} to execute
     */
    public void ifHeadTokenId(@Nonnull final Consumer<TokenID> ifPresent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convenience method to check if the headNftId has a value
     *
     * @return true of the headNftId has a value
     */
    public boolean hasHeadNftId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for headNftId if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if headNftId is null
     * @return the value for headNftId if it has a value, or else returns the default value
     */
    public NftID headNftIdOrElse(@Nonnull final NftID defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for headNftId if it has a value, or else throws an NPE.
     * value for the type.
     *
     * @return the value for headNftId if it has a value
     * @throws NullPointerException if headNftId is null
     */
    @Nonnull
    public NftID headNftIdOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes the supplied {@link Consumer} if, and only if, the headNftId has a value
     *
     * @param ifPresent the {@link Consumer} to execute
     */
    public void ifHeadNftId(@Nonnull final Consumer<NftID> ifPresent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convenience method to check if the autoRenewAccountId has a value
     *
     * @return true of the autoRenewAccountId has a value
     */
    public boolean hasAutoRenewAccountId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for autoRenewAccountId if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if autoRenewAccountId is null
     * @return the value for autoRenewAccountId if it has a value, or else returns the default value
     */
    public AccountID autoRenewAccountIdOrElse(@Nonnull final AccountID defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for autoRenewAccountId if it has a value, or else throws an NPE.
     * value for the type.
     *
     * @return the value for autoRenewAccountId if it has a value
     * @throws NullPointerException if autoRenewAccountId is null
     */
    @Nonnull
    public AccountID autoRenewAccountIdOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes the supplied {@link Consumer} if, and only if, the autoRenewAccountId has a value
     *
     * @param ifPresent the {@link Consumer} to execute
     */
    public void ifAutoRenewAccountId(@Nonnull final Consumer<AccountID> ifPresent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convenience method to check if the headPendingAirdropId has a value
     *
     * @return true of the headPendingAirdropId has a value
     */
    public boolean hasHeadPendingAirdropId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for headPendingAirdropId if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if headPendingAirdropId is null
     * @return the value for headPendingAirdropId if it has a value, or else returns the default value
     */
    public PendingAirdropId headPendingAirdropIdOrElse(@Nonnull final PendingAirdropId defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for headPendingAirdropId if it has a value, or else throws an NPE.
     * value for the type.
     *
     * @return the value for headPendingAirdropId if it has a value
     * @throws NullPointerException if headPendingAirdropId is null
     */
    @Nonnull
    public PendingAirdropId headPendingAirdropIdOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes the supplied {@link Consumer} if, and only if, the headPendingAirdropId has a value
     *
     * @param ifPresent the {@link Consumer} to execute
     */
    public void ifHeadPendingAirdropId(@Nonnull final Consumer<PendingAirdropId> ifPresent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Direct typed getter for one of field stakedAccountId.
     *
     * @return one of value or null if one of is not set or a different one of value
     */
    @Nullable
    public AccountID stakedAccountId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convenience method to check if the stakedId has a one-of with type STAKED_ACCOUNT_ID
     *
     * @return true of the one of kind is STAKED_ACCOUNT_ID
     */
    public boolean hasStakedAccountId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for stakedAccountId if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if stakedAccountId is null
     * @return the value for stakedAccountId if it has a value, or else returns the default value
     */
    public AccountID stakedAccountIdOrElse(@Nonnull final AccountID defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for stakedAccountId if it was set, or throws a NullPointerException if it was not set.
     *
     * @return the value for stakedAccountId if it has a value
     * @throws NullPointerException if stakedAccountId is null
     */
    @Nonnull
    public AccountID stakedAccountIdOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Direct typed getter for one of field stakedNodeId.
     *
     * @return one of value or null if one of is not set or a different one of value
     */
    @Nullable
    public Long stakedNodeId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convenience method to check if the stakedId has a one-of with type STAKED_NODE_ID
     *
     * @return true of the one of kind is STAKED_NODE_ID
     */
    public boolean hasStakedNodeId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for stakedNodeId if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if stakedNodeId is null
     * @return the value for stakedNodeId if it has a value, or else returns the default value
     */
    public Long stakedNodeIdOrElse(@Nonnull final Long defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for stakedNodeId if it was set, or throws a NullPointerException if it was not set.
     *
     * @return the value for stakedNodeId if it has a value
     * @throws NullPointerException if stakedNodeId is null
     */
    @Nonnull
    public Long stakedNodeIdOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Return a builder for building a copy of this model object. It will be pre-populated with all the data from this
     * model object.
     *
     * @return a pre-populated builder
     */
    public Builder copyBuilder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public long tinybarBalance() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int numberAssociations() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int numberPositiveBalances() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public long numberOwnedNfts() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public List<AccountCryptoAllowance> cryptoAllowances() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public List<AccountApprovalForAllAllowance> approveForAllNftAllowances() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public List<AccountFungibleTokenAllowance> tokenAllowances() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Enum for the type of "staked_id" oneof value
     */
    public enum StakedIdOneOfType implements com.hedera.pbj.runtime.EnumWithProtoMetadata {

        /**
         * Enum value for a unset OneOf, to avoid null OneOfs
         */
        UNSET(-1, "UNSET"),
        /**
         * <b>(10)</b> ID of the new account to which this account is staking. If set to the sentinel <code>0.0.0</code> AccountID,
         *  this field removes this account's staked account ID.
         */
        STAKED_ACCOUNT_ID(10, "staked_account_id"),
        /**
         * <b>(11)</b> ID of the new node this account is staked to. If set to the sentinel <code>-1</code>, this field
         *  removes this account's staked node ID.
         */
        STAKED_NODE_ID(11, "staked_node_id");

        /**
         * The field ordinal in protobuf for this type
         */
        private final int protoOrdinal;

        /**
         * The original field name in protobuf for this type
         */
        private final String protoName;

        /**
         * OneOf Type Enum Constructor
         *
         * @param protoOrdinal The oneof field ordinal in protobuf for this type
         * @param protoName The original field name in protobuf for this type
         */
        StakedIdOneOfType(final int protoOrdinal, String protoName) {
            this.protoOrdinal = protoOrdinal;
            this.protoName = protoName;
        }

        /**
         * Get enum from protobuf ordinal
         *
         * @param ordinal the protobuf ordinal number
         * @return enum for matching ordinal
         * @throws IllegalArgumentException if ordinal doesn't exist
         */
        public static StakedIdOneOfType fromProtobufOrdinal(int ordinal) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Get enum from string name, supports the enum or protobuf format name
         *
         * @param name the enum or protobuf format name
         * @return enum for matching name
         */
        public static StakedIdOneOfType fromString(String name) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Get the oneof field ordinal in protobuf for this type
         *
         * @return The oneof field ordinal in protobuf for this type
         */
        public int protoOrdinal() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Get the original field name in protobuf for this type
         *
         * @return The original field name in protobuf for this type
         */
        public String protoName() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Builder class for easy creation, ideal for clean code where performance is not critical. In critical performance
     * paths use the constructor directly.
     */
    public static final class Builder {

        @Nullable
        private AccountID accountId = null;

        @Nonnull
        private Bytes alias = Bytes.EMPTY;

        @Nullable
        private Key key = null;

        private long expirationSecond = 0;

        private Supplier<Long> tinybarBalanceSupplier = DEFAULT_LONG_SUPPLIER;

        @Nonnull
        private String memo = "";

        private boolean deleted = false;

        private long stakedToMe = 0;

        private long stakePeriodStart = 0;

        private OneOf<Account.StakedIdOneOfType> stakedId = com.hedera.hapi.node.state.token.codec.AccountProtoCodec.STAKED_ID_UNSET;

        private boolean declineReward = false;

        private boolean receiverSigRequired = false;

        @Nullable
        private TokenID headTokenId = null;

        @Nullable
        private NftID headNftId = null;

        private long headNftSerialNumber = 0;

        private Supplier<Long> numberOwnedNftsSupplier = DEFAULT_LONG_SUPPLIER;

        private int maxAutoAssociations = 0;

        private int usedAutoAssociations = 0;

        private Supplier<Integer> numberAssociationsSupplier = DEFAULT_INTEGER_SUPPLIER;

        private boolean smartContract = false;

        private Supplier<Integer> numberPositiveBalancesSupplier = DEFAULT_INTEGER_SUPPLIER;

        private long ethereumNonce = 0;

        private long stakeAtStartOfLastRewardedPeriod = 0;

        @Nullable
        private AccountID autoRenewAccountId = null;

        private long autoRenewSeconds = 0;

        private int contractKvPairsNumber = 0;

        @Nonnull
        private Supplier<List<AccountCryptoAllowance>> cryptoAllowancesSupplier = Collections::emptyList;

        @Nonnull
        private Supplier<List<AccountApprovalForAllAllowance>> approveForAllNftAllowancesSupplier = Collections::emptyList;

        @Nonnull
        private Supplier<List<AccountFungibleTokenAllowance>> tokenAllowancesSupplier = Collections::emptyList;

        private int numberTreasuryTitles = 0;

        private boolean expiredAndPendingRemoval = false;

        @Nonnull
        private Bytes firstContractStorageKey = Bytes.EMPTY;

        @Nullable
        private PendingAirdropId headPendingAirdropId = null;

        private long numberPendingAirdrops = 0;

        private long numberHooksInUse = 0;

        private long firstHookId = 0;

        private long numberLambdaStorageSlots = 0;

        /**
         * Create an empty builder
         */
        public Builder() {
        }

        /**
         * Create a pre-populated Builder.
         *
         * @param accountId <b>(1)</b> The unique entity id of the account.,
         * @param alias <b>(2)</b> The alias to use for this account, if any.,
         * @param key <b>(3)</b> (Optional) The key to be used to sign transactions from the account, if any.
         *            This key will not be set for hollow accounts until the account is finalized.
         *            This key should be set on all the accounts, except for immutable accounts (0.0.800 and 0.0.801).,
         * @param expirationSecond <b>(4)</b> The expiration time of the account, in seconds since the epoch.,
         * @param tinybarBalanceSupplier <b>(5)</b> The balance of the account, in tiny-bars wrapped in a supplier.,
         * @param memo <b>(6)</b> An optional description of the account with UTF-8 encoding up to 100 bytes.,
         * @param deleted <b>(7)</b> A boolean marking if the account has been deleted.,
         * @param stakedToMe <b>(8)</b> The amount of hbars staked to the account.,
         * @param stakePeriodStart <b>(9)</b> If this account stakes to another account, its value will be -1. It will
         *                         be set to the time when the account starts staking to a node.,
         * @param stakedId <b>(10, 11)</b> ID of the account or node to which this account is staking.,
         * @param declineReward <b>(12)</b> A boolean marking if the account declines rewards.,
         * @param receiverSigRequired <b>(13)</b> A boolean marking if the account requires a receiver signature.,
         * @param headTokenId <b>(14)</b> The token ID of the head of the linked list from token relations map for the account.,
         * @param headNftId <b>(15)</b> The NftID of the head of the linked list from unique tokens map for the account.,
         * @param headNftSerialNumber <b>(16)</b> The serial number of the head NftID of the linked list from unique tokens map for the account.,
         * @param numberOwnedNftsSupplier <b>(17)</b> The number of NFTs owned by the account wrapped in a supplier.,
         * @param maxAutoAssociations <b>(18)</b> The maximum number of tokens that can be auto-associated with the account.,
         * @param usedAutoAssociations <b>(19)</b> The number of used auto-association slots.,
         * @param numberAssociationsSupplier <b>(20)</b> The number of tokens associated with the account wrapped in a supplier. This number is used for
         *  *                           fee calculation during renewal of the account.,
         * @param smartContract <b>(21)</b> A boolean marking if the account is a smart contract.,
         * @param numberPositiveBalancesSupplier <b>(22)</b> The number of tokens with a positive balance associated with the account wrapped in a supplier.
         *  *                               If the account has positive balance in a token, it can not be deleted.,
         * @param ethereumNonce <b>(23)</b> The nonce of the account, used for Ethereum interoperability.,
         * @param stakeAtStartOfLastRewardedPeriod <b>(24)</b> The amount of hbars staked to the account at the start of the last rewarded period.,
         * @param autoRenewAccountId <b>(25)</b> (Optional) The id of an auto-renew account, in the same shard and realm as the account, that
         *                           has signed a transaction allowing the network to use its balance to automatically extend the account's
         *                           expiration time when it passes.,
         * @param autoRenewSeconds <b>(26)</b> The number of seconds the network should automatically extend the account's expiration by, if the
         *                         account has a valid auto-renew account, and is not deleted upon expiration.
         *                         If this is not provided in an allowed range on account creation, the transaction will fail with INVALID_AUTO_RENEWAL_PERIOD.
         *                         The default values for the minimum period and maximum period are 30 days and 90 days, respectively.,
         * @param contractKvPairsNumber <b>(27)</b> If this account is a smart-contract, number of key-value pairs stored on the contract.
         *                              This is used to determine the storage rent for the contract.,
         * @param cryptoAllowancesSupplier <b>(28)</b> (Optional) List of crypto allowances approved by the account in a supplier.
         *  *                         It contains account number for which the allowance is approved to and
         *  *                         the amount approved for that account.,
         * @param approveForAllNftAllowancesSupplier <b>(29)</b> (Optional) List of non-fungible token allowances approved for all by the account in a supplier.
         *  *                                   It contains account number approved for spending all serial numbers for the given
         *  *                                   NFT token number using approved_for_all flag.
         *  *                                   Allowances for a specific serial number is stored in the NFT itself in state.,
         * @param tokenAllowancesSupplier <b>(30)</b> (Optional) List of fungible token allowances approved by the account in a supplier.
         *  *                        It contains account number for which the allowance is approved to and  the token number.
         *  *                        It also contains and the amount approved for that account.,
         * @param numberTreasuryTitles <b>(31)</b> The number of tokens for which this account is treasury,
         * @param expiredAndPendingRemoval <b>(32)</b> A flag indicating if the account is expired and pending removal.
         *                                 Only the entity expiration system task toggles this flag when it reaches this account
         *                                 and finds it expired. Before setting the flag the system task checks if the account has
         *                                 an auto-renew account with balance. This is done to prevent a zero-balance account with a funded
         *                                 auto-renew account from being treated as expired in the interval between its expiration
         *                                 and the time the system task actually auto-renews it.,
         * @param firstContractStorageKey <b>(33)</b> The first key in the doubly-linked list of this contract's storage mappings;
         *                                It will be null if if the account is not a contract or the contract has no storage mappings.,
         * @param headPendingAirdropId <b>(34)</b> A pending airdrop ID at the head of the linked list for this account
         *                             from the account airdrops map.<br/>
         *                             The account airdrops are connected by including the "next" and "previous"
         *                             `PendingAirdropID` in each `AccountAirdrop` message.
         *                             <p>
         *                             This value SHALL NOT be empty if this account is "sender" for any
         *                             pending airdrop, and SHALL be empty otherwise.
         * @param numberPendingAirdrops <b>(35)</b> The number of pending airdrops owned by the account. This number is used to collect rent
         *                              for the account.
         * @param numberHooksInUse <b>(36)</b> The number of hooks currently in use on this account.
         * @param firstHookId <b>(37)</b> If the account has more than zero hooks in use, the id of the first hook in its
         *                    doubly-linked list of hooks.
         * @param numberLambdaStorageSlots <b>(38)</b> The number of storage slots in use by this account's lambdas.
         */
        @SuppressWarnings("java:S107")
        public Builder(AccountID accountId, Bytes alias, Key key, long expirationSecond, Supplier<Long> tinybarBalanceSupplier, String memo, boolean deleted, long stakedToMe, long stakePeriodStart, OneOf<Account.StakedIdOneOfType> stakedId, boolean declineReward, boolean receiverSigRequired, TokenID headTokenId, NftID headNftId, long headNftSerialNumber, Supplier<Long> numberOwnedNftsSupplier, int maxAutoAssociations, int usedAutoAssociations, Supplier<Integer> numberAssociationsSupplier, boolean smartContract, Supplier<Integer> numberPositiveBalancesSupplier, long ethereumNonce, long stakeAtStartOfLastRewardedPeriod, AccountID autoRenewAccountId, long autoRenewSeconds, int contractKvPairsNumber, Supplier<List<AccountCryptoAllowance>> cryptoAllowancesSupplier, Supplier<List<AccountApprovalForAllAllowance>> approveForAllNftAllowancesSupplier, Supplier<List<AccountFungibleTokenAllowance>> tokenAllowancesSupplier, int numberTreasuryTitles, boolean expiredAndPendingRemoval, Bytes firstContractStorageKey, PendingAirdropId headPendingAirdropId, long numberPendingAirdrops, long numberHooksInUse, long firstHookId, long numberLambdaStorageSlots) {
            this.accountId = accountId;
            this.alias = alias != null ? alias : Bytes.EMPTY;
            this.key = key;
            this.expirationSecond = expirationSecond;
            this.tinybarBalanceSupplier = tinybarBalanceSupplier;
            this.memo = memo != null ? memo : "";
            this.deleted = deleted;
            this.stakedToMe = stakedToMe;
            this.stakePeriodStart = stakePeriodStart;
            this.stakedId = stakedId;
            this.declineReward = declineReward;
            this.receiverSigRequired = receiverSigRequired;
            this.headTokenId = headTokenId;
            this.headNftId = headNftId;
            this.headNftSerialNumber = headNftSerialNumber;
            this.numberOwnedNftsSupplier = numberOwnedNftsSupplier;
            this.maxAutoAssociations = maxAutoAssociations;
            this.usedAutoAssociations = usedAutoAssociations;
            this.numberAssociationsSupplier = numberAssociationsSupplier;
            this.smartContract = smartContract;
            this.numberPositiveBalancesSupplier = numberPositiveBalancesSupplier;
            this.ethereumNonce = ethereumNonce;
            this.stakeAtStartOfLastRewardedPeriod = stakeAtStartOfLastRewardedPeriod;
            this.autoRenewAccountId = autoRenewAccountId;
            this.autoRenewSeconds = autoRenewSeconds;
            this.contractKvPairsNumber = contractKvPairsNumber;
            this.cryptoAllowancesSupplier = cryptoAllowancesSupplier == null ? Collections::emptyList : cryptoAllowancesSupplier;
            this.approveForAllNftAllowancesSupplier = approveForAllNftAllowancesSupplier == null ? Collections::emptyList : approveForAllNftAllowancesSupplier;
            this.tokenAllowancesSupplier = tokenAllowancesSupplier == null ? Collections::emptyList : tokenAllowancesSupplier;
            this.numberTreasuryTitles = numberTreasuryTitles;
            this.expiredAndPendingRemoval = expiredAndPendingRemoval;
            this.firstContractStorageKey = firstContractStorageKey != null ? firstContractStorageKey : Bytes.EMPTY;
            this.headPendingAirdropId = headPendingAirdropId;
            this.numberPendingAirdrops = numberPendingAirdrops;
            this.numberHooksInUse = numberHooksInUse;
            this.firstHookId = firstHookId;
            this.numberLambdaStorageSlots = numberLambdaStorageSlots;
        }

        /**
         * Build a new model record with data set on builder
         *
         * @return new model record with data set
         */
        public Account build() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(1)</b> The unique entity id of the account.
         *
         * @param accountId value to set
         * @return builder to continue building with
         */
        public Builder accountId(@Nullable AccountID accountId) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(1)</b> The unique entity id of the account.
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder accountId(AccountID.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(2)</b> The alias to use for this account, if any.
         *
         * @param alias value to set
         * @return builder to continue building with
         */
        public Builder alias(@Nonnull Bytes alias) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(3)</b> (Optional) The key to be used to sign transactions from the account, if any.
         * This key will not be set for hollow accounts until the account is finalized.
         * This key should be set on all the accounts, except for immutable accounts (0.0.800 and 0.0.801).
         *
         * @param key value to set
         * @return builder to continue building with
         */
        public Builder key(@Nullable Key key) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(3)</b> (Optional) The key to be used to sign transactions from the account, if any.
         * This key will not be set for hollow accounts until the account is finalized.
         * This key should be set on all the accounts, except for immutable accounts (0.0.800 and 0.0.801).
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder key(Key.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(4)</b> The expiration time of the account, in seconds since the epoch.
         *
         * @param expirationSecond value to set
         * @return builder to continue building with
         */
        public Builder expirationSecond(long expirationSecond) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(5)</b> The balance of the account, in tiny-bars.
         *
         * @param tinybarBalance value to set
         * @return builder to continue building with
         */
        public Builder tinybarBalance(long tinybarBalance) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(5)</b> The balance of the account, in tiny-bars.
         *
         * @param tinybarBalanceSupplier value to set
         * @return builder to continue building with
         */
        public Builder tinybarBalance(Supplier<Long> tinybarBalanceSupplier) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(6)</b> An optional description of the account with UTF-8 encoding up to 100 bytes.
         *
         * @param memo value to set
         * @return builder to continue building with
         */
        public Builder memo(@Nonnull String memo) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(7)</b> A boolean marking if the account has been deleted.
         *
         * @param deleted value to set
         * @return builder to continue building with
         */
        public Builder deleted(boolean deleted) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(8)</b> The amount of hbars staked to the account.
         *
         * @param stakedToMe value to set
         * @return builder to continue building with
         */
        public Builder stakedToMe(long stakedToMe) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(9)</b> If this account stakes to another account, its value will be -1. It will
         * be set to the time when the account starts staking to a node.
         *
         * @param stakePeriodStart value to set
         * @return builder to continue building with
         */
        public Builder stakePeriodStart(long stakePeriodStart) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(10)</b> ID of the new account to which this account is staking. If set to the sentinel <code>0.0.0</code> AccountID,
         * this field removes this account's staked account ID.
         *
         * @param stakedAccountId value to set
         * @return builder to continue building with
         */
        public Builder stakedAccountId(@Nullable AccountID stakedAccountId) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(10)</b> ID of the new account to which this account is staking. If set to the sentinel <code>0.0.0</code> AccountID,
         * this field removes this account's staked account ID.
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder stakedAccountId(AccountID.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(11)</b> ID of the new node this account is staked to. If set to the sentinel <code>-1</code>, this field
         * removes this account's staked node ID.
         *
         * @param stakedNodeId value to set
         * @return builder to continue building with
         */
        public Builder stakedNodeId(long stakedNodeId) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(12)</b> A boolean marking if the account declines rewards.
         *
         * @param declineReward value to set
         * @return builder to continue building with
         */
        public Builder declineReward(boolean declineReward) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(12)</b> A boolean marking if the account requires a receiver signature.
         *
         * @param receiverSigRequired value to set
         * @return builder to continue building with
         */
        public Builder receiverSigRequired(boolean receiverSigRequired) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(13)</b> The token ID of the head of the linked list from token relations map for the account.
         *
         * @param headTokenId value to set
         * @return builder to continue building with
         */
        public Builder headTokenId(@Nullable TokenID headTokenId) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(13)</b> The token ID of the head of the linked list from token relations map for the account.
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder headTokenId(TokenID.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(14)</b> The NftID of the head of the linked list from unique tokens map for the account.
         *
         * @param headNftId value to set
         * @return builder to continue building with
         */
        public Builder headNftId(@Nullable NftID headNftId) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(14)</b> The NftID of the head of the linked list from unique tokens map for the account.
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder headNftId(NftID.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(15)</b> The serial number of the head NftID of the linked list from unique tokens map for the account.
         *
         * @param headNftSerialNumber value to set
         * @return builder to continue building with
         */
        public Builder headNftSerialNumber(long headNftSerialNumber) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(16)</b> The number of NFTs owned by the account.
         *
         * @param numberOwnedNfts value to set
         * @return builder to continue building with
         */
        public Builder numberOwnedNfts(long numberOwnedNfts) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(16)</b> The number of NFTs owned by the account.
         *
         * @param numberOwnedNftsSupplier value to set
         * @return builder to continue building with
         */
        public Builder numberOwnedNfts(Supplier<Long> numberOwnedNftsSupplier) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(17)</b> The maximum number of tokens that can be auto-associated with the account.
         *
         * @param maxAutoAssociations value to set
         * @return builder to continue building with
         */
        public Builder maxAutoAssociations(int maxAutoAssociations) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(18)</b> The number of used auto-association slots.
         *
         * @param usedAutoAssociations value to set
         * @return builder to continue building with
         */
        public Builder usedAutoAssociations(int usedAutoAssociations) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(19)</b> The number of tokens associated with the account. This number is used for
         * fee calculation during renewal of the account.
         *
         * @param numberAssociations value to set
         * @return builder to continue building with
         */
        public Builder numberAssociations(int numberAssociations) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(19)</b> The number of tokens associated with the account. This number is used for
         * fee calculation during renewal of the account.
         *
         * @param numberAssociationsSupplier value to set
         * @return builder to continue building with
         */
        public Builder numberAssociations(Supplier<Integer> numberAssociationsSupplier) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(20)</b> A boolean marking if the account is a smart contract.
         *
         * @param smartContract value to set
         * @return builder to continue building with
         */
        public Builder smartContract(boolean smartContract) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(21)</b> The number of tokens with a positive balance associated with the account.
         * If the account has positive balance in a token, it can not be deleted.
         *
         * @param numberPositiveBalances value to set
         * @return builder to continue building with
         */
        public Builder numberPositiveBalances(int numberPositiveBalances) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(21)</b> The number of tokens with a positive balance associated with the account.
         * If the account has positive balance in a token, it can not be deleted.
         *
         * @param numberPositiveBalancesSupplier value to set
         * @return builder to continue building with
         */
        public Builder numberPositiveBalances(Supplier<Integer> numberPositiveBalancesSupplier) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(22)</b> The nonce of the account, used for Ethereum interoperability.
         *
         * @param ethereumNonce value to set
         * @return builder to continue building with
         */
        public Builder ethereumNonce(long ethereumNonce) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(23)</b> The amount of hbars staked to the account at the start of the last rewarded period.
         *
         * @param stakeAtStartOfLastRewardedPeriod value to set
         * @return builder to continue building with
         */
        public Builder stakeAtStartOfLastRewardedPeriod(long stakeAtStartOfLastRewardedPeriod) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(24)</b> (Optional) The id of an auto-renew account, in the same shard and realm as the account, that
         * has signed a transaction allowing the network to use its balance to automatically extend the account's
         * expiration time when it passes.
         *
         * @param autoRenewAccountId value to set
         * @return builder to continue building with
         */
        public Builder autoRenewAccountId(@Nullable AccountID autoRenewAccountId) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(24)</b> (Optional) The id of an auto-renew account, in the same shard and realm as the account, that
         * has signed a transaction allowing the network to use its balance to automatically extend the account's
         * expiration time when it passes.
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder autoRenewAccountId(AccountID.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(25)</b> The number of seconds the network should automatically extend the account's expiration by, if the
         * account has a valid auto-renew account, and is not deleted upon expiration.
         * If this is not provided in an allowed range on account creation, the transaction will fail with INVALID_AUTO_RENEWAL_PERIOD.
         * The default values for the minimum period and maximum period are 30 days and 90 days, respectively.
         *
         * @param autoRenewSeconds value to set
         * @return builder to continue building with
         */
        public Builder autoRenewSeconds(long autoRenewSeconds) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(26)</b> If this account is a smart-contract, number of key-value pairs stored on the contract.
         * This is used to determine the storage rent for the contract.
         *
         * @param contractKvPairsNumber value to set
         * @return builder to continue building with
         */
        public Builder contractKvPairsNumber(int contractKvPairsNumber) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(27</b> (Optional) List of crypto allowances approved by the account.
         * It contains account number for which the allowance is approved to and
         * the amount approved for that account.
         *
         * @param cryptoAllowances value to set
         * @return builder to continue building with
         */
        public Builder cryptoAllowances(@Nonnull List<AccountCryptoAllowance> cryptoAllowances) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(27)</b> (Optional) List of crypto allowances approved by the account.
         * It contains account number for which the allowance is approved to and
         * the amount approved for that account.
         *
         * @param cryptoAllowancesSupplier value to set
         * @return builder to continue building with
         */
        public Builder cryptoAllowances(@Nonnull Supplier<List<AccountCryptoAllowance>> cryptoAllowancesSupplier) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(28)</b> (Optional) List of non-fungible token allowances approved for all by the account.
         * It contains account number approved for spending all serial numbers for the given
         * NFT token number using approved_for_all flag.
         * Allowances for a specific serial number is stored in the NFT itself in state.
         *
         * @param approveForAllNftAllowances value to set
         * @return builder to continue building with
         */
        public Builder approveForAllNftAllowances(@Nonnull List<AccountApprovalForAllAllowance> approveForAllNftAllowances) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(28)</b> (Optional) List of non-fungible token allowances approved for all by the account.
         * It contains account number approved for spending all serial numbers for the given
         * NFT token number using approved_for_all flag.
         * Allowances for a specific serial number is stored in the NFT itself in state.
         *
         * @param approveForAllNftAllowancesSupplier value to set
         * @return builder to continue building with
         */
        public Builder approveForAllNftAllowances(@Nonnull Supplier<List<AccountApprovalForAllAllowance>> approveForAllNftAllowancesSupplier) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(29)</b> (Optional) List of fungible token allowances approved by the account.
         * It contains account number for which the allowance is approved to and  the token number.
         * It also contains and the amount approved for that account.
         *
         * @param tokenAllowances value to set
         * @return builder to continue building with
         */
        public Builder tokenAllowances(@Nonnull List<AccountFungibleTokenAllowance> tokenAllowances) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(29)</b> (Optional) List of fungible token allowances approved by the account.
         * It contains account number for which the allowance is approved to and  the token number.
         * It also contains and the amount approved for that account.
         *
         * @param tokenAllowancesSupplier value to set
         * @return builder to continue building with
         */
        public Builder tokenAllowances(@Nonnull Supplier<List<AccountFungibleTokenAllowance>> tokenAllowancesSupplier) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(30)</b> The number of tokens for which this account is treasury
         *
         * @param numberTreasuryTitles value to set
         * @return builder to continue building with
         */
        public Builder numberTreasuryTitles(int numberTreasuryTitles) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(31)</b> A flag indicating if the account is expired and pending removal.
         * Only the entity expiration system task toggles this flag when it reaches this account
         * and finds it expired. Before setting the flag the system task checks if the account has
         * an auto-renew account with balance. This is done to prevent a zero-balance account with a funded
         * auto-renew account from being treated as expired in the interval between its expiration
         * and the time the system task actually auto-renews it.
         *
         * @param expiredAndPendingRemoval value to set
         * @return builder to continue building with
         */
        public Builder expiredAndPendingRemoval(boolean expiredAndPendingRemoval) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(32)</b> The first key in the doubly-linked list of this contract's storage mappings;
         * It will be null if if the account is not a contract or the contract has no storage mappings.
         *
         * @param firstContractStorageKey value to set
         * @return builder to continue building with
         */
        public Builder firstContractStorageKey(@Nonnull Bytes firstContractStorageKey) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(33)</b> A pending airdrop ID at the head of the linked list for this account
         * from the account airdrops map.<br/>
         * The account airdrops are connected by including the "next" and "previous"
         * `PendingAirdropID` in each `AccountAirdrop` message.
         * <p>
         * This value SHALL NOT be empty if this account is "sender" for any
         * pending airdrop, and SHALL be empty otherwise.
         *
         * @param headPendingAirdropId value to set
         * @return builder to continue building with
         */
        public Builder headPendingAirdropId(@Nullable PendingAirdropId headPendingAirdropId) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(34)</b> A pending airdrop ID at the head of the linked list for this account
         * from the account airdrops map.<br/>
         * The account airdrops are connected by including the "next" and "previous"
         * `PendingAirdropID` in each `AccountAirdrop` message.
         * <p>
         * This value SHALL NOT be empty if this account is "sender" for any
         * pending airdrop, and SHALL be empty otherwise.
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder headPendingAirdropId(PendingAirdropId.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(35)</b> The number of pending airdrops owned by the account. This number is used to collect rent
         * for the account.
         *
         * @param numberPendingAirdrops value to set
         * @return builder to continue building with
         */
        public Builder numberPendingAirdrops(long numberPendingAirdrops) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(36)</b> The number of hooks currently in use on this account.
         *
         * @param numberHooksInUse value to set
         * @return builder to continue building with
         */
        public Builder numberHooksInUse(long numberHooksInUse) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(37)</b> If the account has more than zero hooks in use, the id of the first hook in its doubly-linked list of hooks.
         *
         * @param firstHookId value to set
         * @return builder to continue building with
         */
        public Builder firstHookId(long firstHookId) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(38)</b> The number of storage slots in use by this account's lambdas.
         *
         * @param numberLambdaStorageSlots value to set
         * @return builder to continue building with
         */
        public Builder numberLambdaStorageSlots(long numberLambdaStorageSlots) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
