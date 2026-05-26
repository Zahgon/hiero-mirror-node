// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.monitor.publish.transaction.token;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.TokenId;
import com.hedera.hashgraph.sdk.TokenUnfreezeTransaction;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hiero.mirror.monitor.publish.transaction.TransactionSupplier;

@Data
public class TokenUnfreezeTransactionSupplier implements TransactionSupplier<TokenUnfreezeTransaction> {

    @NotBlank
    private String accountId;

    @Min(1)
    private long maxTransactionFee = 1_000_000_000;

    @NotBlank
    private String tokenId;

    @Override
    public TokenUnfreezeTransaction get() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
