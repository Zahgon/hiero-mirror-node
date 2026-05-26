// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.common.util;

import com.google.common.base.CaseFormat;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.Longs;
import com.google.protobuf.ByteOutput;
import com.google.protobuf.ByteString;
import com.google.protobuf.BytesValue;
import com.google.protobuf.Internal;
import com.google.protobuf.UnsafeByteOperations;
import com.hedera.services.stream.proto.HashObject;
import com.hederahashgraph.api.proto.java.AccountID;
import com.hederahashgraph.api.proto.java.ContractID;
import com.hederahashgraph.api.proto.java.Key;
import com.hederahashgraph.api.proto.java.KeyList;
import com.hederahashgraph.api.proto.java.Timestamp;
import com.hederahashgraph.api.proto.java.TokenID;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import lombok.CustomLog;
import lombok.experimental.UtilityClass;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hiero.mirror.common.CommonProperties;
import org.hiero.mirror.common.converter.ObjectToStringSerializer;
import org.hiero.mirror.common.domain.DigestAlgorithm;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.domain.transaction.ContractSlotKey;
import org.hiero.mirror.common.exception.InvalidEntityException;
import org.hiero.mirror.common.exception.ProtobufException;
import org.jspecify.annotations.Nullable;

@CustomLog
@UtilityClass
public class DomainUtils {

    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    public static final ByteString EMPTY_BYTE_STRING = ByteString.EMPTY;

    public static final int EVM_ADDRESS_LENGTH = 20;

    public static final long NANOS_PER_SECOND = 1_000_000_000L;

    public static final long TINYBARS_IN_ONE_HBAR = 100_000_000L;

    private static final long MAX_SYSTEM_ENTITY_NUM = 999;

    private static final byte[] MIRROR_PREFIX = new byte[12];

    private static final int NANO_DIGITS = 9;

    private static final char NULL_CHARACTER = (char) 0;

    // Standard replacement character 0xFFFD
    private static final char NULL_REPLACEMENT = '�';

    private static final String TIMESTAMP_ZERO = "0.0";

    static {
        try {
            // Ensure it's eagerly instantiated since it is used for the conversion of JSONB data into domain objects.
            ObjectToStringSerializer.init();
        } catch (NoClassDefFoundError e) {
            log.warn("Unable to initialize ObjectToStringSerializer possibly due to lack of Hibernate dependencies: {}", e.getMessage());
        }
    }

    /**
     * Convert bytes to hex.
     *
     * @param bytes to be converted
     * @return converted HexString
     */
    public static String bytesToHex(byte[] bytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] getHashBytes(HashObject hashObject) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * A key can either be a complex key (e.g. key list or threshold key) or a primitive key (e.g. ED25519 or
     * ECDSA_SECP256K1). If the protobuf encoding of a Key is a single primitive key or a complex key with exactly one
     * primitive key within it, return the key as a String with lowercase hex encoding.
     *
     * @param protobufKey the protobuf encoding of a Key
     * @return public key as a string in hex encoding, or null
     */
    public static String getPublicKey(@Nullable byte[] protobufKey) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressWarnings({ "deprecation", "java:S1168" })
    private static byte[] getPublicKey(Key key, int depth) {
        // We don't support searching for primitive keys at multiple levels since the REST API matches by hex prefix
        if (depth > 2) {
            return null;
        }
        return switch(key.getKeyCase()) {
            case ECDSA_384 ->
                toBytes(key.getECDSA384());
            case ECDSA_SECP256K1 ->
                toBytes(key.getECDSASecp256K1());
            case ED25519 ->
                toBytes(key.getEd25519());
            case KEYLIST ->
                getPublicKey(key.getKeyList(), depth);
            case RSA_3072 ->
                toBytes(key.getRSA3072());
            case THRESHOLDKEY ->
                getPublicKey(key.getThresholdKey().getKeys(), depth);
            default ->
                null;
        };
    }

