// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.grpc.config;

import io.github.mweirauch.micrometer.jvm.extras.ProcessMemoryMetrics;
import io.github.mweirauch.micrometer.jvm.extras.ProcessThreadMetrics;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class MetricsConfiguration {

    @Bean
    MeterBinder processMemoryMetrics() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    MeterBinder processThreadMetrics() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
