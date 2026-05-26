// SPDX-License-Identifier: Apache-2.0
package com.hedera.hapi.node.state.token;

import static java.util.Objects.requireNonNull;
import static org.hiero.mirror.web3.utils.Suppliers.areSuppliersEqual;
import com.hedera.hapi.node.base.AccountID;
import com.hedera.hapi.node.base.Key;
import com.hedera.hapi.node.base.TokenID;
import com.hedera.hapi.node.base.TokenSupplyType;
import com.hedera.hapi.node.base.TokenType;
import com.hedera.hapi.node.transaction.CustomFee;
import com.hedera.pbj.runtime.Codec;
import com.hedera.pbj.runtime.JsonCodec;
import com.hedera.pbj.runtime.io.buffer.Bytes;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Representation of a Hedera Token Service token entity in the network Merkle tree.
 * <p>
 * As with all network entities, a token has a unique entity number, which is usually given along
 * with the network's shard and realm in the form of a shard.realm.number id.
 *
 * @param tokenId <b>(1)</b> The unique entity id of this token.
 * @param name <b>(2)</b> The human-readable name of this token. Need not be unique. Maximum length allowed is 100 bytes.
 * @param symbol <b>(3)</b> The human-readable symbol for the token. It is not necessarily unique. Maximum length allowed is 100 bytes.
 * @param decimals <b>(4)</b> The number of decimal places of this token. If decimals are 8 or 11, then the number of whole
 *                 tokens can be at most a few billions or millions, respectively. For example, it could match
 *                 Bitcoin (21 million whole tokens with 8 decimals) or hbars (50 billion whole tokens with 8 decimals).
 *                 It could even match Bitcoin with milli-satoshis (21 million whole tokens with 11 decimals).
 * @param totalSupplySupplier <b>(5)</b> The total supply of this token wrapped in a Supplier.
 * @param treasuryAccountId <b>(6)</b> The treasury account id of this token.
 * @param adminKey <b>(7)</b> (Optional) The admin key of this token. If this key is set, the token is mutable.
 *                 A mutable token can be modified.
 *                 If this key is not set on token creation, it cannot be modified.
 * @param kycKey <b>(8)</b> (Optional) The kyc key of this token.
 *               If this key is not set on token creation, it can only be set if the token has admin key set.
 * @param freezeKey <b>(9)</b> (Optional) The freeze key of this token. This key is needed for freezing the token.
 *                  If this key is not set on token creation, it can only be set if the token has admin key set.
 * @param wipeKey <b>(10)</b> (Optional) The wipe key of this token. This key is needed for wiping the token.
 *                If this key is not set on token creation, it can only be set if the token has admin key set.
 * @param supplyKey <b>(11)</b> (Optional) The supply key of this token. This key is needed for minting or burning token.
 *                  If this key is not set on token creation, it can only be set if the token has admin key set.
 * @param feeScheduleKey <b>(12)</b> (Optional) The fee schedule key of this token. This key should be set, in order to make any
 *                       changes to the custom fee schedule.
 *                       If this key is not set on token creation, it can only be set if the token has admin key set.
 * @param pauseKey <b>(13)</b> (Optional) The pause key of this token. This key is needed for pausing the token.
 *                 If this key is not set on token creation, it can only be set if the token has admin key set.
 * @param lastUsedSerialNumber <b>(14)</b> The last used serial number of this token.
 * @param deleted <b>(15)</b> The flag indicating if this token is deleted.
 * @param tokenType <b>(16)</b> The type of this token. A token can be either FUNGIBLE_COMMON or NON_FUNGIBLE_UNIQUE.
 *                  If it has been omitted during token creation, FUNGIBLE_COMMON type is used.
 * @param supplyType <b>(17)</b> The supply type of this token.A token can have either INFINITE or FINITE supply type.
 *                   If it has been omitted during token creation, INFINITE type is used.
 * @param autoRenewAccountId <b>(18)</b> The id of the account (if any) that the network will attempt to charge for the
 *  *                           token's auto-renewal upon expiration.
 * @param autoRenewSeconds <b>(19)</b> The number of seconds the network should automatically extend the token's expiration by, if the
 *                         token has a valid auto-renew account, and is not deleted upon expiration.
 *                         If this is not provided in a allowed range on token creation, the transaction will fail with INVALID_AUTO_RENEWAL_PERIOD.
 *                         The default values for the minimum period and maximum period are 30 days and 90 days, respectively.
 * @param expirationSecond <b>(20)</b> The expiration time of the token, in seconds since the epoch.
 * @param memo <b>(21)</b> An optional description of the token with UTF-8 encoding up to 100 bytes.
 * @param maxSupply <b>(22)</b> The maximum supply of this token.
 * @param paused <b>(23)</b> The flag indicating if this token is paused.
 * @param accountsFrozenByDefault <b>(24)</b> The flag indicating if this token has accounts associated to it that are frozen by default.
 * @param accountsKycGrantedByDefault <b>(25)</b> The flag indicating if this token has accounts associated with it that are KYC granted by default.
 * @param customFeesSupplier <b>(26)</b> (Optional) The custom fees of this token wrapped in a Supplier.
 * @param metadata <b>(27)</b> Metadata of the created token definition
 * @param metadataKey <b>(28)</b> The key which can change the metadata of a token
 *                    (token definition and individual NFTs).
 */
