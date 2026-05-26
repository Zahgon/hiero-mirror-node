// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.utils;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class Suppliers {

    private Suppliers() {
    }

    /**
     * Returns a supplier which caches the instance retrieved during the first call to {@code get()}
     * and returns that value on subsequent calls to {@code get()}. See: <a
     * href="http://en.wikipedia.org/wiki/Memoization">memoization</a>
     *
     * <p>The supplier's serialized
     * form does not contain the cached value, which will be recalculated when {@code get()} is called
     * on the deserialized instance.
     *
     * <p>When the underlying delegate throws an exception then this memoizing supplier will keep
     * delegating calls until it returns valid data.
     *
     * <p>If {@code delegate} is an instance created by an earlier call to {@code memoize}, it is
     * returned directly.
     */
    public static <T> Supplier<T> memoize(Supplier<T> delegate) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compares two Suppliers of values for equality, handling null checks.
     *
     * @param <T> the type of the value
     * @param supplier1 the first supplier to compare
     * @param supplier2 the second supplier to compare
     * @return true if both suppliers produce equal values, or if both are null; false otherwise
     */
    public static <T> boolean areSuppliersEqual(final Supplier<T> supplier1, final Supplier<T> supplier2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
