// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.restjava.mapper;

import com.google.common.collect.Range;
import com.google.protobuf.InvalidProtocolBufferException;
import com.hederahashgraph.api.proto.java.KeyList;
import com.hederahashgraph.api.proto.java.TimestampSeconds;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.util.DomainUtils;
import org.hiero.mirror.rest.model.Key;
import org.hiero.mirror.rest.model.Key.TypeEnum;
import org.hiero.mirror.rest.model.TimestampRange;
import org.hiero.mirror.rest.model.TimestampRangeNullable;
import org.hiero.mirror.restjava.exception.InvalidMappingException;
import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.Named;

@Mapper(mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG)
public interface CommonMapper {

    byte[] IMMUTABILITY_SENTINEL_KEY = com.hederahashgraph.api.proto.java.Key.newBuilder().setKeyList(KeyList.getDefaultInstance()).build().toByteArray();

    String QUALIFIER_TIMESTAMP = "timestamp";

    String QUALIFIER_TIMESTAMP_RANGE = "timestampRange";

    int NANO_DIGITS = 9;

    int FRACTION_SCALE = 9;

    Pattern PATTERN_ECDSA = Pattern.compile("^(3a21|32250a233a21|2a29080112250a233a21)([A-Fa-f0-9]{66})$");

    Pattern PATTERN_ED25519 = Pattern.compile("^(1220|32240a221220|2a28080112240a221220)([A-Fa-f0-9]{64})$");

    long SECONDS_PER_DAY = 86400L;

    String TIMESTAMP_ZERO = "0.0";

    default String mapByteArrayToHexString(byte[] source) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default String mapEntityId(Long source) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default String mapEntityId(EntityId source) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default Key mapKey(byte[] source) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default List<Key> mapKeyList(byte[] source) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default String mapLowerRange(Range<Long> source) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default TimestampRange mapRange(Range<Long> source) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default TimestampRangeNullable mapTimestampRangeNullable(Range<Long> source) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Named(QUALIFIER_TIMESTAMP)
    default String mapTimestamp(Long timestamp) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default Long mapTimestampSeconds(TimestampSeconds source) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Named(QUALIFIER_TIMESTAMP_RANGE)
    default TimestampRange mapTimestampRange(long stakingPeriod) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Calculates the fractional value of a numerator and denominator as a float with up to {@value #FRACTION_SCALE}
     * decimal places.
     *
     * @param numerator   the numerator of the fraction
     * @param denominator the denominator of the fraction
     * @return the result of numerator / denominator as a float, or 0.0f if denominator is 0
     */
    default float mapFraction(long numerator, long denominator) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
