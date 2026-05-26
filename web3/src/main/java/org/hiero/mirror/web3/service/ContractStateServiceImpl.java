// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.service;

import static org.hiero.mirror.web3.evm.config.EvmConfiguration.CACHE_MANAGER_CONTRACT_SLOTS;
import static org.hiero.mirror.web3.evm.config.EvmConfiguration.CACHE_MANAGER_CONTRACT_STATE;
import static org.hiero.mirror.web3.evm.config.EvmConfiguration.CACHE_MANAGER_SLOTS_PER_CONTRACT;
import static org.hiero.mirror.web3.evm.config.EvmConfiguration.CACHE_NAME;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import lombok.CustomLog;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.web3.repository.ContractStateRepository;
import org.hiero.mirror.web3.repository.properties.CacheProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.stereotype.Service;

@CustomLog
@Service
final class ContractStateServiceImpl implements ContractStateService {

    private static final byte[] EMPTY_VALUE = new byte[0];

    private final CacheManager cacheManagerSlotsPerContract;

    private final CacheProperties cacheProperties;

    private final Cache contractSlotsCache;

    private final Cache contractStateCache;

    private final ContractStateRepository contractStateRepository;

    ContractStateServiceImpl(@Qualifier(CACHE_MANAGER_CONTRACT_SLOTS) final CacheManager cacheManagerContractSlots, @Qualifier(CACHE_MANAGER_CONTRACT_STATE) final CacheManager cacheManagerContractState, @Qualifier(CACHE_MANAGER_SLOTS_PER_CONTRACT) final CacheManager cacheManagerSlotsPerContract, final CacheProperties cacheProperties, final ContractStateRepository contractStateRepository) {
        this.cacheManagerSlotsPerContract = cacheManagerSlotsPerContract;
        this.cacheProperties = cacheProperties;
        this.contractSlotsCache = cacheManagerContractSlots.getCache(CACHE_NAME);
        this.contractStateCache = cacheManagerContractState.getCache(CACHE_NAME);
        this.contractStateRepository = contractStateRepository;
    }

    /**
     * Executes findStorageBatch query if the slot value is not cached.
     *
     * @param contractId Entity ID of the contract that the slot key belongs to
     * @param key        The slot key of the slot value we are looking for
     * @return slot value as 32-length left padded Bytes
     */
    @Override
    public Optional<byte[]> findStorage(final EntityId contractId, final byte[] key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Optional<byte[]> findStorageByBlockTimestamp(final EntityId entityId, final byte[] slotKeyByteArray, final long blockTimestamp) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Executes a batch query, returning slotKey-value pairs for contractId, then caches the result. The goal of the
     * query is to preload previously requested data to avoid additional queries against the db.
     *
     * @param contractId id of the contract that the slotKey-value pairs are queried for.
     * @return slotKey-value pairs for contractId
     */
    private Optional<byte[]> findStorageBatch(final EntityId contractId, final byte[] key) {
        final var contractSlotsCache = ((CaffeineCache) this.contractSlotsCache.get(contractId, () -> cacheManagerSlotsPerContract.getCache(contractId.toString())));
        final var wrappedKey = ByteBuffer.wrap(key);
        // Cached slot keys for contract, whose slot values are not present in the contractStateCache
        contractSlotsCache.putIfAbsent(wrappedKey, EMPTY_VALUE);
        final var cachedSlotKeys = contractSlotsCache.getNativeCache().asMap().keySet();
        final var cachedSlots = new ArrayList<byte[]>(cachedSlotKeys.size());
        boolean isKeyEvictedFromCache = true;
        for (var slot : cachedSlotKeys) {
            cachedSlots.add(((ByteBuffer) slot).array());
            if (wrappedKey.equals(slot)) {
                isKeyEvictedFromCache = false;
            }
        }
        final var contractSlotValues = contractStateRepository.findStorageBatch(contractId.getId(), cachedSlots);
        byte[] cachedValue = null;
        for (final var contractSlotValue : contractSlotValues) {
            final byte[] slotKey = contractSlotValue.getSlot();
            final byte[] slotValue = contractSlotValue.getValue();
            contractStateCache.put(generateCacheKey(contractId, slotKey), slotValue);
            if (Arrays.equals(slotKey, key)) {
                cachedValue = slotValue;
            }
        }
        // If the cache key was evicted and hasn't been requested since, the cached value will be null.
        // In that case, fall back to the original query.
        if (isKeyEvictedFromCache) {
            return contractStateRepository.findStorage(contractId.getId(), key);
        }
        return Optional.ofNullable(cachedValue);
    }

    // Generates a cache key emulating the default caching behavior in Spring
    private SimpleKey generateCacheKey(final EntityId contractId, final byte[] slotKey) {
        return new SimpleKey(contractId, slotKey);
    }
}
