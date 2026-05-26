// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.common;

import org.hiero.mirror.web3.exception.InvalidParametersException;
import org.springframework.util.StringUtils;

public sealed interface TransactionIdOrHashParameter permits TransactionHashParameter, TransactionIdParameter {

    static TransactionIdOrHashParameter valueOf(String transactionIdOrHash) throws InvalidParametersException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
