// SPDX-License-Identifier: Apache-2.0
package com.hedera.hapi.node.state.file;

import static java.util.Objects.requireNonNull;
import static org.hiero.mirror.web3.utils.Suppliers.areSuppliersEqual;
import com.hedera.hapi.node.base.FileID;
import com.hedera.hapi.node.base.KeyList;
import com.hedera.pbj.runtime.Codec;
import com.hedera.pbj.runtime.JsonCodec;
import com.hedera.pbj.runtime.io.buffer.Bytes;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Representation of a Hedera Token Service file in the network Merkle tree.
 * <p>
 * As with all network entities, a file has a unique entity number, which is given along
 * with the network's shard and realm in the form of a shard.realm.number id.
 *
 * @param fileId <b>(1)</b> The file's unique file identifier in the Merkle state.
 * @param expirationSecondSupplier <b>(2)</b> The file's consensus expiration time in seconds since the epoch wrapped in a supplier.
 * @param keys <b>(3)</b> All keys at the top level of a key list must sign to create, modify and delete the file.
 * @param contents <b>(4)</b> The bytes that are the contents of the file
 * @param memo <b>(5)</b> The memo associated with the file (UTF-8 encoding max 100 bytes)
 * @param deleted <b>(6)</b> Whether this file is deleted.
 * @param preSystemDeleteExpirationSecond <b>(7)</b> The pre system delete expiration time in seconds
 */
public record File(@Nullable FileID fileId, Supplier<Long> expirationSecondSupplier, @Nullable KeyList keys, @Nonnull Bytes contents, @Nonnull String memo, boolean deleted, long preSystemDeleteExpirationSecond) {

    /**
     * Protobuf codec for reading and writing in protobuf format
     */
    public static final Codec<com.hedera.hapi.node.state.file.File> PROTOBUF = new com.hedera.hapi.node.state.file.codec.FileProtoCodec();

    /**
     * JSON codec for reading and writing in JSON format
     */
    public static final JsonCodec<com.hedera.hapi.node.state.file.File> JSON = new com.hedera.hapi.node.state.file.codec.FileJsonCodec();

    private static final Supplier<Long> DEFAULT_LONG_SUPPLIER = () -> 0L;

    /**
     * Default instance with all fields set to default values
     */
    public static final com.hedera.hapi.node.state.file.File DEFAULT = newBuilder().expirationSecond(DEFAULT_LONG_SUPPLIER).build();

