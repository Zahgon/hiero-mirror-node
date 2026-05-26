// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.common.domain.transaction;

import static org.apache.commons.lang3.StringUtils.leftPad;
import com.hedera.hapi.block.stream.output.protoc.BlockHeader;
import com.hedera.hapi.block.stream.protoc.BlockProof;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.hiero.mirror.common.domain.DigestAlgorithm;
import org.hiero.mirror.common.domain.StreamFile;
import org.hiero.mirror.common.domain.StreamType;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class BlockFile implements StreamFile<BlockTransaction> {

    private static final int BASENAME_LENGTH = 19;

    private static final char BASENAME_PADDING = '0';

    private static final String COMPRESSED_FILE_SUFFIX = ".blk.zstd";

    private static final String FILE_SUFFIX = ".blk";

    private static final Predicate<String> STREAMED_FILENAME_PREDICATE = Pattern.compile("^\\d{19}.blk$").asPredicate();

    @ToString.Exclude
    private BlockHeader blockHeader;

    @ToString.Exclude
    private BlockProof blockProof;

    @ToString.Exclude
    private byte[] bytes;

    private Long consensusStart;

    private Long consensusEnd;

    private Long count;

    private DigestAlgorithm digestAlgorithm;

    @ToString.Exclude
    private String hash;

    private Long index;

    @EqualsAndHashCode.Exclude
    @Singular
    @ToString.Exclude
    private List<BlockTransaction> items;

    @ToString.Exclude
    private BlockTransaction lastLedgerIdPublicationTransaction;

    private Long loadEnd;

    private Long loadStart;

    private String name;

    private String node;

    @ToString.Exclude
    private String previousHash;

    @ToString.Exclude
    private byte[] previousWrappedRecordBlockHash;

    private byte[] rawHash;

    private byte[] rawPreviousHash;

    private RecordFile recordFile;

    private Long roundEnd;

    private Long roundStart;

    private Integer size;

    private int version;

    @Override
    public Long getConsensusEnd() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Long getConsensusStart() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String getFilename(final long blockNumber, final boolean compressed) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public StreamFile<BlockTransaction> copy() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getFileHash() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public BlockSourceType getSourceType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public StreamType getType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean hasRecordFile() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static class BlockFileBuilder {

        public BlockFileBuilder onNewRound(final long roundNumber) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
