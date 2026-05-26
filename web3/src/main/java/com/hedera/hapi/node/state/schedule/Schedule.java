// SPDX-License-Identifier: Apache-2.0
package com.hedera.hapi.node.state.schedule;

import static java.util.Objects.requireNonNull;
import com.hedera.hapi.node.base.AccountID;
import com.hedera.hapi.node.base.Key;
import com.hedera.hapi.node.base.ScheduleID;
import com.hedera.hapi.node.base.Timestamp;
import com.hedera.hapi.node.scheduled.SchedulableTransactionBody;
import com.hedera.hapi.node.transaction.TransactionBody;
import com.hedera.pbj.runtime.Codec;
import com.hedera.pbj.runtime.JsonCodec;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Representation of a Hedera Schedule entry in the network Merkle tree.<br/>
 * A Schedule represents a request to run a transaction _at some future time_
 * either when the `Schedule` expires (if long term schedules are enabled and
 * `wait_for_expiry` is true) or as soon as the `Schedule` has gathered
 * enough signatures via any combination of the `scheduleCreate` and 0 or more
 * subsequent `scheduleSign` transactions.
 *
 * @param scheduleId <b>(1)</b> This schedule's ID within the global network state.
 *                   <p>
 *                   This value SHALL be unique within the network.
 * @param deleted <b>(2)</b> A flag indicating this schedule is deleted.
 *                <p>
 *                A schedule SHALL either be executed or deleted, but never both.
 * @param executed <b>(3)</b> A flag indicating this schedule has executed.
 *                 <p>
 *                 A schedule SHALL either be executed or deleted, but never both.
 * @param waitForExpiry <b>(4)</b> A schedule flag to wait for expiration before executing.
 *                      <p>
 *                      A schedule SHALL be executed immediately when all necessary signatures
 *                      are gathered, unless this flag is set.<br/>
 *                      If this flag is set, the schedule SHALL wait until the consensus time
 *                      reaches `expiration_time_provided`, when signatures MUST again be
 *                      verified. If all required signatures are present at that time, the
 *                      schedule SHALL be executed. Otherwise the schedule SHALL expire without
 *                      execution.
 *                      <p>
 *                      Note that a schedule is always removed from state after it expires,
 *                      regardless of whether it was executed or not.
 * @param memo <b>(5)</b> A short description for this schedule.
 *             <p>
 *             This value, if set, MUST NOT exceed `transaction.maxMemoUtf8Bytes`
 *             (default 100) bytes when encoded as UTF-8.
 * @param schedulerAccountId <b>(6)</b> The scheduler account for this schedule.
 *                           <p>
 *                           This SHALL be the account that submitted the original
 *                           ScheduleCreate transaction.
 * @param payerAccountId <b>(7)</b> The explicit payer account for the scheduled transaction.
 *                       <p>
 *                       If set, this account SHALL be added to the accounts that MUST sign the
 *                       schedule before it may execute.
 * @param adminKey <b>(8)</b> The admin key for this schedule.
 *                 <p>
 *                 This key, if set, MUST sign any `schedule_delete` transaction.<br/>
 *                 If not set, then this schedule SHALL NOT be deleted, and any
 *                 `schedule_delete` transaction for this schedule SHALL fail.
 * @param scheduleValidStart <b>(9)</b> The transaction valid start value for this schedule.
 *                           <p>
 *                           This MUST be set, and SHALL be copied from the `TransactionID` of
 *                           the original `schedule_create` transaction.
 * @param providedExpirationSecond <b>(10)</b> The requested expiration time of the schedule if provided by the user.
 *                                 <p>
 *                                 If not provided in the `schedule_create` transaction, this SHALL be set
 *                                 to a default value equal to the current consensus time, forward offset by
 *                                 the maximum schedule expiration time in the current dynamic network
 *                                 configuration (typically 62 days).<br/>
 *                                 The actual `calculated_expiration_second` MAY be "earlier" than this,
 *                                 but MUST NOT be later.
 * @param calculatedExpirationSecond <b>(11)</b> The calculated expiration time of the schedule.
 *                                   <p>
 *                                   This SHALL be calculated from the requested expiration time in the
 *                                   `schedule_create` transaction, and limited by the maximum expiration time
 *                                   in the current dynamic network configuration (typically 62 days).
 *                                   <p>
 *                                   The schedule SHALL be removed from global network state after the network
 *                                   reaches a consensus time greater than or equal to this value.
 * @param resolutionTime <b>(12)</b> The consensus timestamp of the transaction that executed or deleted this schedule.
 *                       <p>
 *                       This value SHALL be set to the `current_consensus_time` when a
 *                       `schedule_delete` transaction is completed.<br/>
 *                       This value SHALL be set to the `current_consensus_time` when the
 *                       scheduled transaction is executed, either as a result of gathering the
 *                       final required signature, or, if long-term schedule execution is enabled,
 *                       at the requested execution time.
 * @param scheduledTransaction <b>(13)</b> The scheduled transaction to execute.
 *                             <p>
 *                             This MUST be one of the transaction types permitted in the current value
 *                             of the `schedule.whitelist` in the dynamic network configuration.
 * @param originalCreateTransaction <b>(14)</b> The full transaction that created this schedule.
 *                                  <p>
 *                                  This is primarily used for duplicate schedule create detection. This is
 *                                  also the source of the parent transaction ID, from which the child
 *                                  transaction ID is derived when the `scheduled_transaction` is executed.
 * @param signatoriesSupplier <b>(15)</b> All of the "primitive" keys that have already signed this schedule in a supplier.
 *                    <p>
 *                    The scheduled transaction SHALL NOT be executed before this list is
 *                    sufficient to "activate" the required keys for the scheduled transaction.<br/>
 *                    A Key SHALL NOT be stored in this list unless the corresponding private
 *                    key has signed either the original `schedule_create` transaction or a
 *                    subsequent `schedule_sign` transaction intended for, and referencing to,
 *                    this specific schedule.
 *                    <p>
 *                    The only keys stored are "primitive" keys (ED25519 or ECDSA_SECP256K1) in
 *                    order to ensure that any key list or threshold keys are correctly handled,
 *                    regardless of signing order, intervening changes, or other situations.
 *                    The `scheduled_transaction` SHALL execute only if, at the time of
 *                    execution, this list contains sufficient public keys to satisfy the
 *                    full requirements for signature on that transaction.
 */
