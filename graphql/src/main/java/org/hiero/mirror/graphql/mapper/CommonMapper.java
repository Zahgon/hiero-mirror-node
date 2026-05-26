// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.graphql.mapper;

import static org.apache.commons.codec.binary.Base64.encodeBase64String;
import static org.hiero.mirror.common.util.DomainUtils.toBytes;
import com.google.common.collect.Range;
import com.hederahashgraph.api.proto.java.ContractID;
import com.hederahashgraph.api.proto.java.Key;
import com.hederahashgraph.api.proto.java.KeyList;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.hiero.mirror.graphql.viewmodel.EntityId;
import org.hiero.mirror.graphql.viewmodel.TimestampRange;
import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

@Mapper(mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG)
public interface CommonMapper {

    Logger logger = LoggerFactory.getLogger(CommonMapper.class);

    String CONTRACT_ID = "CONTRACT_ID";

    String KEYS = "keys";

    String THRESHOLD = "threshold";

    default EntityId mapContractId(ContractID contractID) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default Duration mapDuration(Long source) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default EntityId mapEntityId(Long source) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default EntityId mapEntityId(org.hiero.mirror.common.domain.entity.EntityId source) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default Instant mapInstant(Long source) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default Object mapKey(byte[] source) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressWarnings("deprecation")
    default Object mapKey(Key key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default List<Object> mapKeyList(KeyList keyList) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default TimestampRange mapRange(Range<Long> source) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
