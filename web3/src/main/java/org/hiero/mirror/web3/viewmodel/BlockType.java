// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.viewmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Locale;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 * BlockType represents a way to identify a specific block in the chain. Can be one of:
 *  - block tag ("earliest", "latest", "safe", "pending", "finalized")
 *  - block number (decimal or hex string)
 *  - block hash (hex string of length 64 or 96, with or without 0x prefix)
 */
public record BlockType(String name, long number) {

    private static final Pattern BLOCK_PATTERN = Pattern.compile("^(?:" + "(earliest|finalized|latest|pending|safe)" + "|(\\d{1,20})" + "|0x([0-9a-f]{64}|[0-9a-f]{96})" + "|0x([0-9a-f]{1,16})" + ")$");

    private static final int GROUP_TAG = 1;

    private static final int GROUP_DECIMAL = 2;

    private static final int GROUP_HASH = 3;

    private static final int GROUP_HEX_NUM = 4;

    public static final BlockType EARLIEST = new BlockType("earliest", 0L);

    public static final BlockType LATEST = new BlockType("latest", Long.MAX_VALUE);

    /**
     * Value for number when blockType represents a block hash,
     * name holds the normalized hex string (0x prefix + lowercase hex digits).
     */
    public static final long BLOCK_HASH_SENTINEL = -1L;

    public boolean isHash() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @JsonCreator
    public static BlockType of(final String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static BlockType blockTypeForTag(String tag) {
        return switch(tag) {
            case "earliest" ->
                EARLIEST;
            case "finalized", "latest", "pending", "safe" ->
                LATEST;
            default ->
                throw new IllegalStateException("Unexpected block tag: " + tag);
        };
    }

    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
