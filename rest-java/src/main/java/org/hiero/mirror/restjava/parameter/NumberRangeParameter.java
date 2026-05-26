// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.restjava.parameter;

import org.apache.commons.lang3.StringUtils;
import org.hiero.mirror.restjava.common.RangeOperator;

public record NumberRangeParameter(RangeOperator operator, Long value) implements RangeParameter<Long> {

    public static final NumberRangeParameter EMPTY = new NumberRangeParameter(RangeOperator.UNKNOWN, -1L);

    public static NumberRangeParameter valueOf(String valueRangeParam) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static long getNumberValue(String number) {
        var value = Long.parseLong(number);
        if (value < 0) {
            throw new IllegalArgumentException("Invalid range value");
        }
        return value;
    }

    public long getInclusiveValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