public record Token(@jakarta.annotation.Nullable TokenID tokenId, @Nonnull String name, @Nonnull String symbol, int decimals, Supplier<Long> totalSupplySupplier, @Nullable AccountID treasuryAccountId, @Nullable Key adminKey, @Nullable Key kycKey, @Nullable Key freezeKey, @Nullable Key wipeKey, @Nullable Key supplyKey, @Nullable Key feeScheduleKey, @Nullable Key pauseKey, long lastUsedSerialNumber, boolean deleted, TokenType tokenType, TokenSupplyType supplyType, @Nullable AccountID autoRenewAccountId, long autoRenewSeconds, long expirationSecond, @Nonnull String memo, long maxSupply, boolean paused, boolean accountsFrozenByDefault, boolean accountsKycGrantedByDefault, @Nonnull Supplier<List<CustomFee>> customFeesSupplier, @Nonnull Bytes metadata, @Nullable Key metadataKey) {

    /**
     * Protobuf codec for reading and writing in protobuf format
     */
    public static final Codec<Token> PROTOBUF = new com.hedera.hapi.node.state.token.codec.TokenProtoCodec();

    /**
     * JSON codec for reading and writing in JSON format
     */
    public static final JsonCodec<Token> JSON = new com.hedera.hapi.node.state.token.codec.TokenJsonCodec();

    /**
     * Default instance with all fields set to default values
     */
    public static final Token DEFAULT = newBuilder().totalSupply(0L).autoRenewAccountId(null).treasuryAccountId(null).build();

