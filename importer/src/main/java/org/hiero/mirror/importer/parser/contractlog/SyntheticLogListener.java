// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.contractlog;

import static org.hiero.mirror.common.util.DomainUtils.fromTrimmedEvmAddress;
import static org.hiero.mirror.common.util.DomainUtils.trim;
import static org.hiero.mirror.importer.parser.contractlog.SyntheticContractLogServiceImpl.CONTRACT_LOG_MARKER;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.CaffeineSpec;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.google.common.collect.Iterators;
import io.micrometer.core.annotation.Timed;
import jakarta.inject.Named;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import lombok.AccessLevel;
import lombok.CustomLog;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.hiero.mirror.common.domain.contract.ContractLog;
import org.hiero.mirror.common.domain.contract.ContractResult;
import org.hiero.mirror.common.domain.entity.Entity;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.domain.transaction.RecordFile;
import org.hiero.mirror.common.util.DomainUtils;
import org.hiero.mirror.common.util.LogsBloomFilter;
import org.hiero.mirror.importer.config.CacheProperties;
import org.hiero.mirror.importer.domain.EvmAddressMapping;
import org.hiero.mirror.importer.parser.record.RecordStreamFileListener;
import org.hiero.mirror.importer.parser.record.entity.EntityListener;
import org.hiero.mirror.importer.parser.record.entity.EntityProperties;
import org.hiero.mirror.importer.parser.record.entity.ParserContext;
import org.hiero.mirror.importer.repository.EntityRepository;
import org.springframework.core.annotation.Order;

@Named
@Order(2)
@CustomLog
@RequiredArgsConstructor
final class SyntheticLogListener implements EntityListener, RecordStreamFileListener {

    static final int MAX_CACHE_LOAD_ENTRIES = 30000;

    @Getter(lazy = true)
    private final LoadingCache<Long, byte[]> evmCache = buildCache();

    private final ParserContext parserContext;

    private final CacheProperties cacheProperties;

    private final EntityRepository entityRepository;

    private final EntityProperties entityProperties;

    @Override
    public boolean isEnabled() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @Timed
    public void onEnd(RecordFile recordFile) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void updateRecordFileBloom(final RecordFile recordFile, final Collection<ContractResult> contractResults) {
        if (contractResults.isEmpty()) {
            return;
        }
        final var aggregatedBloom = aggregateRecordFileBloom(recordFile, contractResults);
        if (aggregatedBloom.length == LogsBloomFilter.BYTE_SIZE) {
            recordFile.setLogsBloom(aggregatedBloom);
        }
    }

    private byte[] aggregateRecordFileBloom(final RecordFile recordFile, final Collection<ContractResult> contractResults) {
        var aggregatedBloom = recordFile.getLogsBloom() != null ? recordFile.getLogsBloom() : new byte[LogsBloomFilter.BYTE_SIZE];
        for (final var contractResult : contractResults) {
            aggregatedBloom = LogsBloomFilter.or(contractResult.getBloom(), aggregatedBloom);
        }
        return aggregatedBloom;
    }

    @Override
    public void onContractLog(ContractLog contractLog) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private LoadingCache<Long, byte[]> buildCache() {
        return Caffeine.from(CaffeineSpec.parse(cacheProperties.getEvmAddress())).build(new CacheLoader<>() {

            @Override
            public byte[] load(Long key) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

            @Override
            public Map<Long, byte[]> loadAll(Set<? extends Long> keys) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        });
    }

    private Map<Long, byte[]> getCacheMisses(Set<? extends Long> keys) {
        if (keys.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<Long, byte[]> loadedFromDb = new HashMap<>(keys.size());
        if (keys.size() <= MAX_CACHE_LOAD_ENTRIES) {
            processBatch(loadedFromDb, entityRepository.findEvmAddressesByIds(keys));
        } else {
            final var keyIterator = keys.iterator();
            final var batches = Iterators.partition(keyIterator, MAX_CACHE_LOAD_ENTRIES);
            while (batches.hasNext()) {
                List<? extends Long> batch = batches.next();
                processBatch(loadedFromDb, entityRepository.findEvmAddressesByIds(batch));
            }
        }
        return loadedFromDb;
    }

    private void processBatch(Map<Long, byte[]> result, List<EvmAddressMapping> evmAddressMappings) {
        for (var mapping : evmAddressMappings) {
            result.put(mapping.getId(), trim(mapping.getEvmAddress()));
        }
    }

    @Getter(AccessLevel.PACKAGE)
    @RequiredArgsConstructor
    class SyntheticLogUpdater {

        private final EntityId contractId;

        private final EntityId sender;

        private final EntityId receiver;

        private final ContractLog contractLog;

        public void populateSearchIds() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void updateContractLog(Map<Long, byte[]> entityEvmAddresses) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Retrieves the EVM address for a contract. Returns the alias if available,
         * otherwise returns the long-zero address (contract num as hex representation).
         *
         * @param contractEntityId the contract entity ID
         * @param entityEvmAddresses the map of entity IDs to EVM addresses
         * @return the contract address as a byte array
         */
        private byte[] getContractAddress(final EntityId contractEntityId, final Map<Long, byte[]> entityEvmAddresses) {
            if (EntityId.isEmpty(contractEntityId)) {
                return DomainUtils.toEvmAddress(contractLog.getContractId());
            }
            var cachedEvmAddress = entityEvmAddresses.get(contractEntityId.getId());
            if (cachedEvmAddress != null) {
                return cachedEvmAddress;
            }
            var contextEntity = parserContext.get(Entity.class, contractEntityId.getId());
            if (contextEntity != null && !ArrayUtils.isEmpty(contextEntity.getEvmAddress())) {
                var trimmedEvmAddress = trim(contextEntity.getEvmAddress());
                getEvmCache().put(contractEntityId.getId(), trimmedEvmAddress);
                return trimmedEvmAddress;
            }
            var longZeroAddress = DomainUtils.toEvmAddress(contractEntityId);
            getEvmCache().put(contractEntityId.getId(), longZeroAddress);
            return longZeroAddress;
        }

        /**
         * Creates a bloom filter for a synthetic contract log using the log's address, topics, and data.
         *
         * @param contractAddress the contract address to use in the bloom filter
         * @return the bloom filter as a byte array
         */
        private byte[] createBloom(byte[] contractAddress) {
            final var logsBloomFilter = new LogsBloomFilter();
            logsBloomFilter.insertAddress(contractAddress);
            logsBloomFilter.insertTopic(contractLog.getTopic0());
            logsBloomFilter.insertTopic(contractLog.getTopic1());
            logsBloomFilter.insertTopic(contractLog.getTopic2());
            logsBloomFilter.insertTopic(contractLog.getTopic3());
            return logsBloomFilter.toArrayUnsafe();
        }

        public void updateTopicField(final EntityId key, final Map<Long, byte[]> entityEvmAddresses, final Consumer<byte[]> setter, final byte[] defaultValue) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
