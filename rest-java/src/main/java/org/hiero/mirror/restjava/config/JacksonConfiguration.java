// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.restjava.config;

import com.fasterxml.jackson.core.StreamReadConstraints;
import com.fasterxml.jackson.core.StreamWriteConstraints;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import org.springframework.boot.jackson2.autoconfigure.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class JacksonConfiguration {

    // Configure JSON parsing limits to reject malicious input
    @Bean
    @SuppressWarnings("removal")
    Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