    /**
     * Create a pre-populated Token.
     *
     * @param tokenId <b>(1)</b> The unique entity id of this token.
     * @param name <b>(2)</b> The human-readable name of this token. Need not be unique. Maximum length allowed is 100 bytes.
     * @param symbol <b>(3)</b> The human-readable symbol for the token. It is not necessarily unique. Maximum length allowed is 100 bytes.
     * @param decimals <b>(4)</b> The number of decimal places of this token. If decimals are 8 or 11, then the number of whole
     *                 tokens can be at most a few billions or millions, respectively. For example, it could match
     *                 Bitcoin (21 million whole tokens with 8 decimals) or hbars (50 billion whole tokens with 8 decimals).
     *                 It could even match Bitcoin with milli-satoshis (21 million whole tokens with 11 decimals).
     * @param totalSupply <b>(5)</b> The total supply of this token wrapped in a Supplier.
     * @param treasuryAccountId <b>(6)</b> The treasury account id of this token wrapped in a Supplier.
     * @param adminKey <b>(7)</b> (Optional) The admin key of this token. If this key is set, the token is mutable.
     *                 A mutable token can be modified.
     *                 If this key is not set on token creation, it cannot be modified.
     * @param kycKey <b>(8)</b> (Optional) The kyc key of this token.
     *               If this key is not set on token creation, it can only be set if the token has admin key set.
     * @param freezeKey <b>(9)</b> (Optional) The freeze key of this token. This key is needed for freezing the token.
     *                  If this key is not set on token creation, it can only be set if the token has admin key set.
     * @param wipeKey <b>(10)</b> (Optional) The wipe key of this token. This key is needed for wiping the token.
     *                If this key is not set on token creation, it can only be set if the token has admin key set.
     * @param supplyKey <b>(11)</b> (Optional) The supply key of this token. This key is needed for minting or burning token.
     *                  If this key is not set on token creation, it can only be set if the token has admin key set.
     * @param feeScheduleKey <b>(12)</b> (Optional) The fee schedule key of this token. This key should be set, in order to make any
     *                       changes to the custom fee schedule.
     *                       If this key is not set on token creation, it can only be set if the token has admin key set.
     * @param pauseKey <b>(13)</b> (Optional) The pause key of this token. This key is needed for pausing the token.
     *                 If this key is not set on token creation, it can only be set if the token has admin key set.
     * @param lastUsedSerialNumber <b>(14)</b> The last used serial number of this token.
     * @param deleted <b>(15)</b> The flag indicating if this token is deleted.
     * @param tokenType <b>(16)</b> The type of this token. A token can be either FUNGIBLE_COMMON or NON_FUNGIBLE_UNIQUE.
     *                  If it has been omitted during token creation, FUNGIBLE_COMMON type is used.
     * @param supplyType <b>(17)</b> The supply type of this token.A token can have either INFINITE or FINITE supply type.
     *                   If it has been omitted during token creation, INFINITE type is used.
     * @param autoRenewAccountId <b>(18)</b> The id of the account (if any) that the network will attempt to charge for the
     *  *                           token's auto-renewal upon expiration wrapped in a Supplier.
     * @param autoRenewSeconds <b>(19)</b> The number of seconds the network should automatically extend the token's expiration by, if the
     *                         token has a valid auto-renew account, and is not deleted upon expiration.
     *                         If this is not provided in a allowed range on token creation, the transaction will fail with INVALID_AUTO_RENEWAL_PERIOD.
     *                         The default values for the minimum period and maximum period are 30 days and 90 days, respectively.
     * @param expirationSecond <b>(20)</b> The expiration time of the token, in seconds since the epoch.
     * @param memo <b>(21)</b> An optional description of the token with UTF-8 encoding up to 100 bytes.
     * @param maxSupply <b>(22)</b> The maximum supply of this token.
     * @param paused <b>(23)</b> The flag indicating if this token is paused.
     * @param accountsFrozenByDefault <b>(24)</b> The flag indicating if this token has accounts associated to it that are frozen by default.
     * @param accountsKycGrantedByDefault <b>(25)</b> The flag indicating if this token has accounts associated with it that are KYC granted by default.
     * @param customFees <b>(26)</b> (Optional) The custom fees of this token wrapped in a Supplier.
     * @param metadata <b>(27)</b> Metadata of the created token definition
     * @param metadataKey <b>(28)</b> The key which can change the metadata of a token
     *                    (token definition and individual NFTs).
     */
    public Token(TokenID tokenId, String name, String symbol, int decimals, long totalSupply, AccountID treasuryAccountId, Key adminKey, Key kycKey, Key freezeKey, Key wipeKey, Key supplyKey, Key feeScheduleKey, Key pauseKey, long lastUsedSerialNumber, boolean deleted, TokenType tokenType, TokenSupplyType supplyType, AccountID autoRenewAccountId, long autoRenewSeconds, long expirationSecond, String memo, long maxSupply, boolean paused, boolean accountsFrozenByDefault, boolean accountsKycGrantedByDefault, List<CustomFee> customFees, Bytes metadata, Key metadataKey) {
        this(tokenId, name, symbol, decimals, () -> totalSupply, treasuryAccountId, adminKey, kycKey, freezeKey, wipeKey, supplyKey, feeScheduleKey, pauseKey, lastUsedSerialNumber, deleted, tokenType, supplyType, autoRenewAccountId, autoRenewSeconds, expirationSecond, memo, maxSupply, paused, accountsFrozenByDefault, accountsKycGrantedByDefault, () -> customFees, metadata, metadataKey);
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

    /**
     * Convenience method to check if the tokenId has a value
     *
     * @return true of the tokenId has a value
     */
    public boolean hasTokenId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for tokenId if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if tokenId is null
     * @return the value for tokenId if it has a value, or else returns the default value
     */
    public TokenID tokenIdOrElse(@Nonnull final TokenID defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for tokenId if it has a value, or else throws an NPE.
     * value for the type.
     *
     * @return the value for tokenId if it has a value
     * @throws NullPointerException if tokenId is null
     */
    @Nonnull
    public TokenID tokenIdOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes the supplied {@link Consumer} if, and only if, the tokenId has a value
     *
     * @param ifPresent the {@link Consumer} to execute
     */
    public void ifTokenId(@Nonnull final Consumer<TokenID> ifPresent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convenience method to check if the treasuryAccountId has a value
     *
     * @return true of the treasuryAccountId has a value
     */
    public boolean hasTreasuryAccountId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for treasuryAccountId if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if treasuryAccountId is null
     * @return the value for treasuryAccountId if it has a value, or else returns the default value
     */
    public AccountID treasuryAccountIdOrElse(@Nonnull final AccountID defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for treasuryAccountId if it has a value, or else throws an NPE.
     * value for the type.
     *
     * @return the value for treasuryAccountId if it has a value
     * @throws NullPointerException if treasuryAccountId is null
     */
    @Nonnull
    public AccountID treasuryAccountIdOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes the supplied {@link Consumer} if, and only if, the treasuryAccountId has a value
     *
     * @param ifPresent the {@link Consumer} to execute
     */
    public void ifTreasuryAccountId(@Nonnull final Consumer<AccountID> ifPresent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convenience method to check if the adminKey has a value
     *
     * @return true of the adminKey has a value
     */
    public boolean hasAdminKey() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for adminKey if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if adminKey is null
     * @return the value for adminKey if it has a value, or else returns the default value
     */
    public Key adminKeyOrElse(@Nonnull final Key defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for adminKey if it has a value, or else throws an NPE.
     * value for the type.
     *
     * @return the value for adminKey if it has a value
     * @throws NullPointerException if adminKey is null
     */
    @Nonnull
    public Key adminKeyOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes the supplied {@link Consumer} if, and only if, the adminKey has a value
     *
     * @param ifPresent the {@link Consumer} to execute
     */
    public void ifAdminKey(@Nonnull final Consumer<Key> ifPresent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convenience method to check if the kycKey has a value
     *
     * @return true of the kycKey has a value
     */
    public boolean hasKycKey() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for kycKey if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if kycKey is null
     * @return the value for kycKey if it has a value, or else returns the default value
     */
    public Key kycKeyOrElse(@Nonnull final Key defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for kycKey if it has a value, or else throws an NPE.
     * value for the type.
     *
     * @return the value for kycKey if it has a value
     * @throws NullPointerException if kycKey is null
     */
    @Nonnull
    public Key kycKeyOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes the supplied {@link Consumer} if, and only if, the kycKey has a value
     *
     * @param ifPresent the {@link Consumer} to execute
     */
    public void ifKycKey(@Nonnull final Consumer<Key> ifPresent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convenience method to check if the freezeKey has a value
     *
     * @return true of the freezeKey has a value
     */
    public boolean hasFreezeKey() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for freezeKey if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if freezeKey is null
     * @return the value for freezeKey if it has a value, or else returns the default value
     */
    public Key freezeKeyOrElse(@Nonnull final Key defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for freezeKey if it has a value, or else throws an NPE.
     * value for the type.
     *
     * @return the value for freezeKey if it has a value
     * @throws NullPointerException if freezeKey is null
     */
    @Nonnull
    public Key freezeKeyOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes the supplied {@link Consumer} if, and only if, the freezeKey has a value
     *
     * @param ifPresent the {@link Consumer} to execute
     */
    public void ifFreezeKey(@Nonnull final Consumer<Key> ifPresent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convenience method to check if the wipeKey has a value
     *
     * @return true of the wipeKey has a value
     */
    public boolean hasWipeKey() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for wipeKey if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if wipeKey is null
     * @return the value for wipeKey if it has a value, or else returns the default value
     */
    public Key wipeKeyOrElse(@Nonnull final Key defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for wipeKey if it has a value, or else throws an NPE.
     * value for the type.
     *
     * @return the value for wipeKey if it has a value
     * @throws NullPointerException if wipeKey is null
     */
    @Nonnull
    public Key wipeKeyOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes the supplied {@link Consumer} if, and only if, the wipeKey has a value
     *
     * @param ifPresent the {@link Consumer} to execute
     */
    public void ifWipeKey(@Nonnull final Consumer<Key> ifPresent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convenience method to check if the supplyKey has a value
     *
     * @return true of the supplyKey has a value
     */
    public boolean hasSupplyKey() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for supplyKey if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if supplyKey is null
     * @return the value for supplyKey if it has a value, or else returns the default value
     */
    public Key supplyKeyOrElse(@Nonnull final Key defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for supplyKey if it has a value, or else throws an NPE.
     * value for the type.
     *
     * @return the value for supplyKey if it has a value
     * @throws NullPointerException if supplyKey is null
     */
    @Nonnull
    public Key supplyKeyOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes the supplied {@link Consumer} if, and only if, the supplyKey has a value
     *
     * @param ifPresent the {@link Consumer} to execute
     */
    public void ifSupplyKey(@Nonnull final Consumer<Key> ifPresent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convenience method to check if the feeScheduleKey has a value
     *
     * @return true of the feeScheduleKey has a value
     */
    public boolean hasFeeScheduleKey() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for feeScheduleKey if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if feeScheduleKey is null
     * @return the value for feeScheduleKey if it has a value, or else returns the default value
     */
    public Key feeScheduleKeyOrElse(@Nonnull final Key defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for feeScheduleKey if it has a value, or else throws an NPE.
     * value for the type.
     *
     * @return the value for feeScheduleKey if it has a value
     * @throws NullPointerException if feeScheduleKey is null
     */
    @Nonnull
    public Key feeScheduleKeyOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes the supplied {@link Consumer} if, and only if, the feeScheduleKey has a value
     *
     * @param ifPresent the {@link Consumer} to execute
     */
    public void ifFeeScheduleKey(@Nonnull final Consumer<Key> ifPresent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convenience method to check if the pauseKey has a value
     *
     * @return true of the pauseKey has a value
     */
    public boolean hasPauseKey() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for pauseKey if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if pauseKey is null
     * @return the value for pauseKey if it has a value, or else returns the default value
     */
    public Key pauseKeyOrElse(@Nonnull final Key defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for pauseKey if it has a value, or else throws an NPE.
     * value for the type.
     *
     * @return the value for pauseKey if it has a value
     * @throws NullPointerException if pauseKey is null
     */
    @Nonnull
    public Key pauseKeyOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes the supplied {@link Consumer} if, and only if, the pauseKey has a value
     *
     * @param ifPresent the {@link Consumer} to execute
     */
    public void ifPauseKey(@Nonnull final Consumer<Key> ifPresent) {
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
     * Convenience method to check if the metadataKey has a value
     *
     * @return true of the metadataKey has a value
     */
    public boolean hasMetadataKey() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for metadataKey if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if metadataKey is null
     * @return the value for metadataKey if it has a value, or else returns the default value
     */
    public Key metadataKeyOrElse(@Nonnull final Key defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for metadataKey if it has a value, or else throws an NPE.
     * value for the type.
     *
     * @return the value for metadataKey if it has a value
     * @throws NullPointerException if metadataKey is null
     */
    @Nonnull
    public Key metadataKeyOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes the supplied {@link Consumer} if, and only if, the metadataKey has a value
     *
     * @param ifPresent the {@link Consumer} to execute
     */
    public void ifMetadataKey(@Nonnull final Consumer<Key> ifPresent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * @return The custom fees of this token
     */
    public List<CustomFee> customFees() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * @return The total supply of this token
     */
    public long totalSupply() {
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

    /**
     * Builder class for easy creation, ideal for clean code where performance is not critical. In critical performance
     * paths use the constructor directly.
     */
    public static final class Builder {

        @Nullable
        private TokenID tokenId = null;

        @Nonnull
        private String name = "";

        @Nonnull
        private String symbol = "";

        private int decimals = 0;

        @Nullable
        private Supplier<Long> totalSupplySupplier = null;

        @Nullable
        private AccountID treasuryAccountId = null;

        @Nullable
        private Key adminKey = null;

        @Nullable
        private Key kycKey = null;

        @Nullable
        private Key freezeKey = null;

        @Nullable
        private Key wipeKey = null;

        @Nullable
        private Key supplyKey = null;

        @Nullable
        private Key feeScheduleKey = null;

        @Nullable
        private Key pauseKey = null;

        private long lastUsedSerialNumber = 0;

        private boolean deleted = false;

        private TokenType tokenType = TokenType.fromProtobufOrdinal(0);

        private TokenSupplyType supplyType = TokenSupplyType.fromProtobufOrdinal(0);

        @Nullable
        private AccountID autoRenewAccountId = null;

        private long autoRenewSeconds = 0;

        private long expirationSecond = 0;

        @Nonnull
        private String memo = "";

        private long maxSupply = 0;

        private boolean paused = false;

        private boolean accountsFrozenByDefault = false;

        private boolean accountsKycGrantedByDefault = false;

        @Nullable
        private Supplier<List<CustomFee>> customFeesSupplier = Collections::emptyList;

        @Nonnull
        private Bytes metadata = Bytes.EMPTY;

        @Nullable
        private Key metadataKey = null;

        /**
         * Create an empty builder
         */
        public Builder() {
        }

        /**
         * Create a pre-populated Builder.
         *
         * @param tokenId                     <b>(1)</b> The unique entity id of this token.
         * @param name                        <b>(2)</b> The human-readable name of this token. Need not be unique.
         *                                    Maximum length allowed is 100 bytes.
         * @param symbol                      <b>(3)</b> The human-readable symbol for the token. It is not necessarily
         *                                    unique. Maximum length allowed is 100 bytes.
         * @param decimals                    <b>(4)</b> The number of decimal places of this token. If decimals are 8
         *                                    or 11, then the number of whole
         *                                    tokens can be at most a few billions or millions, respectively. For
         *                                    example, it could match Bitcoin (21 million whole tokens with 8 decimals)
         *                                    or hbars (50 billion whole tokens with 8 decimals). It could even match
         *                                    Bitcoin with milli-satoshis (21 million whole tokens with 11 decimals).
         * @param totalSupplySupplier         <b>(5)</b> The total supply of this token wrapped in a Supplier.
         * @param treasuryAccountId           <b>(6)</b> The treasury account id of this token.
         * @param adminKey                    <b>(7)</b> (Optional) The admin key of this token. If this key is set, the
         *                                    token is mutable.
         *                                    A mutable token can be modified. If this key is not set on token creation,
         *                                    it cannot be modified.
         * @param kycKey                      <b>(8)</b> (Optional) The kyc key of this token.
         *                                    If this key is not set on token creation, it can only be set if the token
         *                                    has admin key set.
         * @param freezeKey                   <b>(9)</b> (Optional) The freeze key of this token. This key is needed for
         *                                    freezing the token.
         *                                    If this key is not set on token creation, it can only be set if the token
         *                                    has admin key set.
         * @param wipeKey                     <b>(10)</b> (Optional) The wipe key of this token. This key is needed for
         *                                    wiping the token.
         *                                    If this key is not set on token creation, it can only be set if the token
         *                                    has admin key set.
         * @param supplyKey                   <b>(11)</b> (Optional) The supply key of this token. This key is needed
         *                                    for minting or burning token.
         *                                    If this key is not set on token creation, it can only be set if the token
         *                                    has admin key set.
         * @param feeScheduleKey              <b>(12)</b> (Optional) The fee schedule key of this token. This key should
         *                                    be set, in order to make any
         *                                    changes to the custom fee schedule. If this key is not set on token
         *                                    creation, it can only be set if the token has admin key set.
         * @param pauseKey                    <b>(13)</b> (Optional) The pause key of this token. This key is needed for
         *                                    pausing the token.
         *                                    If this key is not set on token creation, it can only be set if the token
         *                                    has admin key set.
         * @param lastUsedSerialNumber        <b>(14)</b> The last used serial number of this token.
         * @param deleted                     <b>(15)</b> The flag indicating if this token is deleted.
         * @param tokenType                   <b>(16)</b> The type of this token. A token can be either FUNGIBLE_COMMON
         *                                    or NON_FUNGIBLE_UNIQUE.
         *                                    If it has been omitted during token creation, FUNGIBLE_COMMON type is
         *                                    used.
         * @param supplyType                  <b>(17)</b> The supply type of this token.A token can have either INFINITE
         *                                    or FINITE supply type.
         *                                    If it has been omitted during token creation, INFINITE type is used.
         * @param autoRenewAccountId          <b>(18)</b> The id of the account (if any) that the network will attempt
         *                                    to charge for the
         *                                    *                           token's auto-renewal upon expiration.
         * @param autoRenewSeconds            <b>(19)</b> The number of seconds the network should automatically extend
         *                                    the token's expiration by, if the
         *                                    token has a valid auto-renew account, and is not deleted upon expiration.
         *                                    If this is not provided in a allowed range on token creation, the
         *                                    transaction will fail with INVALID_AUTO_RENEWAL_PERIOD. The default values
         *                                    for the minimum period and maximum period are 30 days and 90 days,
         *                                    respectively.
         * @param expirationSecond            <b>(20)</b> The expiration time of the token, in seconds since the epoch.
         * @param memo                        <b>(21)</b> An optional description of the token with UTF-8 encoding up to
         *                                    100 bytes.
         * @param maxSupply                   <b>(22)</b> The maximum supply of this token.
         * @param paused                      <b>(23)</b> The flag indicating if this token is paused.
         * @param accountsFrozenByDefault     <b>(24)</b> The flag indicating if this token has accounts associated to
         *                                    it that are frozen by default.
         * @param accountsKycGrantedByDefault <b>(25)</b> The flag indicating if this token has accounts associated with
         *                                    it that are KYC granted by default.
         * @param customFeesSupplier          <b>(26)</b> (Optional) The custom fees of this token wrapped in a
         *                                    Supplier.
         * @param metadata                    <b>(27)</b> Metadata of the created token definition
         * @param metadataKey                 <b>(28)</b> The key which can change the metadata of a token
         *                                    (token definition and individual NFTs).
         */
        @SuppressWarnings("java:S107")
        public Builder(TokenID tokenId, String name, String symbol, int decimals, Supplier<Long> totalSupplySupplier, AccountID treasuryAccountId, Key adminKey, Key kycKey, Key freezeKey, Key wipeKey, Key supplyKey, Key feeScheduleKey, Key pauseKey, long lastUsedSerialNumber, boolean deleted, TokenType tokenType, TokenSupplyType supplyType, AccountID autoRenewAccountId, long autoRenewSeconds, long expirationSecond, String memo, long maxSupply, boolean paused, boolean accountsFrozenByDefault, boolean accountsKycGrantedByDefault, Supplier<List<CustomFee>> customFeesSupplier, Bytes metadata, Key metadataKey) {
            this.tokenId = tokenId;
            this.name = name != null ? name : "";
            this.symbol = symbol != null ? symbol : "";
            this.decimals = decimals;
            this.totalSupplySupplier = totalSupplySupplier;
            this.treasuryAccountId = treasuryAccountId;
            this.adminKey = adminKey;
            this.kycKey = kycKey;
            this.freezeKey = freezeKey;
            this.wipeKey = wipeKey;
            this.supplyKey = supplyKey;
            this.feeScheduleKey = feeScheduleKey;
            this.pauseKey = pauseKey;
            this.lastUsedSerialNumber = lastUsedSerialNumber;
            this.deleted = deleted;
            this.tokenType = tokenType;
            this.supplyType = supplyType;
            this.autoRenewAccountId = autoRenewAccountId;
            this.autoRenewSeconds = autoRenewSeconds;
            this.expirationSecond = expirationSecond;
            this.memo = memo != null ? memo : "";
            this.maxSupply = maxSupply;
            this.paused = paused;
            this.accountsFrozenByDefault = accountsFrozenByDefault;
            this.accountsKycGrantedByDefault = accountsKycGrantedByDefault;
            this.customFeesSupplier = customFeesSupplier;
            this.metadata = metadata != null ? metadata : Bytes.EMPTY;
            this.metadataKey = metadataKey;
        }

        /**
         * Build a new model record with data set on builder
         *
         * @return new model record with data set
         */
        public Token build() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(1)</b> The unique entity id of this token.
         *
         * @param tokenId value to set
         * @return builder to continue building with
         */
        public Builder tokenId(@Nullable TokenID tokenId) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(1)</b> The unique entity id of this token.
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder tokenId(TokenID.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(2)</b> The human-readable name of this token. Need not be unique. Maximum length allowed is 100 bytes.
         *
         * @param name value to set
         * @return builder to continue building with
         */
        public Builder name(@Nonnull String name) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(3)</b> The human-readable symbol for the token. It is not necessarily unique. Maximum length allowed is
         * 100 bytes.
         *
         * @param symbol value to set
         * @return builder to continue building with
         */
        public Builder symbol(@Nonnull String symbol) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(4)</b> The number of decimal places of this token. If decimals are 8 or 11, then the number of whole
         * tokens can be at most a few billions or millions, respectively. For example, it could match Bitcoin (21
         * million whole tokens with 8 decimals) or hbars (50 billion whole tokens with 8 decimals). It could even match
         * Bitcoin with milli-satoshis (21 million whole tokens with 11 decimals).
         *
         * @param decimals value to set
         * @return builder to continue building with
         */
        public Builder decimals(int decimals) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(5)</b> The total supply of this token.
         *
         * @param totalSupply value to set
         * @return builder to continue building with
         */
        public Builder totalSupply(long totalSupply) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(5)</b> The total supply of this token.
         *
         * @param totalSupplySupplier value to set
         * @return builder to continue building with
         */
        public Builder totalSupply(Supplier<Long> totalSupplySupplier) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(6)</b> The treasury account id of this token. This account receives the initial supply of
         * tokens as well as the tokens from the Token Mint operation once executed. The balance of the treasury account
         * is decreased when the Token Burn operation is executed.
         *
         * @param treasuryAccountId value to set
         * @return builder to continue building with
         */
        public Builder treasuryAccountId(@Nullable AccountID treasuryAccountId) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(7)</b> (Optional) The admin key of this token. If this key is set, the token is mutable.
         * A mutable token can be modified. If this key is not set on token creation, it cannot be modified.
         *
         * @param adminKey value to set
         * @return builder to continue building with
         */
        public Builder adminKey(@Nullable Key adminKey) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(7)</b> (Optional) The admin key of this token. If this key is set, the token is mutable.
         * A mutable token can be modified. If this key is not set on token creation, it cannot be modified.
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder adminKey(Key.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(8)</b> (Optional) The kyc key of this token.
         * If this key is not set on token creation, it can only be set if the token has admin key set.
         *
         * @param kycKey value to set
         * @return builder to continue building with
         */
        public Builder kycKey(@Nullable Key kycKey) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(8)</b> (Optional) The kyc key of this token.
         * If this key is not set on token creation, it can only be set if the token has admin key set.
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder kycKey(Key.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(9)</b> (Optional) The freeze key of this token. This key is needed for freezing the token.
         * If this key is not set on token creation, it can only be set if the token has admin key set.
         *
         * @param freezeKey value to set
         * @return builder to continue building with
         */
        public Builder freezeKey(@Nullable Key freezeKey) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(9)</b> (Optional) The freeze key of this token. This key is needed for freezing the token.
         * If this key is not set on token creation, it can only be set if the token has admin key set.
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder freezeKey(Key.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(10)</b> (Optional) The wipe key of this token. This key is needed for wiping the token.
         * If this key is not set on token creation, it can only be set if the token has admin key set.
         *
         * @param wipeKey value to set
         * @return builder to continue building with
         */
        public Builder wipeKey(@Nullable Key wipeKey) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(10)</b> (Optional) The wipe key of this token. This key is needed for wiping the token.
         * If this key is not set on token creation, it can only be set if the token has admin key set.
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder wipeKey(Key.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(11)</b> (Optional) The supply key of this token. This key is needed for minting or burning token.
         * If this key is not set on token creation, it can only be set if the token has admin key set.
         *
         * @param supplyKey value to set
         * @return builder to continue building with
         */
        public Builder supplyKey(@Nullable Key supplyKey) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(11)</b> (Optional) The supply key of this token. This key is needed for minting or burning token.
         * If this key is not set on token creation, it can only be set if the token has admin key set.
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder supplyKey(Key.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(12)</b> (Optional) The fee schedule key of this token. This key should be set, in order to make any
         * changes to the custom fee schedule. If this key is not set on token creation, it can only be set if the token
         * has admin key set.
         *
         * @param feeScheduleKey value to set
         * @return builder to continue building with
         */
        public Builder feeScheduleKey(@Nullable Key feeScheduleKey) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(12)</b> (Optional) The fee schedule key of this token. This key should be set, in order to make any
         * changes to the custom fee schedule. If this key is not set on token creation, it can only be set if the token
         * has admin key set.
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder feeScheduleKey(Key.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(13)</b> (Optional) The pause key of this token. This key is needed for pausing the token.
         * If this key is not set on token creation, it can only be set if the token has admin key set.
         *
         * @param pauseKey value to set
         * @return builder to continue building with
         */
        public Builder pauseKey(@Nullable Key pauseKey) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(13)</b> (Optional) The pause key of this token. This key is needed for pausing the token.
         * If this key is not set on token creation, it can only be set if the token has admin key set.
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder pauseKey(Key.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(14)</b> The last used serial number of this token.
         *
         * @param lastUsedSerialNumber value to set
         * @return builder to continue building with
         */
        public Builder lastUsedSerialNumber(long lastUsedSerialNumber) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(15)</b> The flag indicating if this token is deleted.
         *
         * @param deleted value to set
         * @return builder to continue building with
         */
        public Builder deleted(boolean deleted) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(16)</b> The type of this token. A token can be either FUNGIBLE_COMMON or NON_FUNGIBLE_UNIQUE.
         * If it has been omitted during token creation, FUNGIBLE_COMMON type is used.
         *
         * @param tokenType value to set
         * @return builder to continue building with
         */
        public Builder tokenType(TokenType tokenType) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(17)</b> The supply type of this token.A token can have either INFINITE or FINITE supply type.
         * If it has been omitted during token creation, INFINITE type is used.
         *
         * @param supplyType value to set
         * @return builder to continue building with
         */
        public Builder supplyType(TokenSupplyType supplyType) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(18)</b> The id of the account (if any) that the network will attempt to charge for the
         * token's auto-renewal upon expiration.
         *
         * @param autoRenewAccountId value to set
         * @return builder to continue building with
         */
        public Builder autoRenewAccountId(@Nullable AccountID autoRenewAccountId) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(19)</b> The number of seconds the network should automatically extend the token's expiration by, if the
         * token has a valid auto-renew account, and is not deleted upon expiration. If this is not provided in a
         * allowed range on token creation, the transaction will fail with INVALID_AUTO_RENEWAL_PERIOD. The default
         * values for the minimum period and maximum period are 30 days and 90 days, respectively.
         *
         * @param autoRenewSeconds value to set
         * @return builder to continue building with
         */
        public Builder autoRenewSeconds(long autoRenewSeconds) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(20)</b> The expiration time of the token, in seconds since the epoch.
         *
         * @param expirationSecond value to set
         * @return builder to continue building with
         */
        public Builder expirationSecond(long expirationSecond) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(21)</b> An optional description of the token with UTF-8 encoding up to 100 bytes.
         *
         * @param memo value to set
         * @return builder to continue building with
         */
        public Builder memo(@Nonnull String memo) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(22)</b> The maximum supply of this token.
         *
         * @param maxSupply value to set
         * @return builder to continue building with
         */
        public Builder maxSupply(long maxSupply) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(23)</b> The flag indicating if this token is paused.
         *
         * @param paused value to set
         * @return builder to continue building with
         */
        public Builder paused(boolean paused) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(24)</b> The flag indicating if this token has accounts associated to it that are frozen by default.
         *
         * @param accountsFrozenByDefault value to set
         * @return builder to continue building with
         */
        public Builder accountsFrozenByDefault(boolean accountsFrozenByDefault) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(25)</b> The flag indicating if this token has accounts associated with it that are KYC granted by
         * default.
         *
         * @param accountsKycGrantedByDefault value to set
         * @return builder to continue building with
         */
        public Builder accountsKycGrantedByDefault(boolean accountsKycGrantedByDefault) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(26)</b> (Optional) The custom fees of this token.
         *
         * @param customFees value to set
         * @return builder to continue building with
         */
        public Builder customFees(@Nonnull List<CustomFee> customFees) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(26)</b> (Optional) The custom fees of this token.
         *
         * @param customFeesSupplier value to set
         * @return builder to continue building with
         */
        public Builder customFees(@Nonnull Supplier<List<CustomFee>> customFeesSupplier) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(27)</b> Metadata of the created token definition
         *
         * @param metadata value to set
         * @return builder to continue building with
         */
        public Builder metadata(@Nonnull Bytes metadata) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(28)</b> The key which can change the metadata of a token
         * (token definition and individual NFTs).
         *
         * @param metadataKey value to set
         * @return builder to continue building with
         */
        public Builder metadataKey(@Nullable Key metadataKey) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(28)</b> The key which can change the metadata of a token
         * (token definition and individual NFTs).
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder metadataKey(Key.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
