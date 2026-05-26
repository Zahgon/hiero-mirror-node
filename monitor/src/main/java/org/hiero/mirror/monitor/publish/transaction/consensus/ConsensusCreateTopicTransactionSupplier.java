// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.monitor.publish.transaction.consensus;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.PublicKey;
import com.hedera.hashgraph.sdk.TopicCreateTransaction;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hiero.mirror.monitor.publish.transaction.AdminKeyable;
import org.hiero.mirror.monitor.publish.transaction.TransactionSupplier;
import org.hiero.mirror.monitor.util.Utility;

@Data
public class ConsensusCreateTopicTransactionSupplier implements TransactionSupplier<TopicCreateTransaction>, AdminKeyable {

    private String adminKey;

    private String autoRenewAccountId;

    @Min(1)
    private long maxTransactionFee = 1_000_000_000;

    @Override
    public TopicCreateTransaction get() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
