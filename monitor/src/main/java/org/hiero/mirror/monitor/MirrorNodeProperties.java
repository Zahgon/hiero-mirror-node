// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.monitor;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties
@Data
@NoArgsConstructor
@Validated
public class MirrorNodeProperties {

    @NotNull
    private GrpcProperties grpc = new GrpcProperties();

    @NotNull
    private RestProperties rest = new RestProperties();

    private RestProperties restJava;

    @Data
    @Validated
    public static class GrpcProperties {

        @NotBlank
        private String host;

        @Min(0)
        @Max(65535)
        private int port = 443;

        public String getEndpoint() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    @Data
    @Validated
    public static class RestProperties {

        @NotBlank
        private String host;

        @Min(0)
        @Max(65535)
        private int port = 443;

        public String getBaseUrl() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
