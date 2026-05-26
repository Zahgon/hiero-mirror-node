// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.common.domain.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Arrays;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.domain.Persistable;

@Data
@Entity
@NoArgsConstructor
public class TransactionHash implements Persistable<byte[]> {

    public static final int V1_SHARD_COUNT = 32;

    private long consensusTimestamp;

    @Id
    private byte[] hash;

    private byte[] hashSuffix;

    private long payerAccountId;

    @Builder
    public TransactionHash(long consensusTimestamp, byte[] hash, long payerAccountId) {
        this.consensusTimestamp = consensusTimestamp;
        setHash(hash);
        this.payerAccountId = payerAccountId;
    }

    @JsonIgnore
    @Override
    public byte[] getId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int calculateV1Shard() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean hashIsValid() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setHash(byte[] hash) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
