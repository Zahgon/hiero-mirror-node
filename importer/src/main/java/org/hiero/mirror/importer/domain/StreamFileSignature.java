// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.domain;

import static org.hiero.mirror.importer.reader.signature.ProtoSignatureFileReader.VERSION;
import java.util.Comparator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hiero.mirror.common.domain.StreamType;
import org.hiero.mirror.common.util.DomainUtils;
import org.hiero.mirror.importer.addressbook.ConsensusNode;

@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@ToString(exclude = { "bytes", "fileHash", "fileHashSignature", "metadataHash", "metadataHashSignature" })
public class StreamFileSignature implements Comparable<StreamFileSignature> {

    private static final String COMPRESSED_EXTENSION = ".gz";

    private static final Comparator<StreamFileSignature> COMPARATOR = Comparator.comparing(StreamFileSignature::getNode).thenComparing(StreamFileSignature::getFilename);

    private byte[] bytes;

    private byte[] fileHash;

    private byte[] fileHashSignature;

    @EqualsAndHashCode.Include
    private StreamFilename filename;

    private byte[] metadataHash;

    private byte[] metadataHashSignature;

    @EqualsAndHashCode.Include
    private ConsensusNode node;

    private SignatureType signatureType;

    @Builder.Default
    private SignatureStatus status = SignatureStatus.DOWNLOADED;

    private StreamType streamType;

    private byte version;

    @Override
    public int compareTo(StreamFileSignature other) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public StreamFilename getDataFilename() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getFileHashAsHex() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getMetadataHashAsHex() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean hasCompressedDataFile() {
        return version >= VERSION || filename.isCompressed();
    }

    public enum SignatureStatus {

        // Signature has been downloaded and parsed but not verified
        DOWNLOADED,
        // Signature has been verified against the node's public key
        VERIFIED,
        // Signature verification consensus reached by a node count greater than the consensusRatio
        CONSENSUS_REACHED,
        // Signature for given node was not found for download
        NOT_FOUND
    }

    @Getter
    @RequiredArgsConstructor
    public enum SignatureType {

        SHA_384_WITH_RSA(1, 384, "SHA384withRSA", "SunRsaSign");

        private final int fileMarker;

        private final int maxLength;

        private final String algorithm;

        private final String provider;

        public static SignatureType of(int signatureTypeIndicator) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
