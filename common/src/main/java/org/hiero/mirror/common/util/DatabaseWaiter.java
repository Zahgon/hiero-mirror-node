// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.common.util;

import com.google.common.util.concurrent.Uninterruptibles;
import java.sql.DriverManager;
import java.util.Properties;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.common.CommonProperties;
import org.springframework.util.StringUtils;

@CustomLog
@RequiredArgsConstructor
public class DatabaseWaiter {

    private final CommonProperties commonProperties;

    public void waitForDatabase(String jdbcUrl, String username, String password) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
