// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.monitor.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.Duration;

public class DurationToStringSerializer extends StdSerializer<Duration> {

    private static final long serialVersionUID = 5848583700556532429L;

    protected DurationToStringSerializer() {
        super(Duration.class);
    }

    @Override
    public void serialize(Duration duration, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String convert(Duration duration) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
