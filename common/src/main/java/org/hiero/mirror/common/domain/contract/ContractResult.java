// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.common.domain.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.ArrayUtils;
import org.hiero.mirror.common.converter.EntityIdConverter;
import org.hiero.mirror.common.converter.ListToStringSerializer;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.springframework.data.domain.Persistable;

@Data
@Entity
@NoArgsConstructor
@SuperBuilder
public class ContractResult implements Persistable<Long> {

    private static final byte[] EMPTY_BLOOM = new byte[256];

    private Long amount;

    @ToString.Exclude
    private byte[] bloom;

    @ToString.Exclude
    private byte[] callResult;

    @Id
    private Long consensusTimestamp;

    private long contractId;

    @Builder.Default
    @JsonSerialize(using = ListToStringSerializer.class)
    private List<Long> createdContractIds = Collections.emptyList();

    private String errorMessage;

    @ToString.Exclude
    private byte[] failedInitcode;

    @ToString.Exclude
    private byte[] functionParameters;

    // Temporary field until we can confirm the migration captured everything
    private byte[] functionResult;

    private Long gasConsumed;

    private Long gasLimit;

    private Long gasUsed;

    @Convert(converter = EntityIdConverter.class)
    private EntityId payerAccountId;

    @Convert(converter = EntityIdConverter.class)
    private EntityId senderId;

    private byte[] transactionHash;

    private Integer transactionIndex;

    private int transactionNonce;

    private Integer transactionResult;

    @JsonIgnore
    @Override
    public Long getId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setBloom(byte[] bloom) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public ContractTransactionHash toContractTransactionHash() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
