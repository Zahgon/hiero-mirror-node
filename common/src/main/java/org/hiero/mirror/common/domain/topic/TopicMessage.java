// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.common.domain.topic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Comparator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hiero.mirror.common.converter.EntityIdConverter;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.springframework.data.domain.Persistable;

// For builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Data
@Entity
@JsonTypeInfo(use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME)
@JsonTypeName("TopicMessage")
@NoArgsConstructor
public class TopicMessage implements Comparable<TopicMessage>, Persistable<Long>, StreamMessage {

    private static final Comparator<TopicMessage> COMPARATOR = Comparator.nullsFirst(Comparator.comparing(TopicMessage::getTopicId).thenComparing(TopicMessage::getSequenceNumber));

    private Integer chunkNum;

    private Integer chunkTotal;

    @Id
    private long consensusTimestamp;

    @ToString.Exclude
    private byte[] initialTransactionId;

    @ToString.Exclude
    private byte[] message;

    @Convert(converter = EntityIdConverter.class)
    private EntityId payerAccountId;

    @ToString.Exclude
    private byte[] runningHash;

    private Integer runningHashVersion;

    private long sequenceNumber;

    @Convert(converter = EntityIdConverter.class)
    private EntityId topicId;

    private Long validStartTimestamp;

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

    @Override
    public int compareTo(TopicMessage other) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
