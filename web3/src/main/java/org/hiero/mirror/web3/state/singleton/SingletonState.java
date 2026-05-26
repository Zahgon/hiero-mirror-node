// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.state.singleton;

import java.util.function.Supplier;
import org.hiero.mirror.web3.state.RegisterableState;

public interface SingletonState<T> extends Supplier<T>, RegisterableState {

    default void set(T value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
