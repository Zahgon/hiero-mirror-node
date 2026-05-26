// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.apache.commons.codec.binary.Hex;

@SuppressWarnings("java:S6548")
public class ByteArrayArrayToHexSerializer extends JsonSerializer<byte[][]> {

    public static final ByteArrayArrayToHexSerializer INSTANCE = new ByteArrayArrayToHexSerializer();

    private static final String DELIMITER = ",";

    private static final String END = "}";

    private static final String NULL = "null";

    private static final String PREFIX = "\\\\x";

    private static final String QUOTE = "\"";

    private static final String START = "{";

    private ByteArrayArrayToHexSerializer() {
    }

    @Override
    public void serialize(byte[][] value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
