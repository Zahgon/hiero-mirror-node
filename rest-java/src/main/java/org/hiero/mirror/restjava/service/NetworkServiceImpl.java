// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.restjava.service;

import static java.lang.Long.MAX_VALUE;
import static org.hiero.mirror.restjava.jooq.domain.tables.RegisteredNode.REGISTERED_NODE;
import com.google.common.collect.Range;
import jakarta.inject.Named;
import jakarta.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.common.domain.SystemEntity;
import org.hiero.mirror.common.domain.addressbook.NetworkStake;
import org.hiero.mirror.common.domain.node.RegisteredNode;
import org.hiero.mirror.common.util.DomainUtils;
import org.hiero.mirror.restjava.common.RangeOperator;
import org.hiero.mirror.restjava.config.NetworkProperties;
import org.hiero.mirror.restjava.dto.NetworkNodeDto;
import org.hiero.mirror.restjava.dto.NetworkNodeRequest;
import org.hiero.mirror.restjava.dto.NetworkSupply;
import org.hiero.mirror.restjava.dto.RegisteredNodesRequest;
import org.hiero.mirror.restjava.parameter.NumberRangeParameter;
import org.hiero.mirror.restjava.repository.AccountBalanceRepository;
import org.hiero.mirror.restjava.repository.EntityRepository;
import org.hiero.mirror.restjava.repository.NetworkNodeRepository;
import org.hiero.mirror.restjava.repository.NetworkStakeRepository;
import org.hiero.mirror.restjava.repository.RegisteredNodeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Named
@RequiredArgsConstructor
final class NetworkServiceImpl implements NetworkService {

    private static final Long[] EMPTY_NODE_ID_ARRAY = new Long[0];

    private final AccountBalanceRepository accountBalanceRepository;

    private final EntityRepository entityRepository;

    private final NetworkStakeRepository networkStakeRepository;

    private final NetworkProperties networkProperties;

    private final NetworkNodeRepository networkNodeRepository;

    private final RegisteredNodeRepository registeredNodeRepository;

    private final SystemEntity systemEntity;

    @Override
    public NetworkStake getLatestNetworkStake() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public NetworkSupply getSupply(Bound timestamp) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private long getFirstDayOfMonth(long timestamp, int monthOffset) {
        final var instant = Instant.ofEpochSecond(0, timestamp);
        final var dateTime = instant.atZone(ZoneOffset.UTC);
        final var firstDay = dateTime.plusMonths(monthOffset).withDayOfMonth(1);
        return firstDay.toLocalDate().atStartOfDay(ZoneOffset.UTC).toEpochSecond() * DomainUtils.NANOS_PER_SECOND;
    }

    @Override
    public List<NetworkNodeDto> getNetworkNodes(NetworkNodeRequest request) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Collection<RegisteredNode> getRegisteredNodes(RegisteredNodesRequest request) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static Range<Long> resolveRegisteredNodeIdBounds(List<NumberRangeParameter> registeredNodeIdRanges) {
        long lowerBound = 0L;
        long upperBound = MAX_VALUE;
        for (final var range : registeredNodeIdRanges) {
            if (range.operator() == RangeOperator.EQ) {
                if (registeredNodeIdRanges.size() > 1) {
                    throw new IllegalArgumentException("The 'eq' operator cannot be combined with other operators");
                }
                return Range.closed(range.value(), range.value());
            } else if (range.hasLowerBound()) {
                lowerBound = Math.max(lowerBound, range.getInclusiveValue());
            } else if (range.hasUpperBound()) {
                upperBound = Math.min(upperBound, range.getInclusiveValue());
            }
        }
        if (lowerBound > upperBound) {
            throw new IllegalArgumentException("Invalid range: lower bound exceeds upper bound");
        }
        return Range.closed(lowerBound, upperBound);
    }

    private long getAddressBookFileId(final NetworkNodeRequest request) {
        return request.getFileId() != null ? request.getFileId().value() : systemEntity.addressBookFile102().getId();
    }
}
