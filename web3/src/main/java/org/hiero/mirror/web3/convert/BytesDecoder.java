// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.convert;

import static org.hiero.mirror.web3.validation.HexValidator.HEX_PREFIX;
import com.esaulpaugh.headlong.abi.ABIType;
import com.esaulpaugh.headlong.abi.Tuple;
import com.esaulpaugh.headlong.abi.TypeFactory;
import lombok.experimental.UtilityClass;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;

@UtilityClass
public class BytesDecoder {

    // Error(string)
    private static final String ERROR_FUNCTION_SELECTOR = "0x08c379a0";

    private static final byte[] ERROR_SELECTOR = { (byte) 0x08, (byte) 0xc3, (byte) 0x79, (byte) 0xa0 };

    private static final ABIType<Tuple> STRING_DECODER = TypeFactory.create("(string)");

    private static final byte[] EMPTY_BYTES = new byte[0];

    public static String maybeDecodeSolidityErrorStringToReadableMessage(final String revertReason) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String getAbiEncodedRevertReason(final String revertReason) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean startsWithErrorSelector(final byte[] bytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static boolean isAbiEncodedErrorString(final String revertReason) {
        return revertReason != null && revertReason.startsWith(ERROR_FUNCTION_SELECTOR);
    }

    public static byte[] hexToBytes(final String hexString) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
