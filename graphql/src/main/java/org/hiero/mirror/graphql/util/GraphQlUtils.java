// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.graphql.util;

import com.google.common.base.Splitter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.function.Function;
import lombok.experimental.UtilityClass;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Strings;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.graphql.viewmodel.EntityIdInput;
import org.hiero.mirror.graphql.viewmodel.HbarUnit;
import org.hiero.mirror.graphql.viewmodel.Node;

@UtilityClass
public class GraphQlUtils {

    private static final Base32 BASE32 = new Base32();

    private static final String HEX_PREFIX = "0x";

    private static final Splitter SPLITTER = Splitter.on(':');

    public static Long convertCurrency(HbarUnit unit, Long tinybars) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <T> T getId(Node node, Function<List<String>, T> converter) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static EntityId toEntityId(EntityIdInput entityId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void validateOneOf(Object... values) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] decodeBase32(String base32) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] decodeEvmAddress(String evmAddress) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
