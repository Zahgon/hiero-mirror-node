// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.common.domain.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import jakarta.persistence.Transient;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hiero.mirror.common.converter.EntityIdConverter;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.util.LogsBloomFilter;
import org.springframework.data.domain.Persistable;

// For Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
@EqualsAndHashCode(exclude = "contractResult")
@Entity
@IdClass(ContractLog.Id.class)
@NoArgsConstructor
public class ContractLog implements Persistable<ContractLog.Id> {

    @ToString.Exclude
    private byte[] bloom;

    @jakarta.persistence.Id
    private long consensusTimestamp;

    @Convert(converter = EntityIdConverter.class)
    private EntityId contractId;

    @ToString.Exclude
    private byte[] data;

    @jakarta.persistence.Id
    private int index;

    @Convert(converter = EntityIdConverter.class)
    private EntityId rootContractId;

    @Convert(converter = EntityIdConverter.class)
    private EntityId payerAccountId;

    private byte[] topic0;

    private byte[] topic1;

    private byte[] topic2;

    private byte[] topic3;

    private byte[] transactionHash;

    private int transactionIndex;

    private boolean synthetic;

    /**
     * Transient reference to the ContractResult this log belongs to.
     * Used for updating the bloom filter in the correct ContractResult during synthetic log processing.
     */
    @JsonIgnore
    @Transient
    @ToString.Exclude
    private ContractResult contractResult;

    @Override
    @JsonIgnore
    public Id getId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setBloom(final byte[] bloom) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Id implements Serializable {

        private static final long serialVersionUID = -6192177810161178246L;

        private long consensusTimestamp;

        private int index;
    }
}
