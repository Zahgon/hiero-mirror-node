// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.common.domain.balance;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hiero.mirror.common.domain.StreamFile;
import org.hiero.mirror.common.domain.StreamType;

@Builder(toBuilder = true)
@Data
@Entity
// For Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class AccountBalanceFile implements StreamFile<AccountBalance> {

    @ToString.Exclude
    private byte[] bytes;

    @Id
    private Long consensusTimestamp;

    private Long count;

    @ToString.Exclude
    private String fileHash;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Transient
    private List<AccountBalance> items = List.of();

    private Long loadEnd;

    private Long loadStart;

    private String name;

    private boolean synthetic;

    private int timeOffset;

    @Override
    public StreamFile<AccountBalance> copy() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Long getConsensusStart() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void setConsensusStart(Long timestamp) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Long getConsensusEnd() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public StreamType getType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
