// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.service;

import static java.time.ZoneOffset.UTC;
import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.hiero.mirror.web3.convert.BytesDecoder.maybeDecodeSolidityErrorStringToReadableMessage;
import static org.hiero.mirror.web3.validation.HexValidator.HEX_PREFIX;
import com.google.common.annotations.VisibleForTesting;
import com.hedera.hapi.node.base.ResponseCodeEnum;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Meter.MeterProvider;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import jakarta.inject.Named;
import java.time.Instant;
import java.time.YearMonth;
import lombok.CustomLog;
import org.hiero.mirror.web3.common.ContractCallContext;
import org.hiero.mirror.web3.evm.properties.EvmProperties;
import org.hiero.mirror.web3.exception.BlockNumberNotFoundException;
import org.hiero.mirror.web3.exception.MirrorEvmTransactionException;
import org.hiero.mirror.web3.service.model.CallServiceParameters;
import org.hiero.mirror.web3.service.model.EvmTransactionResult;
import org.hiero.mirror.web3.state.Utils;
import org.hiero.mirror.web3.throttle.ThrottleManager;
import org.hiero.mirror.web3.throttle.ThrottleProperties;
import org.hiero.mirror.web3.utils.Suppliers;
import org.hiero.mirror.web3.viewmodel.BlockType;

@Named
@CustomLog
public abstract class ContractCallService {

    static final String EVM_INVOCATION_METRIC = "hiero.mirror.web3.evm.invocation";

    static final String GAS_LIMIT_METRIC = "hiero.mirror.web3.evm.gas.limit";

    static final String GAS_USED_METRIC = "hiero.mirror.web3.evm.gas.used";

    static final String TAG_BLOCK = "block";

    static final String TAG_ITERATION = "iteration";

    static final String TAG_STATUS = "status";

    static final String TAG_TYPE = "type";

    protected final EvmProperties evmProperties;

    private final MeterProvider<Counter> invocationCounter;

    private final MeterProvider<Counter> gasLimitCounter;

    private final MeterProvider<Counter> gasUsedCounter;

    private final RecordFileService recordFileService;

    private final ThrottleProperties throttleProperties;

    private final ThrottleManager throttleManager;

    private final TransactionExecutionService transactionExecutionService;

    @SuppressWarnings("java:S107")
    protected ContractCallService(ThrottleManager throttleManager, ThrottleProperties throttleProperties, MeterRegistry meterRegistry, RecordFileService recordFileService, EvmProperties evmProperties, TransactionExecutionService transactionExecutionService) {
        this.invocationCounter = Counter.builder(EVM_INVOCATION_METRIC).description("The number of EVM invocations").withRegistry(meterRegistry);
        this.gasLimitCounter = Counter.builder(GAS_LIMIT_METRIC).description("The amount of gas limit sent in the request").withRegistry(meterRegistry);
        this.gasUsedCounter = Counter.builder(GAS_USED_METRIC).description("The amount of gas consumed by the EVM").withRegistry(meterRegistry);
        this.recordFileService = recordFileService;
        this.throttleProperties = throttleProperties;
        this.throttleManager = throttleManager;
        this.evmProperties = evmProperties;
        this.transactionExecutionService = transactionExecutionService;
    }

    @VisibleForTesting
    public EvmTransactionResult callContract(CallServiceParameters params) throws MirrorEvmTransactionException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * This method is responsible for calling a smart contract function. The method is divided into two main parts:
     * <p>
     * 1. If the call is historical, the method retrieves the corresponding record file and initializes the contract
     * call context with the historical state. The method then proceeds to call the contract.
     * </p>
     * <p>
     * 2. If the call is not historical, the method initializes the contract call context with the current state and
     * proceeds to call the contract.
     * </p>
     *
     * @param params the call service parameters
     * @param ctx    the contract call context
     * @return {@link EvmTransactionResult} of the contract call
     * @throws MirrorEvmTransactionException if any pre-checks fail with {@link IllegalStateException} or
     *                                       {@link IllegalArgumentException}
     */
    protected final EvmTransactionResult callContract(CallServiceParameters params, ContractCallContext ctx) throws MirrorEvmTransactionException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected final EvmTransactionResult doProcessCall(CallServiceParameters params, long estimatedGas, boolean estimate) throws MirrorEvmTransactionException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void restoreGasToBucket(EvmTransactionResult result, long gasLimit) {
        // If the transaction fails, gasUsed is equal to gasLimit, so restore the configured refund percent
        // of the gasLimit value back in the bucket.
        final var gasLimitToRestoreBaseline = (long) (gasLimit * throttleProperties.getGasLimitRefundPercent() / 100f);
        if (result == null || (!result.isSuccessful() && gasLimit == result.gasUsed())) {
            throttleManager.restore(gasLimitToRestoreBaseline);
        } else {
            // The transaction was successful or reverted, so restore the remaining gas back in the bucket or
            // the configured refund percent of the gasLimit value back in the bucket - whichever is lower.
            final var gasRemaining = gasLimit - result.gasUsed();
            throttleManager.restore(Math.min(gasRemaining, gasLimitToRestoreBaseline));
        }
    }

    protected void validateResult(final EvmTransactionResult txnResult, final CallServiceParameters params) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected final void updateMetrics(CallServiceParameters parameters, long gasUsed, int iterations, String status) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected final void updateGasLimitMetric(final CallServiceParameters parameters) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private String getBlock() {
        return ContractCallContext.get().getTimestamp().filter(// Filter future timestamps to reduce cardinality
        t -> t <= Utils.getCurrentTimestamp()).map(t -> YearMonth.from(Instant.ofEpochSecond(0L, t).atZone(UTC)).toString()).orElse(BlockType.LATEST.toString());
    }
}
