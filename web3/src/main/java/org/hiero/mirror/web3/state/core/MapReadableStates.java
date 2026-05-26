// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.state.core;

import com.swirlds.state.spi.ReadableKVState;
import com.swirlds.state.spi.ReadableQueueState;
import com.swirlds.state.spi.ReadableSingletonState;
import java.util.Map;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

@SuppressWarnings("unchecked")
public class MapReadableStates extends AbstractMapReadableState {

    public MapReadableStates(@NonNull final Map<Integer, ?> states) {
        super(states);
    }

    @NonNull
    @Override
    public <K, V> ReadableKVState<K, V> get(int stateId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @NonNull
    @Override
    public <T> ReadableSingletonState<T> getSingleton(int stateId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @NonNull
    @Override
    public <E> ReadableQueueState<E> getQueue(int stateId) {
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
}
