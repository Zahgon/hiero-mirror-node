// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.restjava.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Collections;
import java.util.List;
import org.hiero.mirror.common.converter.ObjectToStringSerializer;
import org.hiero.mirror.common.util.DomainUtils;
import org.hiero.mirror.rest.model.NetworkNode;
import org.hiero.mirror.rest.model.ServiceEndpoint;
import org.hiero.mirror.rest.model.TimestampRange;
import org.hiero.mirror.rest.model.TimestampRangeNullable;
import org.hiero.mirror.restjava.dto.NetworkNodeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfiguration.class)
public interface NetworkNodeMapper extends CollectionMapper<NetworkNodeDto, NetworkNode> {

    @Override
    @Mapping(target = "grpcProxyEndpoint", expression = "java(parseServiceEndpoint(row.grpcProxyEndpointJson()))")
    @Mapping(target = "serviceEndpoints", expression = "java(parseServiceEndpointList(row.serviceEndpointsJson()))")
    @Mapping(target = "stakingPeriod", qualifiedByName = "mapStakingPeriod")
    @Mapping(target = "timestamp", expression = "java(mapTimestampRange(row))")
    NetworkNode map(NetworkNodeDto row);

    default TimestampRange mapTimestampRange(NetworkNodeDto row) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Named("mapStakingPeriod")
    default TimestampRangeNullable mapStakingPeriod(Long stakingPeriod) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default ServiceEndpoint parseServiceEndpoint(String json) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default List<ServiceEndpoint> parseServiceEndpointList(String json) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
