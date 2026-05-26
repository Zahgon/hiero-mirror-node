// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.reader.record;

import com.google.common.base.Stopwatch;
import jakarta.inject.Named;
import java.io.DataInputStream;
import java.io.IOException;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.common.domain.transaction.RecordFile;
import org.hiero.mirror.importer.domain.StreamFileData;
import org.hiero.mirror.importer.exception.InvalidStreamFileException;
import org.hiero.mirror.importer.exception.StreamFileReaderException;
import org.jspecify.annotations.NullMarked;
import org.springframework.context.annotation.Primary;

@CustomLog
@Named
@NullMarked
@Primary
@RequiredArgsConstructor
public class CompositeRecordFileReader implements RecordFileReader {

    private final RecordFileReaderImplV1 version1Reader;

    private final RecordFileReaderImplV2 version2Reader;

    private final RecordFileReaderImplV5 version5Reader;

    private final ProtoRecordFileReader version6Reader;

    @Override
    public RecordFile read(StreamFileData streamFileData) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
