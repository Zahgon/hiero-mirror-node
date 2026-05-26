// SPDX-License-Identifier: Apache-2.0
package com.swirlds.state.spi;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.hiero.mirror.web3.common.ContractCallContext;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * A base class for implementations of {@link WritableKVState}.
 *
 * @param <K> The key type
 * @param <V> The value type
 */
@SuppressWarnings("unchecked")
public abstract class WritableKVStateBase<K, V> extends ReadableKVStateBase<K, V> implements WritableKVState<K, V> {

    /**
     * Create a new StateBase.
     *
     * @param serviceName The name of the service that owns the state. Cannot be null.
     * @param stateId The state id.
     */
    protected WritableKVStateBase(@NonNull final String serviceName, final int stateId) {
        super(stateId, serviceName);
    }

    /**
     * Compatibility constructor matching platform-sdk signature (int, String).
     * Some components (e.g., WrappedWritableKVState) expect this exact signature at runtime.
     *
     * @param stateId The state ID
     * @param label The state label (may be null)
     */
    protected WritableKVStateBase(final int stateId, final String label) {
        super(stateId, label);
    }

    /**
     * Flushes all changes into the underlying data store. This method should <strong>ONLY</strong>
     * be called by the code that created the {@link WritableKVStateBase} instance or owns it. Don't
     * cast and commit unless you own the instance!
     */
    public void commit() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Clears the set of modified keys and removed keys. Equivalent semantically to a "rollback"
     * operation.
     */
    @Override
    public final void reset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nullable
    public final V get(@NonNull K key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public V getOriginalValue(@NonNull K key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void put(@NonNull final K key, @NonNull final V value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void remove(@NonNull final K key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public final Set<K> modifiedKeys() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     * For the size of a {@link WritableKVState}, we need to take into account the size of the
     * underlying data source, and the modifications that have been made to the state.
     * <ol>
     * <li>if the key is in backing store and is removed in modifications, then it is counted as removed</li>
     * <li>if the key is not in backing store and is added in modifications, then it is counted as addition</li>
     * <li>if the key is in backing store and is added in modifications, then it is not counted as the
     * key already exists in state</li>
     * <li>if the key is not in backing store and is being tried to be removed in modifications,
     * then it is not counted as the key does not exist in state.</li>
     * </ol>
     * @return The size of the state.
     */
    @Deprecated
    public long size() {
        final var sizeOfBackingMap = sizeOfDataSource();
        int numAdditions = 0;
        int numRemovals = 0;
        for (final var mod : getWriteCacheState().entrySet()) {
            boolean isPresentInBackingMap = readFromDataSource((K) mod.getKey()) != null;
            boolean isRemovedInMod = mod.getValue() == null;
            if (isPresentInBackingMap && isRemovedInMod) {
                numRemovals++;
            } else if (!isPresentInBackingMap && !isRemovedInMod) {
                numAdditions++;
            }
        }
        return sizeOfBackingMap + numAdditions - numRemovals;
    }

    /**
     * Puts the given key/value pair into the underlying data source.
     *
     * @param key key to update
     * @param value value to put
     */
    protected abstract void putIntoDataSource(@NonNull K key, @NonNull V value);

    /**
     * Removes the given key and implicit value from the underlying data source.
     *
     * @param key key to remove from the underlying data source
     */
    protected abstract void removeFromDataSource(@NonNull K key);

    /**
     * Returns the size of the underlying data source. This can be a merkle map or a virtual map.
     * @return size of the underlying data source.
     */
    protected abstract long sizeOfDataSource();

    private Map<Object, Object> getWriteCacheState() {
        return ContractCallContext.get().getWriteCacheState(getStateId());
    }
}
