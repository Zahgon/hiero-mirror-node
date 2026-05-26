// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.monitor.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.Instant;
import lombok.CustomLog;
import org.apache.commons.lang3.StringUtils;

@CustomLog
public class StringToInstantDeserializer extends JsonDeserializer<Instant> {

    @Override
    public Instant deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
