// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.reader.balance;

import com.hedera.services.stream.proto.AllAccountBalances;
import com.hedera.services.stream.proto.SingleAccountBalances;
import jakarta.inject.Named;
import java.io.IOException;
import java.util.List;
import lombok.CustomLog;
import org.apache.commons.codec.digest.DigestUtils;
import org.hiero.mirror.common.domain.balance.AccountBalance;
import org.hiero.mirror.common.domain.balance.AccountBalanceFile;
import org.hiero.mirror.common.domain.balance.TokenBalance;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.util.DomainUtils;
import org.hiero.mirror.importer.domain.StreamFileData;
import org.hiero.mirror.importer.exception.InvalidStreamFileException;
import org.hiero.mirror.importer.exception.StreamFileReaderException;

@CustomLog
@Named
public class ProtoBalanceFileReader implements BalanceFileReader {

    private static final String FILE_EXTENSION = "pb";

    @Override
    public boolean supports(StreamFileData streamFileData) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public AccountBalanceFile read(StreamFileData streamFileData) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private AccountBalance toAccountBalance(long consensusTimestamp, SingleAccountBalances balances) {
        EntityId accountId = EntityId.of(balances.getAccountID());
        List<TokenBalance> tokenBalances = balances.getTokenUnitBalancesList().stream().map(tokenBalance -> {
            EntityId tokenId = EntityId.of(tokenBalance.getTokenId());
            TokenBalance.Id id = new TokenBalance.Id(consensusTimestamp, accountId, tokenId);
            return new TokenBalance(tokenBalance.getBalance(), id);
        }).toList();
        return new AccountBalance(balances.getHbarBalance(), tokenBalances, new AccountBalance.Id(consensusTimestamp, accountId));
    }
}
