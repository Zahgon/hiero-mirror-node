// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.reader.block.record;

import static org.hiero.mirror.common.util.DomainUtils.createSha384Digest;
import com.hedera.services.stream.proto.RecordStreamItem;
import java.io.IOException;
import org.hiero.mirror.common.domain.transaction.RecordFile;
import org.hiero.mirror.common.util.DomainUtils;
import org.hiero.mirror.importer.parser.record.sidecar.SidecarProperties;

final class RecordFileItemReaderV2 extends AbstractRecordFileItemReader {

    private static final byte PREV_HASH_MARKER = 0x01;

    private static final byte RECORD_MARKER = 0x02;

    RecordFileItemReaderV2(final SidecarProperties sidecarProperties) {
        super(sidecarProperties);
    }

    @Override
    protected void finalize(final RecordFile recordFile) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void onBody(final Context context) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void onEnd(final Context context) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void onHeader(final Context context) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void onRecordStreamItem(final Context context, final RecordStreamItem recordStreamItem) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
