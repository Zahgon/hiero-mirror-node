// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.service.model;

import static org.hiero.mirror.web3.validation.HexValidator.HEX_PREFIX;
import com.hedera.hapi.node.base.ResponseCodeEnum;
import com.hedera.hapi.node.contract.ContractFunctionResult;
import java.util.Optional;

public record EvmTransactionResult(ResponseCodeEnum responseCodeEnum, ContractFunctionResult functionResult) {

    public Optional<String> getErrorMessage() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String contractCallResult() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isSuccessful() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public long gasUsed() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
