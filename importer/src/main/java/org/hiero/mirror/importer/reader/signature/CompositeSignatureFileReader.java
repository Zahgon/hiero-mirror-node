// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.reader.signature;

import jakarta.inject.Named;
import java.io.DataInputStream;
import java.io.IOException;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.importer.domain.StreamFileData;
import org.hiero.mirror.importer.domain.StreamFileSignature;
import org.hiero.mirror.importer.exception.SignatureFileParsingException;
import org.springframework.context.annotation.Primary;

@CustomLog
@Named
@Primary
@RequiredArgsConstructor
public class CompositeSignatureFileReader implements SignatureFileReader {

    private final SignatureFileReaderV2 signatureFileReaderV2;

    private final SignatureFileReaderV5 signatureFileReaderV5;

    private final ProtoSignatureFileReader protoSignatureFileReader;

    @Override
    public StreamFileSignature read(StreamFileData signatureFileData) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
