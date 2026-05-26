// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.local.SynchronizationStrategy;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.web3.throttle.ThrottleProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class ThrottleConfiguration {

    public static final String GAS_LIMIT_BUCKET = "gasLimitBucket";

    public static final String RATE_LIMIT_BUCKET = "rateLimitBucket";

    public static final String OPCODE_RATE_LIMIT_BUCKET = "opcodeRateLimitBucket";

    private final ThrottleProperties throttleProperties;

    @Bean(name = RATE_LIMIT_BUCKET)
    Bucket rateLimitBucket() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean(name = GAS_LIMIT_BUCKET)
    Bucket gasLimitBucket() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean(name = OPCODE_RATE_LIMIT_BUCKET)
    Bucket opcodeRateLimitBucket() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
