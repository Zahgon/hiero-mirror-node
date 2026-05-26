// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.reader.record.sidecar;

import jakarta.inject.Named;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import org.hiero.mirror.common.domain.transaction.SidecarFile;
import org.hiero.mirror.importer.domain.StreamFileData;
import org.hiero.mirror.importer.exception.InvalidStreamFileException;

@Named
public class SidecarFileReaderImpl implements SidecarFileReader {

    @Override
    public void read(SidecarFile sidecarFile, StreamFileData streamFileData) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
