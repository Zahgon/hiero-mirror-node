// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.evm.contracts.execution.traceability;

import static org.hiero.mirror.web3.utils.Constants.BALANCE_OPERATION_NAME;
import com.hedera.hapi.streams.CallOperationType;
import com.hedera.hapi.streams.ContractAction;
import com.hedera.hapi.streams.ContractActionType;
import com.hedera.node.app.service.contract.impl.exec.ActionSidecarContentTracer;
import com.hedera.node.app.service.contract.impl.exec.systemcontracts.HederaSystemContract;
import jakarta.inject.Named;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.CustomLog;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.tuweni.bytes.Bytes;
import org.hiero.mirror.rest.model.Opcode;
import org.hiero.mirror.web3.common.ContractCallContext;
import org.hyperledger.besu.datatypes.Address;
import org.hyperledger.besu.evm.frame.ExceptionalHaltReason;
import org.hyperledger.besu.evm.frame.MessageFrame;
import org.hyperledger.besu.evm.operation.Operation;
import org.hyperledger.besu.evm.operation.Operation.OperationResult;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Named
@CustomLog
public class OpcodeActionTracer extends AbstractOpcodeTracer implements ActionSidecarContentTracer {

    @Getter
    private final Map<Address, HederaSystemContract> systemContracts = new ConcurrentHashMap<>();

    @Override
    public void tracePreExecution(@NonNull final MessageFrame frame) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void tracePostExecution(@NonNull final MessageFrame frame, @NonNull final OperationResult operationResult) {
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
    public void tracePrecompileCall(@NonNull final MessageFrame frame, final long gasRequirement, @Nullable final Bytes output) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void traceContextEnter(@NonNull final MessageFrame frame) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void traceContextReEnter(@NonNull final MessageFrame frame) {
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

    private Opcode createOpcode(final MessageFrame frame, final long gasCost, final String revertReason, final List<String> stack, final List<String> memory, final Map<String, String> storage) {
        return new Opcode().pc(frame.getPC()).op(frame.getCurrentOperation() != null ? frame.getCurrentOperation().getName() : StringUtils.EMPTY).gas(frame.getRemainingGas()).gasCost(gasCost).depth(frame.getDepth()).stack(stack).memory(memory).storage(storage).reason(revertReason);
    }

    public void setSystemContracts(final Map<Address, HederaSystemContract> systemContracts) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean isCallToSystemContracts(final MessageFrame frame, final Map<Address, HederaSystemContract> systemContracts) {
        final var recipientAddress = frame.getRecipientAddress();
        return systemContracts.containsKey(recipientAddress);
    }

    @Override
    public List<ContractAction> contractActions() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
