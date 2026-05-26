// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.restjava.service;

import jakarta.inject.Named;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.common.domain.hook.Hook;
import org.hiero.mirror.common.domain.hook.HookStorage;
import org.hiero.mirror.restjava.dto.HookStorageRequest;
import org.hiero.mirror.restjava.dto.HookStorageResult;
import org.hiero.mirror.restjava.dto.HooksRequest;
import org.hiero.mirror.restjava.repository.HookRepository;
import org.hiero.mirror.restjava.repository.HookStorageChangeRepository;
import org.hiero.mirror.restjava.repository.HookStorageRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Named
@RequiredArgsConstructor
final class HookServiceImpl implements HookService {

    private static final String HOOK_ID = "hookId";

    private final HookRepository hookRepository;

    private final HookStorageRepository hookStorageRepository;

    private final HookStorageChangeRepository hookStorageChangeRepository;

    private final EntityService entityService;

    @Override
    public Collection<Hook> getHooks(HooksRequest request) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public HookStorageResult getHookStorage(HookStorageRequest request) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private HookStorageResult getHookStorageChange(HookStorageRequest request) {
        final var page = request.getPageRequest();
        final var ownerId = entityService.lookup(request.getOwnerId());
        final long hookId = request.getHookId();
        final var keys = request.getKeys();
        final boolean requestHasKeys = !keys.isEmpty();
        final var keysInRange = request.getKeysInRange();
        if (keysInRange.isEmpty() && requestHasKeys) {
            return new HookStorageResult(ownerId, List.of());
        }
        final var timestamp = request.getTimestamp();
        final long timestampLowerBound = timestamp.getAdjustedLowerRangeValue();
        final long timestampUpperBound = timestamp.adjustUpperBound();
        List<HookStorage> changes;
        if (requestHasKeys) {
            changes = hookStorageChangeRepository.findByKeyInAndTimestampBetween(ownerId.getId(), hookId, keys, timestampLowerBound, timestampUpperBound, page);
        } else {
            changes = hookStorageChangeRepository.findByKeyBetweenAndTimestampBetween(ownerId.getId(), hookId, request.getKeyLowerBound(), request.getKeyUpperBound(), timestampLowerBound, timestampUpperBound, page);
        }
        return new HookStorageResult(ownerId, changes);
    }
}