    @SuppressWarnings("java:S1168")
    private static byte[] getPublicKey(KeyList keyList, int depth) {
        List<Key> keys = keyList.getKeysList();
        if (keys.size() == 1) {
            return getPublicKey(keys.get(0), depth + 1);
        }
        return null;
    }

    /**
     * Converts time in (second, nanos) to time in only nanos.
     */
    public static long convertToNanos(long second, long nanos) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Converts time in (second, nanos) to time in only nanos, with a fallback if overflow: If positive overflow, return
     * the max time in the future (Long.MAX_VALUE). If negative overflow, return the max time in the past
     * (Long.MIN_VALUE).
     */
    public static long convertToNanosMax(long second, long nanos) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Converts instant to time in only nanos, with a fallback if overflow: If positive overflow, return the max time in
     * the future (Long.MAX_VALUE). If negative overflow, return the max time in the past (Long.MIN_VALUE).
     */
    public static long convertToNanosMax(Instant instant) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static MessageDigest createSha384Digest() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isSystemEntity(EntityId entityId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Pad a byte array with leading zeros to a given length.
     *
     * @param bytes  the byte array to pad
     * @param length the length to pad to
     */
    public static byte[] leftPadBytes(byte[] bytes, int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static long now() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convert Timestamp to a Long type timeStampInNanos
     */
    public static Long timeStampInNanos(Timestamp timestamp) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Long timestampInNanosMax(Timestamp timestamp) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Cleans a string of invalid characters that would cause it to fail when inserted into the database. In particular,
     * PostgreSQL does not allow the null character (0x0000) to be inserted.
     *
     * @param input string containing potentially invalid characters
     * @return the cleaned string
     */
    public static String sanitize(String input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * This method converts a protobuf ByteString into a byte array. Optimization is done in case the input is a
     * LiteralByteString to not make a copy of the underlying array and return it as is. This is okay for our purposes
     * since we never modify the array and just directly store it in the database.
     * <p>
     * If the ByteString is smaller than the estimated size to allocate an UnsafeByteOutput object, copy the array
     * regardless since we'd be allocating a similar amount of memory either way.
     *
     * @param byteString to convert
     * @return bytes extracted from the ByteString
     */
    @SuppressWarnings("java:S1168")
    public static byte[] toBytes(ByteString byteString) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static ByteString fromBytes(byte[] bytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static EntityId fromEvmAddress(byte[] evmAddress) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static EntityId fromTrimmedEvmAddress(final byte[] evmAddress) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static ContractSlotKey normalize(ContractSlotKey slotKey) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] trim(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static ByteString trim(final ByteString data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static BytesValue trim(final BytesValue data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] toEvmAddress(ContractID contractId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] toEvmAddress(AccountID accountId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] toEvmAddress(TokenID tokenId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] toEvmAddress(EntityId contractId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] toEvmAddress(long num) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isLongZeroAddress(byte[] evmAddress) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String toSnakeCase(final String text) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String toTimestamp(long timestamp) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static class UnsafeByteOutput extends ByteOutput {

        // Size of the object header plus a compressed object reference to bytes field
        static final short SIZE = 12 + 4;

        private static final Class<?> SUPPORTED_CLASS;

        static {
            try {
                SUPPORTED_CLASS = Class.forName(ByteString.class.getName() + "$LiteralByteString");
            } catch (ClassNotFoundException e) {
                throw new ProtobufException(String.format("Unable to locate class=%s", ByteString.class.getName() + "$LiteralByteString"));
            }
        }

        private byte[] bytes;

        private static boolean supports(ByteString byteString) {
            return byteString.size() > UnsafeByteOutput.SIZE && byteString.getClass() == UnsafeByteOutput.SUPPORTED_CLASS;
        }

        @Override
        public void write(byte value) throws IOException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void write(byte[] bytes, int offset, int length) throws IOException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void writeLazy(byte[] bytes, int offset, int length) throws IOException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void write(ByteBuffer value) throws IOException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void writeLazy(ByteBuffer value) throws IOException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
