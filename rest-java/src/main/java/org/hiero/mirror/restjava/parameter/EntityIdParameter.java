// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.restjava.parameter;

import org.apache.commons.lang3.StringUtils;

public sealed interface EntityIdParameter permits EntityIdNumParameter, EntityIdEvmAddressParameter, EntityIdAliasParameter {

    static EntityIdParameter valueOf(String id) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    long shard();

    long realm();
}
