// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.reader.block.hash;

import static org.hiero.mirror.common.util.DomainUtils.createSha384Digest;
import static org.hiero.mirror.common.util.DomainUtils.toBytes;
import com.hedera.hapi.block.stream.protoc.MerklePath;
import jakarta.inject.Named;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.codec.binary.Hex;
import org.hiero.mirror.importer.exception.InvalidStreamFileException;

@Named
final class BlockStateProofHasherImpl implements BlockStateProofHasher {

    private static final int DEPTH3_RIGHT_SIBLING_MODULAR_INDEX = 2;

    private static final int MIN_PREVIOUS_BLOCK_ROOT_PATH_SIBLING_COUNT = 7;

    private static final int SIBLING_GROUP_SIZE = 4;

    @Override
    public byte[] getRootHash(final long blockNumber, final byte[] currentRootHash, final List<MerklePath> merklePaths) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
