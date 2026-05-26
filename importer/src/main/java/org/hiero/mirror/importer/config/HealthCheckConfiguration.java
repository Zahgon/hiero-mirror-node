// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.importer.ImporterProperties;
import org.hiero.mirror.importer.parser.ParserProperties;
import org.springframework.boot.health.actuate.endpoint.HealthEndpoint;
import org.springframework.boot.health.contributor.CompositeHealthContributor;
import org.springframework.boot.health.contributor.Status;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
class HealthCheckConfiguration {

    private final ImporterProperties importerProperties;

    private final Collection<ParserProperties> parserProperties;

    @Bean
    Function<String, Status> healthResolver(HealthEndpoint healthEndpoint) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    CompositeHealthContributor streamFileActivity(MeterRegistry meterRegistry) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private MeterRegistry getRegistry(MeterRegistry meterRegistry) {
        if (meterRegistry instanceof CompositeMeterRegistry composite) {
            for (final var registry : composite.getRegistries()) {
                if (registry instanceof PrometheusMeterRegistry) {
                    return registry;
                }
            }
        }
        return meterRegistry;
    }
}
