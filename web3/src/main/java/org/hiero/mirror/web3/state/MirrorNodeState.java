// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.state;

import static com.hedera.node.app.service.contract.impl.schemas.V065ContractSchema.EVM_HOOK_STATES_STATE_ID;
import static com.hedera.node.app.service.contract.impl.schemas.V065ContractSchema.EVM_HOOK_STORAGE_STATE_ID;
import static com.hedera.node.app.service.entityid.impl.schemas.V0730EntityIdSchema.HIGHEST_NODE_ID_STATE_ID;
import static com.hedera.node.app.service.schedule.impl.schemas.V0570ScheduleSchema.SCHEDULED_COUNTS_STATE_ID;
import static com.hedera.node.app.service.schedule.impl.schemas.V0570ScheduleSchema.SCHEDULED_USAGES_STATE_ID;
import static com.hedera.node.app.service.schedule.impl.schemas.V0570ScheduleSchema.SCHEDULE_ID_BY_EQUALITY_STATE_ID;
import static com.hedera.node.app.service.token.impl.schemas.V0490TokenSchema.STAKING_NETWORK_REWARDS_STATE_ID;
import static com.hedera.node.app.service.token.impl.schemas.V0610TokenSchema.NODE_REWARDS_STATE_ID;
import static com.hedera.node.app.state.recordcache.schemas.V0490RecordCacheSchema.TRANSACTION_RECEIPTS_STATE_ID;
import com.google.common.annotations.VisibleForTesting;
import com.hedera.node.app.service.contract.ContractService;
import com.hedera.node.app.service.entityid.EntityIdService;
import com.hedera.node.app.service.schedule.ScheduleService;
import com.hedera.node.app.service.token.TokenService;
import com.hedera.node.app.state.recordcache.RecordCacheService;
import com.swirlds.state.State;
import com.swirlds.state.spi.EmptyWritableStates;
import com.swirlds.state.spi.ReadableKVState;
import com.swirlds.state.spi.ReadableStates;
import com.swirlds.state.spi.WritableStates;
import jakarta.inject.Named;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import org.hiero.base.crypto.Hash;
import org.hiero.mirror.web3.state.core.FunctionReadableSingletonState;
import org.hiero.mirror.web3.state.core.FunctionWritableSingletonState;
import org.hiero.mirror.web3.state.core.ListReadableQueueState;
import org.hiero.mirror.web3.state.core.ListWritableQueueState;
import org.hiero.mirror.web3.state.core.MapReadableKVState;
import org.hiero.mirror.web3.state.core.MapReadableStates;
import org.hiero.mirror.web3.state.core.MapWritableKVState;
import org.hiero.mirror.web3.state.core.MapWritableStates;
import org.hiero.mirror.web3.state.keyvalue.AbstractReadableKVState;
import org.hiero.mirror.web3.state.singleton.DefaultSingleton;
import org.hiero.mirror.web3.state.singleton.SingletonState;
import org.jspecify.annotations.NonNull;

@Named
public class MirrorNodeState implements State {

    private final Map<String, ReadableStates> readableStates = new ConcurrentHashMap<>();

    private final Map<String, WritableStates> writableStates = new ConcurrentHashMap<>();

    // Key is Service, value is Map of state name to state datasource
    private final Map<String, Map<Integer, Object>> states = new HashMap<>();

    public MirrorNodeState(final List<SingletonState<?>> singletonStates, final List<AbstractReadableKVState<?, ?>> readableKVStates) {
        initSingletonStates(singletonStates);
        initKVStates(readableKVStates);
        initQueueStates();
    }

    @NonNull
    @Override
    public ReadableStates getReadableStates(@NonNull String serviceName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @NonNull
    @Override
    public WritableStates getWritableStates(@NonNull String serviceName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void setHash(Hash hash) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @VisibleForTesting
    Map<String, Map<Integer, Object>> getStates() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void initSingletonStates(final List<SingletonState<?>> singletonStates) {
        singletonStates.add(new DefaultSingleton(EntityIdService.NAME, HIGHEST_NODE_ID_STATE_ID));
        singletonStates.add(new DefaultSingleton(TokenService.NAME, STAKING_NETWORK_REWARDS_STATE_ID));
        singletonStates.add(new DefaultSingleton(TokenService.NAME, NODE_REWARDS_STATE_ID));
        singletonStates.forEach(singletonState -> states.computeIfAbsent(singletonState.getServiceName(), k -> new HashMap<>()).put(singletonState.getStateId(), singletonState));
    }

    private void initKVStates(final List<AbstractReadableKVState<?, ?>> readableKVStates) {
        readableKVStates.forEach(kvState -> states.computeIfAbsent(kvState.getServiceName(), k -> new HashMap<>()).put(kvState.getStateId(), kvState));
        final var defaultKvImplementations = Map.of(EVM_HOOK_STORAGE_STATE_ID, ContractService.NAME, EVM_HOOK_STATES_STATE_ID, ContractService.NAME, SCHEDULE_ID_BY_EQUALITY_STATE_ID, ScheduleService.NAME, SCHEDULED_COUNTS_STATE_ID, ScheduleService.NAME, SCHEDULED_USAGES_STATE_ID, ScheduleService.NAME);
        defaultKvImplementations.forEach((stateId, serviceName) -> states.computeIfAbsent(serviceName, k -> new HashMap<>()).put(stateId, createMapReadableStateForId(serviceName, stateId)));
    }

    private void initQueueStates() {
        states.put(RecordCacheService.NAME, new HashMap<>(Map.of(TRANSACTION_RECEIPTS_STATE_ID, new LinkedList<>())));
    }

    private MapReadableKVState<?, ?> createMapReadableStateForId(final String serviceName, int id) {
        return new MapReadableKVState<>(serviceName, id, new HashMap<>());
    }
}
