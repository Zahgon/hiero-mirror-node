// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.converter;

import jakarta.inject.Named;
import java.util.Base64;
import org.jspecify.annotations.NullMarked;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;

@ConfigurationPropertiesBinding
@Named
@NullMarked
final class Base64ToByteArrayConverter implements Converter<String, byte[]> {

    @Override
    public byte[] convert(final String source) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
