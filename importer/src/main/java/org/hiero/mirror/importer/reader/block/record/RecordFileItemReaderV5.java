// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.reader.block.record;

import com.hedera.services.stream.proto.HashObject;
import com.hedera.services.stream.proto.RecordStreamItem;
import java.io.DataOutputStream;
import java.io.IOException;
import org.hiero.mirror.common.domain.DigestAlgorithm;
import org.hiero.mirror.common.util.DomainUtils;
import org.hiero.mirror.importer.parser.record.sidecar.SidecarProperties;

final class RecordFileItemReaderV5 extends AbstractRecordFileItemReader {

    RecordFileItemReaderV5(final SidecarProperties sidecarProperties) {
        super(sidecarProperties);
    }

    private static final long HASH_OBJECT_CLASS_ID = 0xf422da83a251741eL;

    private static final int HASH_OBJECT_CLASS_VERSION = 1;

    private static final int OBJECT_STREAM_VERSION = 1;

    private static final long RECORD_STREAM_OBJECT_CLASS_ID = 0xe370929ba5429d8bL;

    private static final int RECORD_STREAM_OBJECT_CLASS_VERSION = 1;

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

    private static void writeHashObject(final DataOutputStream dos, final HashObject hashObject) throws IOException {
        dos.writeLong(HASH_OBJECT_CLASS_ID);
        dos.writeInt(HASH_OBJECT_CLASS_VERSION);
        dos.writeInt(DigestAlgorithm.SHA_384.getType());
        final var hashBytes = DomainUtils.getHashBytes(hashObject);
        dos.writeInt(hashBytes.length);
        dos.write(hashBytes);
    }
}
