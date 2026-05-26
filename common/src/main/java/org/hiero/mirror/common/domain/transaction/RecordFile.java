// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.common.domain.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hiero.mirror.common.domain.DigestAlgorithm;
import org.hiero.mirror.common.domain.StreamFile;
import org.hiero.mirror.common.domain.StreamType;
import org.springframework.data.util.Version;

@Builder(toBuilder = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RecordFile implements StreamFile<RecordItem> {

    public static final RecordFile EMPTY = new RecordFile();

    public static final Version HAPI_VERSION_NOT_SET = new Version(0, 0, 0);

    public static final Version HAPI_VERSION_0_23_0 = new Version(0, 23, 0);

    public static final Version HAPI_VERSION_0_27_0 = new Version(0, 27, 0);

    public static final Version HAPI_VERSION_0_47_0 = new Version(0, 47, 0);

    public static final Version HAPI_VERSION_0_49_0 = new Version(0, 49, 0);

    public static final Version HAPI_VERSION_0_53_0 = new Version(0, 53, 0);

    @ToString.Exclude
    private byte[] bytes;

    private Long consensusStart;

    @Id
    private Long consensusEnd;

    private Long count;

    @Enumerated
    private DigestAlgorithm digestAlgorithm;

    @ToString.Exclude
    private String fileHash;

    @Builder.Default
    private long gasUsed = 0L;

    private Integer hapiVersionMajor;

    private Integer hapiVersionMinor;

    private Integer hapiVersionPatch;

    @Getter(lazy = true)
    @JsonIgnore
    @Transient
    private final Version hapiVersion = hapiVersion();

    @ToString.Exclude
    private String hash;

    private Long index;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ToString.Exclude
    @Transient
    private List<RecordItem> items = List.of();

    private Long loadEnd;

    private Long loadStart;

    @ToString.Exclude
    private byte[] logsBloom;

    @ToString.Exclude
    @JsonIgnore
    @Transient
    private String metadataHash;

    private String name;

    @Column(name = "prev_hash")
    @JsonProperty("prev_hash")
    @ToString.Exclude
    private String previousHash;

    @ToString.Exclude
    private byte[] previousWrappedRecordBlockHash;

    private Long roundEnd;

    private Long roundStart;

    private int sidecarCount;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ToString.Exclude
    @Transient
    private Collection<SidecarFile> sidecars = List.of();

    private Integer size;

    private Integer softwareVersionMajor;

    private Integer softwareVersionMinor;

    private Integer softwareVersionPatch;

    private int version;

    @ToString.Exclude
    private byte[] wrappedRecordBlockHash;

    @Override
    public RecordFile clear() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public StreamFile<RecordItem> copy() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @JsonIgnore
    public StreamType getType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @JsonIgnore
    public boolean isEmpty() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private Version hapiVersion() {
        if (hapiVersionMajor == null || hapiVersionMinor == null || hapiVersionPatch == null) {
            return HAPI_VERSION_NOT_SET;
        }
        return new Version(hapiVersionMajor, hapiVersionMinor, hapiVersionPatch);
    }
}
