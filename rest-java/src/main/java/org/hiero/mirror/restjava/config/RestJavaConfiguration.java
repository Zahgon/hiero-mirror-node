// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.restjava.config;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.Message;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.restjava.jooq.DomainRecordMapperProvider;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.boot.jooq.autoconfigure.DefaultConfigurationCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@Configuration
@RequiredArgsConstructor
class RestJavaConfiguration {

    private final FormattingConversionService mvcConversionService;

    @PostConstruct
    void initialize() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    DefaultConfigurationCustomizer configurationCustomizer(DomainRecordMapperProvider domainRecordMapperProvider) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    FilterRegistrationBean<ShallowEtagHeaderFilter> etagFilter() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
