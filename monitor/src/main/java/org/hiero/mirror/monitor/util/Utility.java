// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.monitor.util;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;
import lombok.CustomLog;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

@CustomLog
@UtilityClass
public class Utility {

    private static final long MILLIS_OFFSET = Duration.ofMinutes(5L).toMillis();

    /**
     * Parses bytes as a String expected to be in format ^\d+ .*$. The first part is the published timestamp in
     * milliseconds from epoch followed by a mandatory space. Optionally, additional arbitrary characters can be
     * appended that are ignored by this method.
     *
     * @param bytes containing a timestamp encoded as a String
     * @return the parsed Instant
     */
    public static Instant getTimestamp(byte[] bytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] generateMessage(int requestedMessageSize) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String getMemo(String message) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
