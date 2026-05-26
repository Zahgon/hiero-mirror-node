// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.reader.balance;

import com.google.common.base.Stopwatch;
import jakarta.inject.Named;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.common.domain.balance.AccountBalanceFile;
import org.hiero.mirror.importer.domain.StreamFileData;
import org.springframework.context.annotation.Primary;

@CustomLog
@Named
@Primary
@RequiredArgsConstructor
public class CompositeBalanceFileReader implements BalanceFileReader {

    private final BalanceFileReaderImplV1 balanceFileReaderImplV1;

    private final BalanceFileReaderImplV2 balanceFileReaderImplV2;

    private final ProtoBalanceFileReader protoBalanceFileReader;

    @Override
    public boolean supports(StreamFileData streamFileData) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public AccountBalanceFile read(StreamFileData streamFileData) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private BalanceFileReader getReader(StreamFileData streamFileData) {
        if (protoBalanceFileReader.supports(streamFileData)) {
            return protoBalanceFileReader;
        }
        return balanceFileReaderImplV2.supports(streamFileData) ? balanceFileReaderImplV2 : balanceFileReaderImplV1;
    }
}
