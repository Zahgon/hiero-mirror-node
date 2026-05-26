// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.restjava.parameter;

import org.hiero.mirror.restjava.common.RangeOperator;

public interface RangeParameter<T> {

    RangeOperator operator();

    T value();

    // Considering EQ in the same category as GT,GTE as an assumption
    default boolean hasLowerBound() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default boolean hasUpperBound() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default boolean isEmpty() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