    /**
     * Create a pre-populated File.
     *
     * @param fileId <b>(1)</b> The file's unique file identifier in the Merkle state.,
     * @param expirationSecond <b>(2)</b> The file's consensus expiration time in seconds since the epoch.,
     * @param keys <b>(3)</b> All keys at the top level of a key list must sign to create, modify and delete the file.,
     * @param contents <b>(4)</b> The bytes that are the contents of the file,
     * @param memo <b>(5)</b> The memo associated with the file (UTF-8 encoding max 100 bytes),
     * @param deleted <b>(6)</b> Whether this file is deleted.,
     * @param preSystemDeleteExpirationSecond <b>(7)</b> The pre system delete expiration time in seconds
     */
    public File(FileID fileId, long expirationSecond, KeyList keys, Bytes contents, String memo, boolean deleted, long preSystemDeleteExpirationSecond) {
        this(fileId, () -> expirationSecond, keys, contents != null ? contents : Bytes.EMPTY, memo != null ? memo : "", deleted, preSystemDeleteExpirationSecond);
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
     * Convenience method to check if the fileId has a value
     *
     * @return true of the fileId has a value
     */
    public boolean hasFileId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for fileId if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if fileId is null
     * @return the value for fileId if it has a value, or else returns the default value
     */
    public FileID fileIdOrElse(@Nonnull final FileID defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for fileId if it has a value, or else throws an NPE.
     * value for the type.
     *
     * @return the value for fileId if it has a value
     * @throws NullPointerException if fileId is null
     */
    @Nonnull
    public FileID fileIdOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes the supplied {@link Consumer} if, and only if, the fileId has a value
     *
     * @param ifPresent the {@link Consumer} to execute
     */
    public void ifFileId(@Nonnull final Consumer<FileID> ifPresent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convenience method to check if the keys has a value
     *
     * @return true of the keys has a value
     */
    public boolean hasKeys() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for keys if it has a value, or else returns the default
     * value for the type.
     *
     * @param defaultValue the default value to return if keys is null
     * @return the value for keys if it has a value, or else returns the default value
     */
    public KeyList keysOrElse(@Nonnull final KeyList defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the value for keys if it has a value, or else throws an NPE.
     * value for the type.
     *
     * @return the value for keys if it has a value
     * @throws NullPointerException if keys is null
     */
    @Nonnull
    public KeyList keysOrThrow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes the supplied {@link Consumer} if, and only if, the keys has a value
     *
     * @param ifPresent the {@link Consumer} to execute
     */
    public void ifKeys(@Nonnull final Consumer<KeyList> ifPresent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Return a builder for building a copy of this model object. It will be pre-populated with all the data from this
     * model object.
     *
     * @return a pre-populated builder
     */
    public com.hedera.hapi.node.state.file.File.Builder copyBuilder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Return a new builder for building a model object. This is just a shortcut for <code>new Model.Builder()</code>.
     *
     * @return a new builder
     */
    public static com.hedera.hapi.node.state.file.File.Builder newBuilder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Builder class for easy creation, ideal for clean code where performance is not critical. In critical performance
     * paths use the constructor directly.
     */
    public static final class Builder {

        @Nullable
        private FileID fileId = null;

        private Supplier<Long> expirationSecondSupplier = DEFAULT_LONG_SUPPLIER;

        @Nullable
        private KeyList keys = null;

        @Nonnull
        private Bytes contents = Bytes.EMPTY;

        @Nonnull
        private String memo = "";

        private boolean deleted = false;

        private long preSystemDeleteExpirationSecond = 0;

        /**
         * Create an empty builder
         */
        public Builder() {
        }

        /**
         * Create a pre-populated Builder.
         *
         * @param fileId <b>(1)</b> The file's unique file identifier in the Merkle state.,
         * @param expirationSecondSupplier <b>(2)</b> The file's consensus expiration time in seconds since the epoch wrapped in a supplier.,
         * @param keys <b>(3)</b> All keys at the top level of a key list must sign to create, modify and delete the file.,
         * @param contents <b>(4)</b> The bytes that are the contents of the file,
         * @param memo <b>(5)</b> The memo associated with the file (UTF-8 encoding max 100 bytes),
         * @param deleted <b>(6)</b> Whether this file is deleted.,
         * @param preSystemDeleteExpirationSecond <b>(7)</b> The pre system delete expiration time in seconds
         */
        public Builder(FileID fileId, Supplier<Long> expirationSecondSupplier, KeyList keys, Bytes contents, String memo, boolean deleted, long preSystemDeleteExpirationSecond) {
            this.fileId = fileId;
            this.expirationSecondSupplier = expirationSecondSupplier;
            this.keys = keys;
            this.contents = contents != null ? contents : Bytes.EMPTY;
            this.memo = memo != null ? memo : "";
            this.deleted = deleted;
            this.preSystemDeleteExpirationSecond = preSystemDeleteExpirationSecond;
        }

        /**
         * Build a new model record with data set on builder
         *
         * @return new model record with data set
         */
        public com.hedera.hapi.node.state.file.File build() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(1)</b> The file's unique file identifier in the Merkle state.
         *
         * @param fileId value to set
         * @return builder to continue building with
         */
        public com.hedera.hapi.node.state.file.File.Builder fileId(@Nullable FileID fileId) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(1)</b> The file's unique file identifier in the Merkle state.
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public com.hedera.hapi.node.state.file.File.Builder fileId(FileID.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(2)</b> The file's consensus expiration time in seconds since the epoch.
         *
         * @param expirationSecond value to set
         * @return builder to continue building with
         */
        public com.hedera.hapi.node.state.file.File.Builder expirationSecond(long expirationSecond) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(2)</b> The file's consensus expiration time in seconds since the epoch wrapped in a supplier.
         *
         * @param expirationSecondSupplier value to set
         * @return builder to continue building with
         */
        public com.hedera.hapi.node.state.file.File.Builder expirationSecond(Supplier<Long> expirationSecondSupplier) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(3)</b> All keys at the top level of a key list must sign to create, modify and delete the file.
         *
         * @param keys value to set
         * @return builder to continue building with
         */
        public com.hedera.hapi.node.state.file.File.Builder keys(@Nullable KeyList keys) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(3)</b> All keys at the top level of a key list must sign to create, modify and delete the file.
         *
         * @param builder A pre-populated builder
         * @return builder to continue building with
         */
        public com.hedera.hapi.node.state.file.File.Builder keys(KeyList.Builder builder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(4)</b> The bytes that are the contents of the file
         *
         * @param contents value to set
         * @return builder to continue building with
         */
        public com.hedera.hapi.node.state.file.File.Builder contents(@Nonnull Bytes contents) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(5)</b> The memo associated with the file (UTF-8 encoding max 100 bytes)
         *
         * @param memo value to set
         * @return builder to continue building with
         */
        public com.hedera.hapi.node.state.file.File.Builder memo(@Nonnull String memo) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(6)</b> Whether this file is deleted.
         *
         * @param deleted value to set
         * @return builder to continue building with
         */
        public com.hedera.hapi.node.state.file.File.Builder deleted(boolean deleted) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <b>(7)</b> The pre system delete expiration time in seconds
         *
         * @param preSystemDeleteExpirationSecond value to set
         * @return builder to continue building with
         */
        public com.hedera.hapi.node.state.file.File.Builder preSystemDeleteExpirationSecond(long preSystemDeleteExpirationSecond) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
