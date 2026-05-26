// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.common.domain.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ContractTransactionHash implements Persistable<byte[]> {

    private long consensusTimestamp;

    private long entityId;

    @Id
    private byte[] hash;

    private long payerAccountId;

    private Integer transactionResult;

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
}
