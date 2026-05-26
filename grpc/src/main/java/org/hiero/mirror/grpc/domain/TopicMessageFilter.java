// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.grpc.domain;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.security.SecureRandom;
import lombok.Builder;
import lombok.Value;
import org.apache.commons.lang3.RandomStringUtils;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.util.DomainUtils;
import org.springframework.validation.annotation.Validated;

@Builder(toBuilder = true)
@Validated
@Value
public class TopicMessageFilter {

    private static final SecureRandom RANDOM = new SecureRandom();

    private Long endTime;

    @Min(0)
    private long limit;

    @Min(0)
    @NotNull
    @Builder.Default
    private long startTime = DomainUtils.now();

    @Builder.Default
    private String subscriberId = RandomStringUtils.random(8, 0, 0, true, true, null, RANDOM);

    @NotNull
    private EntityId topicId;

    public boolean hasLimit() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @AssertTrue(message = "End time must be after start time")
    public boolean isValidEndTime() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @AssertTrue(message = "Start time must be before the current time")
    public boolean isValidStartTime() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
