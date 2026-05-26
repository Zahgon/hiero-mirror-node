// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.restjava.parameter;

import java.util.regex.Pattern;
import org.hiero.mirror.common.CommonProperties;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.jspecify.annotations.Nullable;

public record EntityIdNumParameter(EntityId id) implements EntityIdParameter {

    private static final String ENTITY_ID_REGEX = "^((\\d{1,4})\\.)?((\\d{1,5})\\.)?(\\d{1,12})$";

    private static final Pattern ENTITY_ID_PATTERN = Pattern.compile(ENTITY_ID_REGEX);

    @Nullable
    static EntityIdNumParameter valueOfNullable(String id) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static EntityIdNumParameter valueOf(String id) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public long shard() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public long realm() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
