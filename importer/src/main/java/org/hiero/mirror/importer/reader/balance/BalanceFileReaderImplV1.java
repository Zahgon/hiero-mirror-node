// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.reader.balance;

import jakarta.inject.Named;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import org.apache.commons.lang3.Strings;
import org.hiero.mirror.importer.exception.InvalidDatasetException;
import org.hiero.mirror.importer.parser.balance.BalanceParserProperties;
import org.hiero.mirror.importer.reader.balance.line.AccountBalanceLineParserV1;

@Named
public class BalanceFileReaderImplV1 extends CsvBalanceFileReader {

    static final String TIMESTAMP_HEADER_PREFIX = "timestamp:";

    private static final int MAX_HEADER_ROWS = 10;

    public BalanceFileReaderImplV1(BalanceParserProperties balanceParserProperties, AccountBalanceLineParserV1 parser) {
        super(balanceParserProperties, parser);
    }

    @Override
    protected String getTimestampHeaderPrefix() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected String getVersionHeaderPrefix() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The file should contain 2 header rows:
     * <p>
     * - single header row Timestamp:YYYY-MM-DDTHH:MM:SS.NNNNNNNNZ
     * <p>
     * - shardNum,realmNum,accountNum,balance
     * <p>
     * followed by rows of data. The logic here is a slight bit more lenient. Look at up to MAX_HEADER_ROWS rows ending
     * at any row containing "shard" and requiring that one of the rows had "Timestamp: some value"
     *
     * @param reader
     * @return consensusTimestamp
     */
    @Override
    protected long parseConsensusTimestamp(BufferedReader reader) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
