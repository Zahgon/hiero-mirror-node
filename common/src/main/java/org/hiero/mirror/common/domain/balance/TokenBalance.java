// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.common.domain.balance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hiero.mirror.common.converter.EntityIdConverter;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.springframework.data.domain.Persistable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TokenBalance implements Persistable<TokenBalance.Id> {

    private long balance;

    @EmbeddedId
    @JsonUnwrapped
    private TokenBalance.Id id;

    @JsonIgnore
    @Override
    public boolean isNew() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class Id implements Serializable {

        private static final long serialVersionUID = -8547332015249955424L;

        // set updatable = false to prevent additional hibernate query
        @Column(nullable = false, updatable = false)
        private long consensusTimestamp;

        @Convert(converter = EntityIdConverter.class)
        // set updatable = false to prevent additional hibernate query
        @Column(nullable = false, updatable = false)
        private EntityId accountId;

        @Convert(converter = EntityIdConverter.class)
        private EntityId tokenId;
    }
}
