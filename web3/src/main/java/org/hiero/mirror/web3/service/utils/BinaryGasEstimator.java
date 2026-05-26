// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.service.utils;

import jakarta.inject.Named;
import java.util.function.LongFunction;
import java.util.function.ObjIntConsumer;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.web3.common.ContractCallContext;
import org.hiero.mirror.web3.evm.properties.EvmProperties;
import org.hiero.mirror.web3.service.model.EvmTransactionResult;

@CustomLog
@RequiredArgsConstructor
@Named
public class BinaryGasEstimator {

    private final EvmProperties properties;

    public long search(final ObjIntConsumer<Long> metricUpdater, final LongFunction<EvmTransactionResult> call, long lo, long hi) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // This method is needed because within the modularized services if the contract call fails an exception is thrown
    // instead of transaction result with 'failed' status which will result in a failing test. This way we handle the
    // exception and return estimated gas
    private EvmTransactionResult safeCall(long mid, LongFunction<EvmTransactionResult> call) {
        try {
            return call.apply(mid);
        } catch (Exception ignored) {
            log.info("Exception while calling contract for gas estimation");
            return null;
        }
    }
}
