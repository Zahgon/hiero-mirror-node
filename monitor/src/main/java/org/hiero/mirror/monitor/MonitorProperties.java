// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.monitor;

import static org.hiero.mirror.monitor.OperatorProperties.DEFAULT_OPERATOR_ACCOUNT_ID;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Data;
import org.hiero.mirror.common.CommonProperties;
import org.jspecify.annotations.Nullable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@EnableConfigurationProperties(CommonProperties.class)
@Validated
@ConfigurationProperties("hiero.mirror.monitor")
public class MonitorProperties {

    @Resource
    private CommonProperties commonProperties;

    @Nullable
    @Valid
    private MirrorNodeProperties mirrorNode;

    @NotNull
    private HederaNetwork network = HederaNetwork.TESTNET;

    @NotNull
    @Valid
    private Set<NodeProperties> nodes = new LinkedHashSet<>();

    @NotNull
    @Valid
    private OperatorProperties operator = new OperatorProperties();

    @NotNull
    @Valid
    private NodeValidationProperties nodeValidation = new NodeValidationProperties();

    public MirrorNodeProperties getMirrorNode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Checks if the operator account id has matching shard and realm. In case of mismatch, if the operator account id
     * is the default, updates its shard and realm, otherwise throws exception.
     */
    @PostConstruct
    void init() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
