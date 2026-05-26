// SPDX-License-Identifier: Apache-2.0
package com.hedera.hapi.node.state.token;

import static java.util.Objects.requireNonNull;
import static org.hiero.mirror.web3.utils.Suppliers.areSuppliersEqual;
import com.hedera.hapi.node.base.AccountID;
import com.hedera.hapi.node.base.TokenID;
import com.hedera.pbj.runtime.Codec;
import com.hedera.pbj.runtime.JsonCodec;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Representation of a Hedera Token Service token relationship entity in the network Merkle tree.
 * <p>
 * As with all network entities, a token relationship has a unique entity number pair, which is represented
 * with the account and the token involved in the relationship.
 *
 * @param tokenId <b>(1)</b> The token involved in this relation.It takes only positive
 * @param accountId <b>(2)</b> The account involved in this association.
 * @param balanceSupplier <b>(3)</b> The balanceSupplier of the token relationship wrapped in a Supplier.
 * @param frozen <b>(4)</b> The flags specifying the token relationship is frozen or not.
 * @param kycGranted <b>(5)</b> The flag indicating if the token relationship has been granted KYC.
 * @param automaticAssociation <b>(6)</b> The flag indicating if the token relationship was created using automatic association.
 * @param previousToken <b>(7)</b> The previous token id of account's association linked list
 * @param nextToken <b>(8)</b> The next token id of account's association linked list
 */
public record TokenRelation(@Nullable TokenID tokenId, @Nullable AccountID accountId, @Nullable Supplier<Long> balanceSupplier, boolean frozen, boolean kycGranted, boolean automaticAssociation, @Nullable TokenID previousToken, @Nullable TokenID nextToken) {

    /**
     * Protobuf codec for reading and writing in protobuf format
     */
    public static final Codec<TokenRelation> PROTOBUF = new com.hedera.hapi.node.state.token.codec.TokenRelationProtoCodec();

    /**
     * JSON codec for reading and writing in JSON format
     */
    public static final JsonCodec<TokenRelation> JSON = new com.hedera.hapi.node.state.token.codec.TokenRelationJsonCodec();

    /**
     * Default instance with all fields set to default values
     */
    public static final TokenRelation DEFAULT = newBuilder().build();

