// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.reader.signature;

import jakarta.inject.Named;
import java.io.IOException;
import org.hiero.mirror.common.domain.DigestAlgorithm;
import org.hiero.mirror.importer.domain.StreamFileData;
import org.hiero.mirror.importer.domain.StreamFileSignature;
import org.hiero.mirror.importer.domain.StreamFileSignature.SignatureType;
import org.hiero.mirror.importer.exception.InvalidStreamFileException;
import org.hiero.mirror.importer.exception.SignatureFileParsingException;
import org.hiero.mirror.importer.reader.ValidatedDataInputStream;

@Named
public class SignatureFileReaderV2 implements SignatureFileReader {

    // the file content signature, should not be hashed
    protected static final byte SIGNATURE_TYPE_SIGNATURE = 3;

    // next 48 bytes are SHA-384 of content of record file
    protected static final byte SIGNATURE_TYPE_FILE_HASH = 4;

    private static final byte VERSION = 2;

    @Override
    public StreamFileSignature read(StreamFileData signatureFileData) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
