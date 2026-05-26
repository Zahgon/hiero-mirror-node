// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.reader.balance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.Strings;
import org.hiero.mirror.common.domain.balance.AccountBalance;
import org.hiero.mirror.common.domain.balance.AccountBalanceFile;
import org.hiero.mirror.common.util.DomainUtils;
import org.hiero.mirror.importer.domain.StreamFileData;
import org.hiero.mirror.importer.exception.InvalidDatasetException;
import org.hiero.mirror.importer.parser.balance.BalanceParserProperties;
import org.hiero.mirror.importer.reader.balance.line.AccountBalanceLineParser;

@CustomLog
@RequiredArgsConstructor
public abstract class CsvBalanceFileReader implements BalanceFileReader {

    static final int BUFFER_SIZE = 16;

    static final Charset CHARSET = StandardCharsets.UTF_8;

    static final String COLUMN_HEADER_PREFIX = "shard";

    private static final String FILE_EXTENSION = "csv";

    private final BalanceParserProperties balanceParserProperties;

    private final AccountBalanceLineParser parser;

    @Override
    public boolean supports(StreamFileData streamFileData) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected boolean supports(String firstLine) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected abstract String getTimestampHeaderPrefix();

    protected abstract String getVersionHeaderPrefix();

    @Override
    public AccountBalanceFile read(StreamFileData streamFileData) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected abstract long parseConsensusTimestamp(BufferedReader reader);

    protected long convertTimestamp(String timestamp) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
