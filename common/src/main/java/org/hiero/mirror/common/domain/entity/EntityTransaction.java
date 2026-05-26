// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.common.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import java.io.Serial;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hiero.mirror.common.converter.EntityIdConverter;
import org.hiero.mirror.common.domain.entity.EntityTransaction.Id;
import org.springframework.data.domain.Persistable;

// For Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
@Entity
@IdClass(EntityTransaction.Id.class)
@NoArgsConstructor
public class EntityTransaction implements Persistable<Id> {

    @Column(updatable = false)
    @jakarta.persistence.Id
    private Long consensusTimestamp;

    @Column(updatable = false)
    @jakarta.persistence.Id
    private Long entityId;

    @Convert(converter = EntityIdConverter.class)
    @Column(updatable = false)
    private EntityId payerAccountId;

    @Column(updatable = false)
    private Integer result;

    @Column(updatable = false)
    private Integer type;

    @JsonIgnore
    @Override
    public Id getId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    public static class Id implements Serializable {

        @Serial
        private static final long serialVersionUID = -3010905088908209508L;

        private long consensusTimestamp;

        private long entityId;
    }
}
