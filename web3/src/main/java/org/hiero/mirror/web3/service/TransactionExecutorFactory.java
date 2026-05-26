// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.service;

import com.hedera.hapi.node.base.SemanticVersion;
import com.hedera.node.app.service.entityid.EntityIdFactory;
import com.hedera.node.app.workflows.standalone.TransactionExecutor;
import com.hedera.node.app.workflows.standalone.TransactionExecutors;
import com.hedera.node.app.workflows.standalone.TransactionExecutors.Properties;
import jakarta.inject.Named;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.web3.common.ContractCallContext;
import org.hiero.mirror.web3.evm.properties.EvmProperties;
import org.hiero.mirror.web3.state.MirrorNodeState;
import org.hyperledger.besu.evm.operation.BlockHashOperation;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

@Named
@RequiredArgsConstructor
public class TransactionExecutorFactory {

    private final BlockHashOperation mirrorBlockHashOperation;

    private final MirrorNodeState mirrorNodeState;

    private final EvmProperties evmProperties;

    private final Map<SemanticVersion, TransactionExecutor> transactionExecutors = new ConcurrentHashMap<>();

    private final EntityIdFactory entityIdFactory;

    @Async
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // Reuse TransactionExecutor across requests for the same EVM version
    public TransactionExecutor get() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private synchronized TransactionExecutor create(SemanticVersion evmVersion) {
        var appProperties = new HashMap<>(evmProperties.getTransactionProperties());
        appProperties.put("contracts.evm.version", "v" + evmVersion.major() + "." + evmVersion.minor());
        var executorConfig = Properties.newBuilder().appProperties(appProperties).customOps(Set.of(mirrorBlockHashOperation)).state(mirrorNodeState).build();
        return TransactionExecutors.TRANSACTION_EXECUTORS.newExecutor(executorConfig, entityIdFactory);
    }
}
