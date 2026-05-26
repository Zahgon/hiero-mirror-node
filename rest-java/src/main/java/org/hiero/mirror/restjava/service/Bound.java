// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.restjava.service;

import static org.hiero.mirror.restjava.common.RangeOperator.EQ;
import static org.hiero.mirror.restjava.common.RangeOperator.GT;
import static org.hiero.mirror.restjava.common.RangeOperator.LT;
import java.util.Arrays;
import java.util.EnumMap;
import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hiero.mirror.restjava.common.RangeOperator;
import org.hiero.mirror.restjava.parameter.NumberRangeParameter;
import org.hiero.mirror.restjava.parameter.RangeParameter;
import org.hiero.mirror.restjava.parameter.TimestampParameter;
import org.jooq.Field;
import org.jspecify.annotations.NullUnmarked;

@NullUnmarked
public class Bound {

    public static final Bound EMPTY = new Bound(null, false, StringUtils.EMPTY, null);

    private final EnumMap<RangeOperator, Integer> cardinality = new EnumMap<>(RangeOperator.class);

    @Getter
    private final Field<Long> field;

    private final String parameterName;

    @Getter
    private RangeParameter<Long> lower;

    @Getter
    private RangeParameter<Long> upper;

    public Bound(RangeParameter<Long>[] params, boolean primarySortField, String parameterName, Field<Long> field) {
        this.field = field;
        this.parameterName = parameterName;
        if (ArrayUtils.isEmpty(params)) {
            return;
        }
        for (var param : params) {
            if (param.hasLowerBound()) {
                lower = param;
            } else if (param.hasUpperBound()) {
                upper = param;
            }
            cardinality.merge(param.operator(), 1, Math::addExact);
        }
        long adjustedLower = getAdjustedLowerRangeValue();
        long adjustedUpper = adjustUpperBound();
        if (primarySortField && adjustedLower > adjustedUpper) {
            throw new IllegalArgumentException("Invalid range provided for %s".formatted(parameterName));
        }
    }

    public long adjustUpperBound() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public RangeParameter<Long> adjustLowerRange() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public long getAdjustedLowerRangeValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void adjustUpperRange() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // Gets a range value if the operator is converted from GT/LT to EQ/GTE/LTE
    public long getInclusiveRangeValue(boolean upper) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int getCardinality(RangeOperator... operators) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isEmpty() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean hasLowerAndUpper() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean hasEqualBounds() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // Returns a new bound with only a lower rangeParameter
    public Bound toLower() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // Returns a new bound with only an upper rangeParameter
    public Bound toUpper() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void verifyUnsupported(RangeOperator unsupportedOperator) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void verifySingleOccurrence() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void verifyEqualOrRange() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Bound of(TimestampParameter[] timestamp, String parameterName, Field<Long> field) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private Bound createBound(RangeParameter<Long> param) {
        if (param == null) {
            return Bound.EMPTY;
        }
        var params = new NumberRangeParameter[] { new NumberRangeParameter(param.operator(), param.value()) };
        return new Bound(params, false, parameterName, field);
    }

    private void verifySingleOccurrence(RangeOperator... rangeOperators) {
        if (this.getCardinality(rangeOperators) > 1) {
            throw new IllegalArgumentException("Only one range operator from %s is allowed for the given parameter for %s".formatted(Arrays.toString(rangeOperators), parameterName));
        }
    }
}
