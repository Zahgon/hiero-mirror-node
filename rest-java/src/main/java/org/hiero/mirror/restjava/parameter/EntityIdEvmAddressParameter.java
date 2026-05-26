// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.restjava.parameter;

import java.util.regex.Pattern;
import lombok.SneakyThrows;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.hiero.mirror.common.CommonProperties;
import org.jspecify.annotations.Nullable;

@SuppressWarnings("java:S6218")
public record EntityIdEvmAddressParameter(long shard, long realm, byte[] evmAddress) implements EntityIdParameter {

    public static final String EVM_ADDRESS_REGEX = "^(((\\d{1,5})\\.)?((\\d{1,5})\\.)?|0x)?([A-Fa-f0-9]{40})$";

    public static final Pattern EVM_ADDRESS_PATTERN = Pattern.compile(EVM_ADDRESS_REGEX);

    @SneakyThrows(DecoderException.class)
    @Nullable
    static EntityIdEvmAddressParameter valueOfNullable(String id) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static EntityIdEvmAddressParameter valueOf(String id) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
