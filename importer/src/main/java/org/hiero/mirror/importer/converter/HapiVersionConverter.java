// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.converter;

import io.micrometer.common.util.StringUtils;
import jakarta.inject.Named;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.util.Version;

@Named
@ConfigurationPropertiesBinding
public class HapiVersionConverter implements Converter<String, Version> {

    @Override
    public Version convert(String source) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
