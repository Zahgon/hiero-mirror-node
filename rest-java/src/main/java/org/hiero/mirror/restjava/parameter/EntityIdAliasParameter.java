// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.restjava.parameter;

import com.google.common.io.BaseEncoding;
import java.util.regex.Pattern;
import org.hiero.mirror.common.CommonProperties;
import org.jspecify.annotations.Nullable;

@SuppressWarnings("java:S6218")
public record EntityIdAliasParameter(long shard, long realm, byte[] alias) implements EntityIdParameter {

    public static final String ALIAS_REGEX = "^((\\d{1,5})\\.)?((\\d{1,5})\\.)?([A-Z2-7]{40,70})$";

    public static final Pattern ALIAS_PATTERN = Pattern.compile(ALIAS_REGEX);

    private static final BaseEncoding BASE32 = BaseEncoding.base32().omitPadding();

    @Nullable
    static EntityIdAliasParameter valueOfNullable(String id) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static EntityIdAliasParameter valueOf(String id) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
