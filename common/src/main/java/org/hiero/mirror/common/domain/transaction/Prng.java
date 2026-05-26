// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.common.domain.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

// For Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
@Entity
@NoArgsConstructor
public class Prng implements Persistable<Long> {

    @Id
    private long consensusTimestamp;

    private long payerAccountId;

    private byte[] prngBytes;

    private Integer prngNumber;

    private int range;

    @JsonIgnore
    @Override
    public Long getId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
