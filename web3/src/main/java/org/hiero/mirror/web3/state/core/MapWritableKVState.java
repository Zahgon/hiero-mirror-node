// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.state.core;

import com.swirlds.state.spi.ReadableKVState;
import com.swirlds.state.spi.WritableKVStateBase;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

@SuppressWarnings("deprecation")
public class MapWritableKVState<K, V> extends WritableKVStateBase<K, V> {

    private final ReadableKVState<K, V> readableBackingStore;

    public MapWritableKVState(@NonNull final String serviceName, final int stateId, @NonNull final ReadableKVState<K, V> readableBackingStore) {
        super(serviceName, stateId);
        this.readableBackingStore = Objects.requireNonNull(readableBackingStore);
    }

    @Override
    protected V readFromDataSource(@NonNull K key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void putIntoDataSource(@NonNull K key, @NonNull V value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void removeFromDataSource(@NonNull K key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public long sizeOfDataSource() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString() {
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
