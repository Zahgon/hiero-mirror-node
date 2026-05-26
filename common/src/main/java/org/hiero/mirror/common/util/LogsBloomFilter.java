// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.common.util;

import com.google.protobuf.ByteString;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.bouncycastle.crypto.digests.KeccakDigest;

@NoArgsConstructor
public final class LogsBloomFilter {

    // 2048 bits
    public static final int BYTE_SIZE = 256;

    public static final byte[] EMPTY = new byte[0];

    private static final int TOPIC_SIZE_BYTES = 32;

    @Getter(value = AccessLevel.PRIVATE, lazy = true)
    private final byte[] data = new byte[BYTE_SIZE];

    private static byte[] keccakHash(byte[] input) {
        final var digest = new KeccakDigest(256);
        digest.update(input, 0, input.length);
        final var hash = new byte[TOPIC_SIZE_BYTES];
        digest.doFinal(hash, 0);
        return hash;
    }

    public boolean couldContain(final byte[] bloom) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Utility method that mutates the target array by aggregating its content with a passed source that
     * should have a matching length
     */
    public static byte[] or(final byte[] source, final byte[] target) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void insertAddress(final byte[] input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void insertTopic(final ByteString input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void insertTopic(final byte[] input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void insert(final byte[] input) {
        if (ArrayUtils.isEmpty(input)) {
            return;
        }
        final var hash = keccakHash(input);
        final var dataArray = getData();
        // Per the Ethereum spec: use the lowest 3 pairs of bytes
        // to determine the 3-bit positions in the 2048-bit filter
        for (int i = 0; i < 3; i++) {
            int lo = hash[2 * i + 1] & 0xFF;
            int hi = hash[2 * i] & 0xFF;
            // mod 2048
            int bitIndex = (hi << 8 | lo) & 0x7FF;
            int byteIndex = BYTE_SIZE - 1 - (bitIndex >> 3);
            int bitShift = bitIndex & 0x7;
            dataArray[byteIndex] |= (byte) (1 << bitShift);
        }
    }

    public void or(final LogsBloomFilter other) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void or(final byte[] other) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public byte[] toArrayUnsafe() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public ByteString toByteString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
