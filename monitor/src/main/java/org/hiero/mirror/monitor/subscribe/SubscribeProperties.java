// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.monitor.subscribe;

import com.google.common.collect.Sets;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.time.DurationMin;
import org.hiero.mirror.monitor.subscribe.grpc.GrpcSubscriberProperties;
import org.hiero.mirror.monitor.subscribe.rest.RestSubscriberProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties("hiero.mirror.monitor.subscribe")
public class SubscribeProperties {

    @Min(1)
    @Max(1024)
    private int clients = 1;

    private boolean enabled = true;

    @NotNull
    @Valid
    private Map<String, GrpcSubscriberProperties> grpc = new LinkedHashMap<>();

    @NotNull
    @Valid
    private Map<String, RestSubscriberProperties> rest = new LinkedHashMap<>();

    @DurationMin(seconds = 1L)
    @NotNull
    private Duration statusFrequency = Duration.ofSeconds(10L);

    @PostConstruct
    void validate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