public record Schedule(@Nullable ScheduleID scheduleId, boolean deleted, boolean executed, boolean waitForExpiry, @Nonnull String memo, @Nullable AccountID schedulerAccountId, @Nullable AccountID payerAccountId, @Nullable Key adminKey, @Nullable Timestamp scheduleValidStart, long providedExpirationSecond, long calculatedExpirationSecond, @Nullable Timestamp resolutionTime, @Nullable SchedulableTransactionBody scheduledTransaction, @Nullable TransactionBody originalCreateTransaction, @Nonnull Supplier<List<Key>> signatoriesSupplier) {

    /**
     * Protobuf codec for reading and writing in protobuf format
     */
    public static final Codec<Schedule> PROTOBUF = new com.hedera.hapi.node.state.schedule.codec.ScheduleProtoCodec();

    /**
     * JSON codec for reading and writing in JSON format
     */
    public static final JsonCodec<Schedule> JSON = new com.hedera.hapi.node.state.schedule.codec.ScheduleJsonCodec();

    /**
     * Default instance with all fields set to default values
     */
    public static final Schedule DEFAULT = newBuilder().build();

    /**
     * Create a pre-populated Schedule.
     *
     * @param scheduleId <b>(1)</b> This schedule's ID within the global network state.
     *                   <p>
     *                   This value SHALL be unique within the network.,
     * @param deleted <b>(2)</b> A flag indicating this schedule is deleted.
     *                <p>
     *                A schedule SHALL either be executed or deleted, but never both.,
     * @param executed <b>(3)</b> A flag indicating this schedule has executed.
     *                 <p>
     *                 A schedule SHALL either be executed or deleted, but never both.,
     * @param waitForExpiry <b>(4)</b> A schedule flag to wait for expiration before executing.
     *                      <p>
     *                      A schedule SHALL be executed immediately when all necessary signatures
     *                      are gathered, unless this flag is set.<br/>
     *                      If this flag is set, the schedule SHALL wait until the consensus time
     *                      reaches `expiration_time_provided`, when signatures MUST again be
     *                      verified. If all required signatures are present at that time, the
     *                      schedule SHALL be executed. Otherwise the schedule SHALL expire without
     *                      execution.
     *                      <p>
     *                      Note that a schedule is always removed from state after it expires,
     *                      regardless of whether it was executed or not.,
     * @param memo <b>(5)</b> A short description for this schedule.
     *             <p>
     *             This value, if set, MUST NOT exceed `transaction.maxMemoUtf8Bytes`
     *             (default 100) bytes when encoded as UTF-8.,
     * @param schedulerAccountId <b>(6)</b> The scheduler account for this schedule.
     *                           <p>
     *                           This SHALL be the account that submitted the original
     *                           ScheduleCreate transaction.,
     * @param payerAccountId <b>(7)</b> The explicit payer account for the scheduled transaction.
     *                       <p>
     *                       If set, this account SHALL be added to the accounts that MUST sign the
     *                       schedule before it may execute.,
     * @param adminKey <b>(8)</b> The admin key for this schedule.
     *                 <p>
     *                 This key, if set, MUST sign any `schedule_delete` transaction.<br/>
     *                 If not set, then this schedule SHALL NOT be deleted, and any
     *                 `schedule_delete` transaction for this schedule SHALL fail.,
     * @param scheduleValidStart <b>(9)</b> The transaction valid start value for this schedule.
     *                           <p>
     *                           This MUST be set, and SHALL be copied from the `TransactionID` of
     *                           the original `schedule_create` transaction.,
     * @param providedExpirationSecond <b>(10)</b> The requested expiration time of the schedule if provided by the user.
     *                                 <p>
     *                                 If not provided in the `schedule_create` transaction, this SHALL be set
     *                                 to a default value equal to the current consensus time, forward offset by
     *                                 the maximum schedule expiration time in the current dynamic network
     *                                 configuration (typically 62 days).<br/>
     *                                 The actual `calculated_expiration_second` MAY be "earlier" than this,
     *                                 but MUST NOT be later.,
     * @param calculatedExpirationSecond <b>(11)</b> The calculated expiration time of the schedule.
     *                                   <p>
     *                                   This SHALL be calculated from the requested expiration time in the
     *                                   `schedule_create` transaction, and limited by the maximum expiration time
     *                                   in the current dynamic network configuration (typically 62 days).
     *                                   <p>
     *                                   The schedule SHALL be removed from global network state after the network
     *                                   reaches a consensus time greater than or equal to this value.,
     * @param resolutionTime <b>(12)</b> The consensus timestamp of the transaction that executed or deleted this schedule.
     *                       <p>
     *                       This value SHALL be set to the `current_consensus_time` when a
     *                       `schedule_delete` transaction is completed.<br/>
     *                       This value SHALL be set to the `current_consensus_time` when the
     *                       scheduled transaction is executed, either as a result of gathering the
     *                       final required signature, or, if long-term schedule execution is enabled,
     *                       at the requested execution time.,
     * @param scheduledTransaction <b>(13)</b> The scheduled transaction to execute.
     *                             <p>
     *                             This MUST be one of the transaction types permitted in the current value
     *                             of the `schedule.whitelist` in the dynamic network configuration.,
     * @param originalCreateTransaction <b>(14)</b> The full transaction that created this schedule.
     *                                  <p>
     *                                  This is primarily used for duplicate schedule create detection. This is
     *                                  also the source of the parent transaction ID, from which the child
     *                                  transaction ID is derived when the `scheduled_transaction` is executed.,
     * @param signatories <b>(15)</b> All of the "primitive" keys that have already signed this schedule.
     *                    <p>
     *                    The scheduled transaction SHALL NOT be executed before this list is
     *                    sufficient to "activate" the required keys for the scheduled transaction.<br/>
     *                    A Key SHALL NOT be stored in this list unless the corresponding private
     *                    key has signed either the original `schedule_create` transaction or a
     *                    subsequent `schedule_sign` transaction intended for, and referencing to,
     *                    this specific schedule.
     *                    <p>
     *                    The only keys stored are "primitive" keys (ED25519 or ECDSA_SECP256K1) in
     *                    order to ensure that any key list or threshold keys are correctly handled,
     *                    regardless of signing order, intervening changes, or other situations.
     *                    The `scheduled_transaction` SHALL execute only if, at the time of
     *                    execution, this list contains sufficient public keys to satisfy the
     *                    full requirements for signature on that transaction.
     */
    public Schedule(ScheduleID scheduleId, boolean deleted, boolean executed, boolean waitForExpiry, String memo, AccountID schedulerAccountId, AccountID payerAccountId, Key adminKey, Timestamp scheduleValidStart, long providedExpirationSecond, long calculatedExpirationSecond, Timestamp resolutionTime, SchedulableTransactionBody scheduledTransaction, TransactionBody originalCreateTransaction, List<Key> signatories) {
        this(scheduleId, deleted, executed, waitForExpiry, memo, schedulerAccountId, payerAccountId, adminKey, scheduleValidStart, providedExpirationSecond, calculatedExpirationSecond, resolutionTime, scheduledTransaction, originalCreateTransaction, () -> signatories == null ? Collections.emptyList() : signatories);
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
     * Convenience method to check if the scheduleId has a value
     *
     * @return true of the scheduleId has a value
     */
    public boolean hasScheduleId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for scheduleId if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if scheduleId is null
     * @return the value for scheduleId if it has a value, or else returns the default value
     */
    public ScheduleID scheduleIdOrElse(@Nonnull final ScheduleID defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for scheduleId if it has a value, or else throws an NPE.
     * value for the type.
     *
     * @return the value for scheduleId if it has a value
     * @throws NullPointerException if scheduleId is null
     */
    @Nonnull
    public ScheduleID scheduleIdOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes the supplied {@link Consumer} if, and only if, the scheduleId has a value
     *
     * @param ifPresent the {@link Consumer} to execute
     */
    public void ifScheduleId(@Nonnull final Consumer<ScheduleID> ifPresent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convenience method to check if the schedulerAccountId has a value
     *
     * @return true of the schedulerAccountId has a value
     */
    public boolean hasSchedulerAccountId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for schedulerAccountId if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if schedulerAccountId is null
     * @return the value for schedulerAccountId if it has a value, or else returns the default value
     */
    public AccountID schedulerAccountIdOrElse(@Nonnull final AccountID defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for schedulerAccountId if it has a value, or else throws an NPE.
     * value for the type.
     *
     * @return the value for schedulerAccountId if it has a value
     * @throws NullPointerException if schedulerAccountId is null
     */
    @Nonnull
    public AccountID schedulerAccountIdOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes the supplied {@link Consumer} if, and only if, the schedulerAccountId has a value
     *
     * @param ifPresent the {@link Consumer} to execute
     */
    public void ifSchedulerAccountId(@Nonnull final Consumer<AccountID> ifPresent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convenience method to check if the payerAccountId has a value
     *
     * @return true of the payerAccountId has a value
     */
    public boolean hasPayerAccountId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for payerAccountId if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if payerAccountId is null
     * @return the value for payerAccountId if it has a value, or else returns the default value
     */
    public AccountID payerAccountIdOrElse(@Nonnull final AccountID defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for payerAccountId if it has a value, or else throws an NPE.
     * value for the type.
     *
     * @return the value for payerAccountId if it has a value
     * @throws NullPointerException if payerAccountId is null
     */
    @Nonnull
    public AccountID payerAccountIdOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes the supplied {@link Consumer} if, and only if, the payerAccountId has a value
     *
     * @param ifPresent the {@link Consumer} to execute
     */
    public void ifPayerAccountId(@Nonnull final Consumer<AccountID> ifPresent) {
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
     * Convenience method to check if the scheduleValidStart has a value
     *
     * @return true of the scheduleValidStart has a value
     */
    public boolean hasScheduleValidStart() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for scheduleValidStart if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if scheduleValidStart is null
     * @return the value for scheduleValidStart if it has a value, or else returns the default value
     */
    public Timestamp scheduleValidStartOrElse(@Nonnull final Timestamp defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for scheduleValidStart if it has a value, or else throws an NPE.
     * value for the type.
     *
     * @return the value for scheduleValidStart if it has a value
     * @throws NullPointerException if scheduleValidStart is null
     */
    @Nonnull
    public Timestamp scheduleValidStartOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes the supplied {@link Consumer} if, and only if, the scheduleValidStart has a value
     *
     * @param ifPresent the {@link Consumer} to execute
     */
    public void ifScheduleValidStart(@Nonnull final Consumer<Timestamp> ifPresent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convenience method to check if the resolutionTime has a value
     *
     * @return true of the resolutionTime has a value
     */
    public boolean hasResolutionTime() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for resolutionTime if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if resolutionTime is null
     * @return the value for resolutionTime if it has a value, or else returns the default value
     */
    public Timestamp resolutionTimeOrElse(@Nonnull final Timestamp defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for resolutionTime if it has a value, or else throws an NPE.
     * value for the type.
     *
     * @return the value for resolutionTime if it has a value
     * @throws NullPointerException if resolutionTime is null
     */
    @Nonnull
    public Timestamp resolutionTimeOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes the supplied {@link Consumer} if, and only if, the resolutionTime has a value
     *
     * @param ifPresent the {@link Consumer} to execute
     */
    public void ifResolutionTime(@Nonnull final Consumer<Timestamp> ifPresent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convenience method to check if the scheduledTransaction has a value
     *
     * @return true of the scheduledTransaction has a value
     */
    public boolean hasScheduledTransaction() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for scheduledTransaction if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if scheduledTransaction is null
     * @return the value for scheduledTransaction if it has a value, or else returns the default value
     */
    public SchedulableTransactionBody scheduledTransactionOrElse(@Nonnull final SchedulableTransactionBody defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for scheduledTransaction if it has a value, or else throws an NPE.
     * value for the type.
     *
     * @return the value for scheduledTransaction if it has a value
     * @throws NullPointerException if scheduledTransaction is null
     */
    @Nonnull
    public SchedulableTransactionBody scheduledTransactionOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes the supplied {@link Consumer} if, and only if, the scheduledTransaction has a value
     *
     * @param ifPresent the {@link Consumer} to execute
     */
    public void ifScheduledTransaction(@Nonnull final Consumer<SchedulableTransactionBody> ifPresent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convenience method to check if the originalCreateTransaction has a value
     *
     * @return true of the originalCreateTransaction has a value
     */
    public boolean hasOriginalCreateTransaction() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for originalCreateTransaction if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if originalCreateTransaction is null
     * @return the value for originalCreateTransaction if it has a value, or else returns the default value
     */
    public TransactionBody originalCreateTransactionOrElse(@Nonnull final TransactionBody defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for originalCreateTransaction if it has a value, or else throws an NPE.
     * value for the type.
     *
     * @return the value for originalCreateTransaction if it has a value
     * @throws NullPointerException if originalCreateTransaction is null
     */
    @Nonnull
    public TransactionBody originalCreateTransactionOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes the supplied {@link Consumer} if, and only if, the originalCreateTransaction has a value
     *
     * @param ifPresent the {@link Consumer} to execute
     */
    public void ifOriginalCreateTransaction(@Nonnull final Consumer<TransactionBody> ifPresent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public List<Key> signatories() {
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
        private ScheduleID scheduleId = null;

        private boolean deleted = false;

        private boolean executed = false;

        private boolean waitForExpiry = false;

        @Nonnull
        private String memo = "";

        @Nullable
        private AccountID schedulerAccountId = null;

        @Nullable
        private AccountID payerAccountId = null;

        @Nullable
        private Key adminKey = null;

        @Nullable
        private Timestamp scheduleValidStart = null;

        private long providedExpirationSecond = 0;

        private long calculatedExpirationSecond = 0;

        @Nullable
        private Timestamp resolutionTime = null;

        @Nullable
        private SchedulableTransactionBody scheduledTransaction = null;

        @Nullable
        private TransactionBody originalCreateTransaction = null;

        @Nonnull
        private Supplier<List<Key>> signatoriesSupplier = Collections::emptyList;

        /**
         * Create an empty builder
         */
        public Builder() {
        }

        /**
         * Create a pre-populated Builder.
         *
         * @param scheduleId <b>(1)</b> This schedule's ID within the global network state.
         *                   <p>
         *                   This value SHALL be unique within the network.,
         * @param deleted <b>(2)</b> A flag indicating this schedule is deleted.
         *                <p>
         *                A schedule SHALL either be executed or deleted, but never both.,
         * @param executed <b>(3)</b> A flag indicating this schedule has executed.
         *                 <p>
         *                 A schedule SHALL either be executed or deleted, but never both.,
         * @param waitForExpiry <b>(4)</b> A schedule flag to wait for expiration before executing.
         *                      <p>
         *                      A schedule SHALL be executed immediately when all necessary signatures
         *                      are gathered, unless this flag is set.<br/>
         *                      If this flag is set, the schedule SHALL wait until the consensus time
         *                      reaches `expiration_time_provided`, when signatures MUST again be
         *                      verified. If all required signatures are present at that time, the
         *                      schedule SHALL be executed. Otherwise the schedule SHALL expire without
         *                      execution.
         *                      <p>
         *                      Note that a schedule is always removed from state after it expires,
         *                      regardless of whether it was executed or not.,
         * @param memo <b>(5)</b> A short description for this schedule.
         *             <p>
         *             This value, if set, MUST NOT exceed `transaction.maxMemoUtf8Bytes`
         *             (default 100) bytes when encoded as UTF-8.,
         * @param schedulerAccountId <b>(6)</b> The scheduler account for this schedule.
         *                           <p>
         *                           This SHALL be the account that submitted the original
         *                           ScheduleCreate transaction.,
         * @param payerAccountId <b>(7)</b> The explicit payer account for the scheduled transaction.
         *                       <p>
         *                       If set, this account SHALL be added to the accounts that MUST sign the
         *                       schedule before it may execute.,
         * @param adminKey <b>(8)</b> The admin key for this schedule.
         *                 <p>
         *                 This key, if set, MUST sign any `schedule_delete` transaction.<br/>
         *                 If not set, then this schedule SHALL NOT be deleted, and any
         *                 `schedule_delete` transaction for this schedule SHALL fail.,
         * @param scheduleValidStart <b>(9)</b> The transaction valid start value for this schedule.
         *                           <p>
         *                           This MUST be set, and SHALL be copied from the `TransactionID` of
         *                           the original `schedule_create` transaction.,
         * @param providedExpirationSecond <b>(10)</b> The requested expiration time of the schedule if provided by the user.
         *                                 <p>
         *                                 If not provided in the `schedule_create` transaction, this SHALL be set
         *                                 to a default value equal to the current consensus time, forward offset by
         *                                 the maximum schedule expiration time in the current dynamic network
         *                                 configuration (typically 62 days).<br/>
         *                                 The actual `calculated_expiration_second` MAY be "earlier" than this,
         *                                 but MUST NOT be later.,
         * @param calculatedExpirationSecond <b>(11)</b> The calculated expiration time of the schedule.
         *                                   <p>
         *                                   This SHALL be calculated from the requested expiration time in the
         *                                   `schedule_create` transaction, and limited by the maximum expiration time
         *                                   in the current dynamic network configuration (typically 62 days).
         *                                   <p>
         *                                   The schedule SHALL be removed from global network state after the network
         *                                   reaches a consensus time greater than or equal to this value.,
         * @param resolutionTime <b>(12)</b> The consensus timestamp of the transaction that executed or deleted this schedule.
         *                       <p>
         *                       This value SHALL be set to the `current_consensus_time` when a
         *                       `schedule_delete` transaction is completed.<br/>
         *                       This value SHALL be set to the `current_consensus_time` when the
         *                       scheduled transaction is executed, either as a result of gathering the
         *                       final required signature, or, if long-term schedule execution is enabled,
         *                       at the requested execution time.,
         * @param scheduledTransaction <b>(13)</b> The scheduled transaction to execute.
         *                             <p>
         *                             This MUST be one of the transaction types permitted in the current value
         *                             of the `schedule.whitelist` in the dynamic network configuration.,
         * @param originalCreateTransaction <b>(14)</b> The full transaction that created this schedule.
         *                                  <p>
         *                                  This is primarily used for duplicate schedule create detection. This is
         *                                  also the source of the parent transaction ID, from which the child
         *                                  transaction ID is derived when the `scheduled_transaction` is executed.,
         * @param signatoriesSupplier <b>(15)</b> All of the "primitive" keys that have already signed this schedule in a supplier.
         *                    <p>
         *                    The scheduled transaction SHALL NOT be executed before this list is
         *                    sufficient to "activate" the required keys for the scheduled transaction.<br/>
         *                    A Key SHALL NOT be stored in this list unless the corresponding private
         *                    key has signed either the original `schedule_create` transaction or a
         *                    subsequent `schedule_sign` transaction intended for, and referencing to,
         *                    this specific schedule.
         *                    <p>
         *                    The only keys stored are "primitive" keys (ED25519 or ECDSA_SECP256K1) in
         *                    order to ensure that any key list or threshold keys are correctly handled,
         *                    regardless of signing order, intervening changes, or other situations.
         *                    The `scheduled_transaction` SHALL execute only if, at the time of
         *                    execution, this list contains sufficient public keys to satisfy the
         *                    full requirements for signature on that transaction.
         */
        public Builder(ScheduleID scheduleId, boolean deleted, boolean executed, boolean waitForExpiry, String memo, AccountID schedulerAccountId, AccountID payerAccountId, Key adminKey, Timestamp scheduleValidStart, long providedExpirationSecond, long calculatedExpirationSecond, Timestamp resolutionTime, SchedulableTransactionBody scheduledTransaction, TransactionBody originalCreateTransaction, Supplier<List<Key>> signatoriesSupplier) {
            this.scheduleId = scheduleId;
            this.deleted = deleted;
            this.executed = executed;
            this.waitForExpiry = waitForExpiry;
            this.memo = memo != null ? memo : "";
            this.schedulerAccountId = schedulerAccountId;
            this.payerAccountId = payerAccountId;
            this.adminKey = adminKey;
            this.scheduleValidStart = scheduleValidStart;
            this.providedExpirationSecond = providedExpirationSecond;
            this.calculatedExpirationSecond = calculatedExpirationSecond;
            this.resolutionTime = resolutionTime;
            this.scheduledTransaction = scheduledTransaction;
            this.originalCreateTransaction = originalCreateTransaction;
            this.signatoriesSupplier = signatoriesSupplier;
        }

        /**
         * Build a new model record with data set on builder
         *
         * @return new model record with data set
         */
        public Schedule build() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(1)</b> This schedule's ID within the global network state.
         * <p>
         * This value SHALL be unique within the network.
         *
         * @param scheduleId value to set
         * @return builder to continue building with
         */
        public Builder scheduleId(@Nullable ScheduleID scheduleId) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(1)</b> This schedule's ID within the global network state.
         * <p>
         * This value SHALL be unique within the network.
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder scheduleId(ScheduleID.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(2)</b> A flag indicating this schedule is deleted.
         * <p>
         * A schedule SHALL either be executed or deleted, but never both.
         *
         * @param deleted value to set
         * @return builder to continue building with
         */
        public Builder deleted(boolean deleted) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(3)</b> A flag indicating this schedule has executed.
         * <p>
         * A schedule SHALL either be executed or deleted, but never both.
         *
         * @param executed value to set
         * @return builder to continue building with
         */
        public Builder executed(boolean executed) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(4)</b> A schedule flag to wait for expiration before executing.
         * <p>
         * A schedule SHALL be executed immediately when all necessary signatures
         * are gathered, unless this flag is set.<br/>
         * If this flag is set, the schedule SHALL wait until the consensus time
         * reaches `expiration_time_provided`, when signatures MUST again be
         * verified. If all required signatures are present at that time, the
         * schedule SHALL be executed. Otherwise the schedule SHALL expire without
         * execution.
         * <p>
         * Note that a schedule is always removed from state after it expires,
         * regardless of whether it was executed or not.
         *
         * @param waitForExpiry value to set
         * @return builder to continue building with
         */
        public Builder waitForExpiry(boolean waitForExpiry) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(5)</b> A short description for this schedule.
         * <p>
         * This value, if set, MUST NOT exceed `transaction.maxMemoUtf8Bytes`
         * (default 100) bytes when encoded as UTF-8.
         *
         * @param memo value to set
         * @return builder to continue building with
         */
        public Builder memo(@Nonnull String memo) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(6)</b> The scheduler account for this schedule.
         * <p>
         * This SHALL be the account that submitted the original
         * ScheduleCreate transaction.
         *
         * @param schedulerAccountId value to set
         * @return builder to continue building with
         */
        public Builder schedulerAccountId(@Nullable AccountID schedulerAccountId) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(6)</b> The scheduler account for this schedule.
         * <p>
         * This SHALL be the account that submitted the original
         * ScheduleCreate transaction.
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder schedulerAccountId(AccountID.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(7)</b> The explicit payer account for the scheduled transaction.
         * <p>
         * If set, this account SHALL be added to the accounts that MUST sign the
         * schedule before it may execute.
         *
         * @param payerAccountId value to set
         * @return builder to continue building with
         */
        public Builder payerAccountId(@Nullable AccountID payerAccountId) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(7)</b> The explicit payer account for the scheduled transaction.
         * <p>
         * If set, this account SHALL be added to the accounts that MUST sign the
         * schedule before it may execute.
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder payerAccountId(AccountID.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(8)</b> The admin key for this schedule.
         * <p>
         * This key, if set, MUST sign any `schedule_delete` transaction.<br/>
         * If not set, then this schedule SHALL NOT be deleted, and any
         * `schedule_delete` transaction for this schedule SHALL fail.
         *
         * @param adminKey value to set
         * @return builder to continue building with
         */
        public Builder adminKey(@Nullable Key adminKey) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(8)</b> The admin key for this schedule.
         * <p>
         * This key, if set, MUST sign any `schedule_delete` transaction.<br/>
         * If not set, then this schedule SHALL NOT be deleted, and any
         * `schedule_delete` transaction for this schedule SHALL fail.
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder adminKey(Key.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(9)</b> The transaction valid start value for this schedule.
         * <p>
         * This MUST be set, and SHALL be copied from the `TransactionID` of
         * the original `schedule_create` transaction.
         *
         * @param scheduleValidStart value to set
         * @return builder to continue building with
         */
        public Builder scheduleValidStart(@Nullable Timestamp scheduleValidStart) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(9)</b> The transaction valid start value for this schedule.
         * <p>
         * This MUST be set, and SHALL be copied from the `TransactionID` of
         * the original `schedule_create` transaction.
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder scheduleValidStart(Timestamp.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(10)</b> The requested expiration time of the schedule if provided by the user.
         * <p>
         * If not provided in the `schedule_create` transaction, this SHALL be set
         * to a default value equal to the current consensus time, forward offset by
         * the maximum schedule expiration time in the current dynamic network
         * configuration (typically 62 days).<br/>
         * The actual `calculated_expiration_second` MAY be "earlier" than this,
         * but MUST NOT be later.
         *
         * @param providedExpirationSecond value to set
         * @return builder to continue building with
         */
        public Builder providedExpirationSecond(long providedExpirationSecond) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(11)</b> The calculated expiration time of the schedule.
         * <p>
         * This SHALL be calculated from the requested expiration time in the
         * `schedule_create` transaction, and limited by the maximum expiration time
         * in the current dynamic network configuration (typically 62 days).
         * <p>
         * The schedule SHALL be removed from global network state after the network
         * reaches a consensus time greater than or equal to this value.
         *
         * @param calculatedExpirationSecond value to set
         * @return builder to continue building with
         */
        public Builder calculatedExpirationSecond(long calculatedExpirationSecond) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(12)</b> The consensus timestamp of the transaction that executed or deleted this schedule.
         * <p>
         * This value SHALL be set to the `current_consensus_time` when a
         * `schedule_delete` transaction is completed.<br/>
         * This value SHALL be set to the `current_consensus_time` when the
         * scheduled transaction is executed, either as a result of gathering the
         * final required signature, or, if long-term schedule execution is enabled,
         * at the requested execution time.
         *
         * @param resolutionTime value to set
         * @return builder to continue building with
         */
        public Builder resolutionTime(@Nullable Timestamp resolutionTime) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(12)</b> The consensus timestamp of the transaction that executed or deleted this schedule.
         * <p>
         * This value SHALL be set to the `current_consensus_time` when a
         * `schedule_delete` transaction is completed.<br/>
         * This value SHALL be set to the `current_consensus_time` when the
         * scheduled transaction is executed, either as a result of gathering the
         * final required signature, or, if long-term schedule execution is enabled,
         * at the requested execution time.
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder resolutionTime(Timestamp.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(13)</b> The scheduled transaction to execute.
         * <p>
         * This MUST be one of the transaction types permitted in the current value
         * of the `schedule.whitelist` in the dynamic network configuration.
         *
         * @param scheduledTransaction value to set
         * @return builder to continue building with
         */
        public Builder scheduledTransaction(@Nullable SchedulableTransactionBody scheduledTransaction) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(13)</b> The scheduled transaction to execute.
         * <p>
         * This MUST be one of the transaction types permitted in the current value
         * of the `schedule.whitelist` in the dynamic network configuration.
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder scheduledTransaction(SchedulableTransactionBody.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(14)</b> The full transaction that created this schedule.
         * <p>
         * This is primarily used for duplicate schedule create detection. This is
         * also the source of the parent transaction ID, from which the child
         * transaction ID is derived when the `scheduled_transaction` is executed.
         *
         * @param originalCreateTransaction value to set
         * @return builder to continue building with
         */
        public Builder originalCreateTransaction(@Nullable TransactionBody originalCreateTransaction) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(14)</b> The full transaction that created this schedule.
         * <p>
         * This is primarily used for duplicate schedule create detection. This is
         * also the source of the parent transaction ID, from which the child
         * transaction ID is derived when the `scheduled_transaction` is executed.
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public Builder originalCreateTransaction(TransactionBody.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(15)</b> All of the "primitive" keys that have already signed this schedule.
         * <p>
         * The scheduled transaction SHALL NOT be executed before this list is
         * sufficient to "activate" the required keys for the scheduled transaction.<br/>
         * A Key SHALL NOT be stored in this list unless the corresponding private
         * key has signed either the original `schedule_create` transaction or a
         * subsequent `schedule_sign` transaction intended for, and referencing to,
         * this specific schedule.
         * <p>
         * The only keys stored are "primitive" keys (ED25519 or ECDSA_SECP256K1) in
         * order to ensure that any key list or threshold keys are correctly handled,
         * regardless of signing order, intervening changes, or other situations.
         * The `scheduled_transaction` SHALL execute only if, at the time of
         * execution, this list contains sufficient public keys to satisfy the
         * full requirements for signature on that transaction.
         *
         * @param signatories value to set
         * @return builder to continue building with
         */
        public Builder signatories(@Nonnull List<Key> signatories) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(15)</b> All of the "primitive" keys that have already signed this schedule.
         * <p>
         * The scheduled transaction SHALL NOT be executed before this list is
         * sufficient to "activate" the required keys for the scheduled transaction.<br/>
         * A Key SHALL NOT be stored in this list unless the corresponding private
         * key has signed either the original `schedule_create` transaction or a
         * subsequent `schedule_sign` transaction intended for, and referencing to,
         * this specific schedule.
         * <p>
         * The only keys stored are "primitive" keys (ED25519 or ECDSA_SECP256K1) in
         * order to ensure that any key list or threshold keys are correctly handled,
         * regardless of signing order, intervening changes, or other situations.
         * The `scheduled_transaction` SHALL execute only if, at the time of
         * execution, this list contains sufficient public keys to satisfy the
         * full requirements for signature on that transaction.
         *
         * @param signatoriesSupplier value to set
         * @return builder to continue building with
         */
        public Builder signatories(@Nonnull Supplier<List<Key>> signatoriesSupplier) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(15)</b> All of the "primitive" keys that have already signed this schedule.
         * <p>
         * The scheduled transaction SHALL NOT be executed before this list is
         * sufficient to "activate" the required keys for the scheduled transaction.<br/>
         * A Key SHALL NOT be stored in this list unless the corresponding private
         * key has signed either the original `schedule_create` transaction or a
         * subsequent `schedule_sign` transaction intended for, and referencing to,
         * this specific schedule.
         * <p>
         * The only keys stored are "primitive" keys (ED25519 or ECDSA_SECP256K1) in
         * order to ensure that any key list or threshold keys are correctly handled,
         * regardless of signing order, intervening changes, or other situations.
         * The `scheduled_transaction` SHALL execute only if, at the time of
         * execution, this list contains sufficient public keys to satisfy the
         * full requirements for signature on that transaction.
         *
         * @param values varargs value to be built into a list
         * @return builder to continue building with
         */
        public Builder signatories(Key... values) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
