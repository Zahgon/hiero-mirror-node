// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.monitor.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class StringToDurationDeserializer extends StdDeserializer<Duration> {

    private static final Pattern PATTERN = Pattern.compile("(\\d+d)?(\\d+h)?(\\d+m)?(\\d+s)?");

    private static final long serialVersionUID = 3690958538780466689L;

    protected StringToDurationDeserializer() {
        super(Duration.class);
    }

    @Override
    public Duration deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
