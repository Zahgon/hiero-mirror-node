// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.common.domain.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hiero.mirror.common.converter.EntityIdConverter;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.springframework.data.domain.Persistable;

// For Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
@Entity
@IdClass(TransactionSignature.Id.class)
@NoArgsConstructor
public class TransactionSignature implements Persistable<TransactionSignature.Id> {

    @jakarta.persistence.Id
    private long consensusTimestamp;

    @Convert(converter = EntityIdConverter.class)
    private EntityId entityId;

    @jakarta.persistence.Id
    @ToString.Exclude
    private byte[] publicKeyPrefix;

    @ToString.Exclude
    private byte[] signature;

    private int type;

    @Override
    @JsonIgnore
    public TransactionSignature.Id getId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Data
    public static class Id implements Serializable {

        private static final long serialVersionUID = -8758644338990079234L;

        private long consensusTimestamp;

        private byte[] publicKeyPrefix;
    }
}
