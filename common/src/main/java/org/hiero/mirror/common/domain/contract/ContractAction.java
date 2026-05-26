// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.common.domain.contract;

import static com.hedera.services.stream.proto.ContractAction.ResultDataCase.REVERT_REASON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.IdClass;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.hiero.mirror.common.converter.EntityIdConverter;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.domain.entity.EntityType;
import org.springframework.data.domain.Persistable;

// For Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
@Entity
@IdClass(ContractAction.Id.class)
@NoArgsConstructor
public class ContractAction implements Persistable<ContractAction.Id> {

    private int callDepth;

    @Convert(converter = EntityIdConverter.class)
    private EntityId caller;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private EntityType callerType;

    private int callOperationType;

    private Integer callType;

    @jakarta.persistence.Id
    private long consensusTimestamp;

    private long gas;

    private long gasUsed;

    @jakarta.persistence.Id
    private int index;

    @ToString.Exclude
    private byte[] input;

    @Convert(converter = EntityIdConverter.class)
    private EntityId payerAccountId;

    @Convert(converter = EntityIdConverter.class)
    private EntityId recipientAccount;

    @ToString.Exclude
    private byte[] recipientAddress;

    @Convert(converter = EntityIdConverter.class)
    private EntityId recipientContract;

    @ToString.Exclude
    private byte[] resultData;

    private int resultDataType;

    private long value;

    @Override
    @JsonIgnore
    public ContractAction.Id getId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @JsonIgnore
    public boolean hasRevertReason() {
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
