// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.migration;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Stopwatch;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.lang3.BooleanUtils;
import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.callback.Context;
import org.flywaydb.core.api.callback.Event;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.transaction.support.TransactionOperations;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@NullMarked
abstract class AsyncJavaMigration<T> extends RepeatableMigration implements Callback {

    private static final String ASYNC_JAVA_MIGRATION_HISTORY_FIXED = """
        select exists(select * from flyway_schema_history where version in ('1.109.0', '2.14.0'))
        """;

    private static final String CHECK_FLYWAY_SCHEMA_HISTORY_EXISTENCE_SQL = """
        select exists(select * from information_schema.tables
        where table_schema = :schema and table_name = 'flyway_schema_history')
        """;

    private static final String SELECT_LAST_CHECKSUM_SQL = """
        select checksum from flyway_schema_history
        where description = :description
        order by installed_rank desc limit 1
        """;

    private static final String SELECT_LAST_CHECKSUM_SQL_PRE_FIX = """
        select checksum from flyway_schema_history
        where description = :description and script like 'com.hedera.%'
        order by installed_rank desc limit 1
        """;

    private static final String UPDATE_CHECKSUM_SQL = """
        with last as (
          select installed_rank from flyway_schema_history
          where description = :description order by installed_rank desc limit 1
        )
        update flyway_schema_history f
        set checksum = :checksum,
        execution_time = least(2147483647, extract(epoch from now() - f.installed_on) * 1000)
        from last
        where f.installed_rank = last.installed_rank
        """;

    private final ObjectProvider<NamedParameterJdbcOperations> namedParameterJdbcOperationsProvider;

    private final String schema;

    private final AtomicBoolean complete = new AtomicBoolean(false);

    private final AtomicBoolean shouldMigrate = new AtomicBoolean(false);

    protected AsyncJavaMigration(Map<String, MigrationProperties> migrationPropertiesMap, ObjectProvider<JdbcOperations> jdbcOperationsProvider, String schema) {
        super(migrationPropertiesMap);
        this.namedParameterJdbcOperationsProvider = new ObjectProvider<>() {

            @Override
            public NamedParameterJdbcOperations getObject() {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        };
        this.schema = schema;
    }

    protected final JdbcOperations getJdbcOperations() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected final NamedParameterJdbcOperations getNamedParameterJdbcOperations() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean canHandleInTransaction(Event event, Context context) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getCallbackName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Integer getChecksum() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void handle(Event event, Context context) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Nullable
    protected final <O> O queryForObjectOrNull(String sql, SqlParameterSource paramSource, Class<O> requiredType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Nullable
    protected final <O> O queryForObjectOrNull(String sql, SqlParameterSource paramSource, RowMapper<O> rowMapper) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean supports(Event event, Context context) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean isComplete() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void doMigrate() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected abstract T getInitial();

    /**
     * Gets the success checksum to set for the migration in flyway schema history table. Note the checksum is required
     * to be positive.
     *
     * @return The success checksum for the migration
     */
    protected final int getSuccessChecksum() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected abstract TransactionOperations getTransactionOperations();

    protected void migrateAsync() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected abstract Optional<T> migratePartial(T last);

    /**
     * Perform any synchronous portion of the migration
     *
     * @return boolean indicating if async migration should be performed
     */
    protected boolean performSynchronousSteps() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected final void runMigrateAsync() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private MapSqlParameterSource getSqlParamSource() {
        return new MapSqlParameterSource().addValue("description", getDescription());
    }

    private boolean hasFlywaySchemaHistoryTable() {
        var exists = getNamedParameterJdbcOperations().queryForObject(CHECK_FLYWAY_SCHEMA_HISTORY_EXISTENCE_SQL, Map.of("schema", schema), Boolean.class);
        return BooleanUtils.isTrue(exists);
    }

    private boolean isAsyncJavaMigrationHistoryFixed() {
        var fixed = getJdbcOperations().queryForObject(ASYNC_JAVA_MIGRATION_HISTORY_FIXED, Boolean.class);
        return BooleanUtils.isTrue(fixed);
    }

    private void onSuccess() {
        var paramSource = getSqlParamSource().addValue("checksum", getSuccessChecksum());
        getNamedParameterJdbcOperations().update(UPDATE_CHECKSUM_SQL, paramSource);
    }

    @VisibleForTesting
    void setComplete(boolean complete) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
