// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.restjava.dto;

import static org.hiero.mirror.restjava.common.Constants.CONSENSUS_TIMESTAMP;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import org.hiero.mirror.restjava.common.Constants;
import org.hiero.mirror.restjava.parameter.EntityIdParameter;
import org.hiero.mirror.restjava.service.Bound;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Builder
@Value
public class HookStorageRequest {

    private final long hookId;

    private final byte[] keyLowerBound;

    @Builder.Default
    private final Collection<byte[]> keys = List.of();

    private final byte[] keyUpperBound;

    @Builder.Default
    private final int limit = 25;

    @Builder.Default
    private final Direction order = Direction.ASC;

    private final EntityIdParameter ownerId;

    @Builder.Default
    private final Bound timestamp = Bound.EMPTY;

    public List<byte[]> getKeysInRange() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public PageRequest getPageRequest() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isHistorical() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
