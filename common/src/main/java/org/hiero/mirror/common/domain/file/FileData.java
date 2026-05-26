// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.common.domain.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hiero.mirror.common.converter.EntityIdConverter;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.domain.transaction.TransactionType;
import org.springframework.data.domain.Persistable;

@Builder(toBuilder = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "fileData")
public class FileData implements Persistable<Long> {

    @Id
    private Long consensusTimestamp;

    @SuppressWarnings("java:S1700")
    private byte[] fileData;

    @Convert(converter = EntityIdConverter.class)
    private EntityId entityId;

    private Integer transactionType;

    public boolean transactionTypeIsAppend() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @JsonIgnore
    public int getDataSize() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

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
