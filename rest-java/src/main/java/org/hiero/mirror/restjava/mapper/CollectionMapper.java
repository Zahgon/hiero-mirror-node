// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.restjava.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.util.CollectionUtils;

public interface CollectionMapper<S, T> {

    T map(S source);

    default List<T> map(Collection<S> sources) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
