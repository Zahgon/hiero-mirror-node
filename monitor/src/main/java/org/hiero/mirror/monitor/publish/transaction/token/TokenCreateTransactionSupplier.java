// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.monitor.publish.transaction.token;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.PublicKey;
import com.hedera.hashgraph.sdk.TokenCreateTransaction;
import com.hedera.hashgraph.sdk.TokenSupplyType;
import com.hedera.hashgraph.sdk.TokenType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Data;
import org.hiero.mirror.monitor.publish.transaction.AdminKeyable;
import org.hiero.mirror.monitor.publish.transaction.TransactionSupplier;
import org.hiero.mirror.monitor.util.Utility;

@Data
public class TokenCreateTransactionSupplier implements TransactionSupplier<TokenCreateTransaction>, AdminKeyable {

    private String adminKey;

    @Min(1)
    private int decimals = 10;

    private boolean freezeDefault = false;

    @Min(1)
    private int initialSupply = 1000000000;

    @Min(1)
    private long maxSupply = 100000000000L;

    @Min(1)
    private long maxTransactionFee = 1_000_000_000;

    @NotNull
    private TokenSupplyType supplyType = TokenSupplyType.INFINITE;

    @NotBlank
    private String symbol = ThreadLocalRandom.current().ints(5, 'A', 'Z').collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

    @NotBlank
    private String treasuryAccountId;

    @NotNull
    private TokenType type = TokenType.FUNGIBLE_COMMON;

    @Override
    public TokenCreateTransaction get() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
