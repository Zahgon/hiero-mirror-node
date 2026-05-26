// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.reader.signature;

import static org.hiero.mirror.common.domain.DigestAlgorithm.SHA_384;
import jakarta.inject.Named;
import java.io.IOException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hiero.mirror.importer.domain.StreamFileData;
import org.hiero.mirror.importer.domain.StreamFileSignature;
import org.hiero.mirror.importer.domain.StreamFileSignature.SignatureType;
import org.hiero.mirror.importer.exception.InvalidStreamFileException;
import org.hiero.mirror.importer.exception.SignatureFileParsingException;
import org.hiero.mirror.importer.reader.AbstractStreamObject;
import org.hiero.mirror.importer.reader.HashObject;
import org.hiero.mirror.importer.reader.ValidatedDataInputStream;

@Named
public class SignatureFileReaderV5 implements SignatureFileReader {

    protected static final byte VERSION = 5;

    @Override
    public StreamFileSignature read(StreamFileData signatureFileData) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @EqualsAndHashCode(callSuper = true)
    @Getter
    private static class SignatureObject extends AbstractStreamObject {

        private final byte[] signature;

        private final SignatureType signatureType;

        SignatureObject(ValidatedDataInputStream vdis, String sectionName) {
            super(vdis);
            try {
                signatureType = SignatureType.SHA_384_WITH_RSA;
                vdis.readInt(signatureType.getFileMarker(), sectionName, "signature type");
                signature = vdis.readLengthAndBytes(1, signatureType.getMaxLength(), true, sectionName, "signature");
            } catch (IOException e) {
                throw new InvalidStreamFileException(e);
            }
        }
    }
}
