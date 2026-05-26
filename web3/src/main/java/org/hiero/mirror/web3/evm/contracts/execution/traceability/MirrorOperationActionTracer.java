// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.evm.contracts.execution.traceability;

import static org.hiero.mirror.common.util.DomainUtils.toEvmAddress;
import static org.hiero.mirror.web3.utils.Constants.BALANCE_OPERATION_NAME;
import com.hedera.hapi.streams.CallOperationType;
import com.hedera.hapi.streams.ContractAction;
import com.hedera.hapi.streams.ContractActionType;
import com.hedera.node.app.service.contract.impl.exec.ActionSidecarContentTracer;
import jakarta.inject.Named;
import java.util.List;
import java.util.Optional;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hiero.base.utility.CommonUtils;
import org.hiero.mirror.common.domain.entity.Entity;
import org.hiero.mirror.web3.common.ContractCallContext;
import org.hiero.mirror.web3.evm.properties.TraceProperties;
import org.hiero.mirror.web3.state.CommonEntityAccessor;
import org.hyperledger.besu.evm.frame.ExceptionalHaltReason;
import org.hyperledger.besu.evm.frame.MessageFrame;
import org.hyperledger.besu.evm.operation.Operation;
import org.jspecify.annotations.NonNull;

@Named
@CustomLog
@RequiredArgsConstructor
public class MirrorOperationActionTracer implements ActionSidecarContentTracer {

    private final TraceProperties traceProperties;

    private final CommonEntityAccessor commonEntityAccessor;

    @Override
    public void tracePreExecution(@NonNull final MessageFrame frame) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void tracePostExecution(@NonNull final MessageFrame frame, final Operation.@NonNull OperationResult operationResult) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void tracePerOpcode(MessageFrame frame, long gas, ExceptionalHaltReason halt, Operation op) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void traceSuspended(MessageFrame parent, MessageFrame child, CallOperationType opCall) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void traceNotExecuting(MessageFrame child) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void traceOriginAction(@NonNull MessageFrame frame) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void sanitizeTracedActions(@NonNull MessageFrame frame) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void tracePrecompileResult(@NonNull MessageFrame frame, @NonNull ContractActionType type) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public List<ContractAction> contractActions() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
