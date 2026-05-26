// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.common;

import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.web3.exception.InvalidParametersException;

public record TransactionIdParameter(EntityId payerAccountId, Instant validStart) implements TransactionIdOrHashParameter {

    private static final Pattern TRANSACTION_ID_PATTERN = Pattern.compile("^(\\d+)\\.(\\d+)\\.(\\d+)-(\\d{1,19})-(\\d{1,9})$");

    public static TransactionIdParameter valueOf(String transactionId) throws InvalidParametersException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
