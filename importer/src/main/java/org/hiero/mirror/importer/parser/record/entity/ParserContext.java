// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.entity;

import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Stores the domain objects parsed from the stream files before persisting to the database.
 */
@Named
@NullMarked
public class ParserContext {

    private final Map<Class<?>, DomainContext<?>> state = new ConcurrentSkipListMap<>(new DomainClassComparator());

    private final Set<Long> evmAddressLookupIds = new HashSet<>();

    public <T> void addTransient(T object) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public <T> void add(T object) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public <T> void add(T object, @Nullable Object key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public <T> void addAll(Collection<T> objects) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void clear() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void forEach(Consumer<Collection<?>> sink) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Nullable
    public <T> T get(Class<T> domainClass, Object key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public <T> Collection<T> get(Class<T> domainClass) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public <T> Collection<T> getTransient(Class<T> domainClass) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public <T> void merge(Object key, T value, BinaryOperator<T> mergeFunction) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void remove(Class<?> domainClass) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Collection<Long> getEvmAddressLookupIds() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void addEvmAddressLookupId(long id) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressWarnings("unchecked")
    private <T> DomainContext<T> getDomainContext(T object) {
        var domainClass = (Class<T>) object.getClass();
        return getDomainContext(domainClass);
    }

    @SuppressWarnings("unchecked")
    private <T> DomainContext<T> getDomainContext(Class<T> domainClass) {
        return (DomainContext<T>) state.computeIfAbsent(domainClass, c -> new DomainContext<>());
    }

    private class DomainContext<T> {

        @Getter
        private final List<T> inserts = new ArrayList<>();

        @Getter(lazy = true)
        private final Map<Object, T> state = new HashMap<>();

        @Getter
        private final List<T> nonPersisted = new ArrayList<>();

        void clear() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
