// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.restjava.converter;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import org.jooq.postgres.extensions.types.LongRange;
import org.springframework.core.convert.converter.Converter;

@SuppressWarnings("java:S6548")
public class LongRangeConverter implements Converter<LongRange, Range<Long>> {

    public static final LongRangeConverter INSTANCE = new LongRangeConverter();

    @Override
    public Range<Long> convert(LongRange source) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
