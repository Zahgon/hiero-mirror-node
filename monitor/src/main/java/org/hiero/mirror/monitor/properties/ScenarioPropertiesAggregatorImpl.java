// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.monitor.properties;

import com.google.common.collect.Lists;
import jakarta.inject.Named;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.CustomLog;

@CustomLog
@Named
public class ScenarioPropertiesAggregatorImpl implements ScenarioPropertiesAggregator {

    private static final Pattern LIST_PATTERN_END = Pattern.compile("(\\w+)\\.\\d+");

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> aggregateProperties(Map<String, String> properties) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
