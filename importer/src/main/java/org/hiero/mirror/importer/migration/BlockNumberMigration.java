// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.migration;

import static org.hiero.mirror.importer.ImporterProperties.HederaNetwork.MAINNET;
import static org.hiero.mirror.importer.ImporterProperties.HederaNetwork.TESTNET;
import com.google.common.base.Stopwatch;
import jakarta.inject.Named;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.tuple.Pair;
import org.flywaydb.core.api.MigrationVersion;
import org.hiero.mirror.importer.ImporterProperties;
import org.hiero.mirror.importer.repository.RecordFileRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

@Named
public class BlockNumberMigration extends RepeatableMigration {

    static final Map<String, Pair<Long, Long>> BLOCK_NUMBER_MAPPING = Map.of(TESTNET, Pair.of(1656461617493248000L, 22384256L), MAINNET, Pair.of(1656461547557609267L, 34305852L));

    private final ImporterProperties importerProperties;

    private final ObjectProvider<NamedParameterJdbcOperations> jdbcOperationsProvider;

    private final ObjectProvider<RecordFileRepository> recordFileRepositoryProvider;

    public BlockNumberMigration(ImporterProperties importerProperties, ObjectProvider<NamedParameterJdbcOperations> jdbcOperationsProvider, ObjectProvider<RecordFileRepository> recordFileRepositoryProvider) {
        super(importerProperties.getMigration());
        this.importerProperties = importerProperties;
        this.jdbcOperationsProvider = jdbcOperationsProvider;
        this.recordFileRepositoryProvider = recordFileRepositoryProvider;
    }

    @Override
    public String getDescription() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected MigrationVersion getMinimumVersion() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void doMigrate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void updateIndex(long correctBlockNumber, long incorrectBlockNumber) {
        long offset = correctBlockNumber - incorrectBlockNumber;
        Stopwatch stopwatch = Stopwatch.createStarted();
        int count = recordFileRepositoryProvider.getObject().updateIndex(offset);
        log.info("Updated {} blocks with offset {} in {}", count, offset, stopwatch);
    }

    private Optional<Long> findBlockNumberByConsensusEnd(long consensusEnd) {
        var params = new MapSqlParameterSource().addValue("consensusEnd", consensusEnd);
        try {
            return Optional.ofNullable(jdbcOperationsProvider.getObject().queryForObject("select index from record_file where consensus_end = :consensusEnd limit 1", params, Long.class));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }
}