    /**
     * Create a pre-populated TokenRelation.
     *
     * @param tokenId <b>(1)</b> The token involved in this relation.It takes only positive,
     * @param accountId <b>(2)</b> The account involved in this association.,
     * @param balance <b>(3)</b> The balance of the token relationship.,
     * @param frozen <b>(4)</b> The flags specifying the token relationship is frozen or not.,
     * @param kycGranted <b>(5)</b> The flag indicating if the token relationship has been granted KYC.,
     * @param automaticAssociation <b>(6)</b> The flag indicating if the token relationship was created using automatic association.,
     * @param previousToken <b>(7)</b> The previous token id of account's association linked list,
     * @param nextToken <b>(8)</b> The next token id of account's association linked list
     */
    public TokenRelation(TokenID tokenId, AccountID accountId, long balance, boolean frozen, boolean kycGranted, boolean automaticAssociation, TokenID previousToken, TokenID nextToken) {
        this(tokenId, accountId, () -> balance, frozen, kycGranted, automaticAssociation, previousToken, nextToken);
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
     * Convenience method to check if the previousToken has a value
     *
     * @return true of the previousToken has a value
     */
    public boolean hasPreviousToken() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for previousToken if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if previousToken is null
     * @return the value for previousToken if it has a value, or else returns the default value
     */
    public TokenID previousTokenOrElse(@Nonnull final TokenID defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for previousToken if it has a value, or else throws an NPE.
     * value for the type.
     *
     * @return the value for previousToken if it has a value
     * @throws NullPointerException if previousToken is null
     */
    @Nonnull
    public TokenID previousTokenOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes the supplied {@link Consumer} if, and only if, the previousToken has a value
     *
     * @param ifPresent the {@link Consumer} to execute
     */
    public void ifPreviousToken(@Nonnull final Consumer<TokenID> ifPresent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convenience method to check if the nextToken has a value
     *
     * @return true of the nextToken has a value
     */
    public boolean hasNextToken() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for nextToken if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if nextToken is null
     * @return the value for nextToken if it has a value, or else returns the default value
     */
    public TokenID nextTokenOrElse(@Nonnull final TokenID defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for nextToken if it has a value, or else throws an NPE.
     * value for the type.
     *
     * @return the value for nextToken if it has a value
     * @throws NullPointerException if nextToken is null
     */
    @Nonnull
    public TokenID nextTokenOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes the supplied {@link Consumer} if, and only if, the nextToken has a value
     *
     * @param ifPresent the {@link Consumer} to execute
     */
    public void ifNextToken(@Nonnull final Consumer<TokenID> ifPresent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * @return The balance of the token relationship
     */
    public long balance() {
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

        @Nullable
        private AccountID accountId = null;

        @Nullable
        private Supplier<Long> balanceSupplier = null;

        private boolean frozen = false;

        private boolean kycGranted = false;

        private boolean automaticAssociation = false;

        @Nullable
        private TokenID previousToken = null;

        @Nullable
        private TokenID nextToken = null;

        /**
         * Create an empty builder
         */
        public Builder() {
        }

        /**
         * Create a pre-populated Builder.
         *
         * @param tokenId <b>(1)</b> The token involved in this relation.It takes only positive,
         * @param accountId <b>(2)</b> The account involved in this association.,
         * @param balanceSupplier <b>(3)</b> The balance of the token relationship wrapped in a Supplier.,
         * @param frozen <b>(4)</b> The flags specifying the token relationship is frozen or not.,
         * @param kycGranted <b>(5)</b> The flag indicating if the token relationship has been granted KYC.,
         * @param automaticAssociation <b>(6)</b> The flag indicating if the token relationship was created using automatic association.,
         * @param previousToken <b>(7)</b> The previous token id of account's association linked list,
         * @param nextToken <b>(8)</b> The next token id of account's association linked list
         */
        @SuppressWarnings("java:S107")
        public Builder(TokenID tokenId, AccountID accountId, Supplier<Long> balanceSupplier, boolean frozen, boolean kycGranted, boolean automaticAssociation, TokenID previousToken, TokenID nextToken) {
            this.tokenId = tokenId;
            this.accountId = accountId;
            this.balanceSupplier = balanceSupplier;
            this.frozen = frozen;
            this.kycGranted = kycGranted;
            this.automaticAssociation = automaticAssociation;
            this.previousToken = previousToken;
            this.nextToken = nextToken;
        }

        /**
         * Build a new model record with data set on builder
         *
         * @return new model record with data set
         */
        public TokenRelation build() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(1)</b> The token involved in this relation.It takes only positive
         *
         * @param tokenId value to set
         * @return builder to continue building with
         */
        public Builder tokenId(@Nullable TokenID tokenId) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(1)</b> The token involved in this relation.It takes only positive
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder tokenId(TokenID.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(2)</b> The account involved in this association.
         *
         * @param accountId value to set
         * @return builder to continue building with
         */
        public Builder accountId(@Nullable AccountID accountId) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(2)</b> The account involved in this association.
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder accountId(AccountID.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(3)</b> The balance of the token relationship.
         *
         * @param balance value to set
         * @return builder to continue building with
         */
        public Builder balance(long balance) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(3)</b> The balance of the token relationship.
         *
         * @param balanceSupplier value to set
         * @return builder to continue building with
         */
        public Builder balanceSupplier(@Nullable Supplier<Long> balanceSupplier) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(4)</b> The flags specifying the token relationship is frozen or not.
         *
         * @param frozen value to set
         * @return builder to continue building with
         */
        public Builder frozen(boolean frozen) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(5)</b> The flag indicating if the token relationship has been granted KYC.
         *
         * @param kycGranted value to set
         * @return builder to continue building with
         */
        public Builder kycGranted(boolean kycGranted) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(6)</b> The flag indicating if the token relationship was created using automatic association.
         *
         * @param automaticAssociation value to set
         * @return builder to continue building with
         */
        public Builder automaticAssociation(boolean automaticAssociation) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(7)</b> The previous token id of account's association linked list
         *
         * @param previousToken value to set
         * @return builder to continue building with
         */
        public Builder previousToken(@Nullable TokenID previousToken) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(7)</b> The previous token id of account's association linked list
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder previousToken(TokenID.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(8)</b> The next token id of account's association linked list
         *
         * @param nextToken value to set
         * @return builder to continue building with
         */
        public Builder nextToken(@Nullable TokenID nextToken) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(8)</b> The next token id of account's association linked list
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder nextToken(TokenID.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
