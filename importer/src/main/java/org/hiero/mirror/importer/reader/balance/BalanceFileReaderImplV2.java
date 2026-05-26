// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.reader.balance;

import jakarta.inject.Named;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import org.apache.commons.lang3.Strings;
import org.hiero.mirror.importer.exception.InvalidDatasetException;
import org.hiero.mirror.importer.parser.balance.BalanceParserProperties;
import org.hiero.mirror.importer.reader.balance.line.AccountBalanceLineParserV2;

@Named
public class BalanceFileReaderImplV2 extends CsvBalanceFileReader {

    static final String VERSION_HEADER = "# version:2";

    private static final String TIMESTAMP_HEADER_PREFIX = "# TimeStamp:";

    public BalanceFileReaderImplV2(BalanceParserProperties balanceParserProperties, AccountBalanceLineParserV2 parser) {
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

    @Override
    protected long parseConsensusTimestamp(BufferedReader reader) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
