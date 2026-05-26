// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.monitor.expression;

import java.util.LinkedHashMap;
import java.util.Map;

public interface ExpressionConverter {

    default Map<String, String> convert(Map<String, String> properties) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    String convert(String property);
}
