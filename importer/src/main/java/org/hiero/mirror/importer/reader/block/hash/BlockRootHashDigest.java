// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.reader.block.hash;

import static org.hiero.mirror.common.util.DomainUtils.createSha384Digest;
import com.hedera.hapi.block.stream.protoc.BlockItem;
import com.hederahashgraph.api.proto.java.Timestamp;
import java.security.MessageDigest;
import org.hiero.mirror.common.util.DomainUtils;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullUnmarked;

@NullUnmarked
public final class BlockRootHashDigest {

    private final IncrementalStreamingHasher consensusHeaderHasher = new IncrementalStreamingHasher();

    private final MessageDigest digest = createSha384Digest();

    private final IncrementalStreamingHasher inputHasher = new IncrementalStreamingHasher();

    private final IncrementalStreamingHasher outputHasher = new IncrementalStreamingHasher();

    private final IncrementalStreamingHasher stateChangesHasher = new IncrementalStreamingHasher();

    private final IncrementalStreamingHasher traceDataHasher = new IncrementalStreamingHasher();

    private Timestamp blockTimestamp;

    private boolean finalized;

    private byte[] previousBlocksTreeHash;

    private byte[] previousHash;

    private byte[] startOfBlockStateHash;

    public void addBlockItem(@NonNull final BlockItem blockItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public byte[] digest() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private byte[] combine(final byte[]... leaves) {
        int size = leaves.length;
        if (size == 0 || (size & (size - 1)) != 0) {
            throw new IllegalArgumentException("The leaves must be non-empty and the count must be a power of 2");
        }
        while (size > 1) {
            for (int i = 0; i < size >> 1; i++) {
                final byte[] internal = HashUtils.hashInternalNode(digest, leaves[2 * i], leaves[2 * i + 1]);
                leaves[i] = internal;
            }
            size = size >> 1;
        }
        return leaves[0];
    }
}
