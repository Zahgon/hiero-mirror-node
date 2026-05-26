// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Range;

public interface History {

    @JsonIgnore
    default boolean hasHistory() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    Range<Long> getTimestampRange();

    void setTimestampRange(Range<Long> timestampRange);

    @JsonIgnore
    default Long getTimestampLower() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void setTimestampLower(long timestampLower) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @JsonIgnore
    default Long getTimestampUpper() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void setTimestampUpper(long timestampUpper) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
