// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.grpc.service;

import jakarta.inject.Named;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import lombok.AccessLevel;
import lombok.CustomLog;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.hiero.mirror.common.domain.SystemEntity;
import org.hiero.mirror.common.domain.addressbook.AddressBookEntry;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.grpc.domain.AddressBookFilter;
import org.hiero.mirror.grpc.exception.EntityNotFoundException;
import org.hiero.mirror.grpc.repository.AddressBookEntryRepository;
import org.hiero.mirror.grpc.repository.AddressBookRepository;
import org.hiero.mirror.grpc.repository.NodeStakeRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.repeat.RepeatSpec;

@CustomLog
@Named
@RequiredArgsConstructor
@Validated
public class NetworkServiceImpl implements NetworkService {

    static final String INVALID_FILE_ID = "Not a valid address book file";

    private static final long NODE_STAKE_EMPTY_TABLE_TIMESTAMP = 0L;

    private final AddressBookProperties addressBookProperties;

    private final AddressBookRepository addressBookRepository;

    private final AddressBookEntryRepository addressBookEntryRepository;

    private final NodeStakeRepository nodeStakeRepository;

    private final SystemEntity systemEntity;

    @Qualifier("readOnly")
    private final TransactionOperations transactionOperations;

    @Getter(lazy = true, value = AccessLevel.PRIVATE)
    private final Set<EntityId> validFileIds = Set.of(systemEntity.addressBookFile101(), systemEntity.addressBookFile102());

    @Override
    public Flux<AddressBookEntry> getNodes(AddressBookFilter filter) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private Flux<AddressBookEntry> page(AddressBookContext context) {
        return transactionOperations.execute(t -> {
            var addressBookTimestamp = context.getAddressBookTimestamp();
            var nodeStakeMap = context.getNodeStakeMap();
            var nextNodeId = context.getNextNodeId();
            var pageSize = addressBookProperties.getPageSize();
            var nodes = addressBookEntryRepository.findByConsensusTimestampAndNodeId(addressBookTimestamp, nextNodeId, pageSize);
            var endpoints = new AtomicInteger(0);
            nodes.forEach(node -> {
                // Override node stake
                node.setStake(nodeStakeMap.getOrDefault(node.getNodeId(), 0L));
                // This hack ensures that the nested serviceEndpoints is loaded eagerly and voids lazy init exceptions
                endpoints.addAndGet(node.getServiceEndpoints().size());
            });
            if (nodes.size() < pageSize) {
                context.completed();
            }
            log.info("Retrieved {} address book entries and {} endpoints for timestamp {} and node ID {}", nodes.size(), endpoints, addressBookTimestamp, nextNodeId);
            return Flux.fromIterable(nodes);
        });
    }

    @Value
    private static class AddressBookContext {

        private final AtomicBoolean complete = new AtomicBoolean(false);

        private final AtomicLong count = new AtomicLong(0L);

        private final AtomicReference<AddressBookEntry> last = new AtomicReference<>();

        private final long addressBookTimestamp;

        private final Map<Long, Long> nodeStakeMap;

        void onNext(AddressBookEntry entry) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        long getNextNodeId() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        boolean isComplete() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void completed() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
