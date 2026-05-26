// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.migration;

import com.google.common.base.Stopwatch;
import java.io.IOException;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.configuration.Configuration;
import org.flywaydb.core.api.migration.Context;
import org.flywaydb.core.api.migration.JavaMigration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class AbstractJavaMigration implements JavaMigration {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected MigrationVersion getMinimumVersion() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void migrate(Context context) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected abstract void doMigrate() throws IOException;

    /**
     * Determine whether a java migration should be skipped based on version and isIgnoreMissingMigrations setting
     *
     * @param configuration flyway Configuration
     * @return whether it should be skipped or not
     */
    protected boolean skipMigration(Configuration configuration) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean hasMinimumRequiredVersion(Configuration configuration) {
        MigrationVersion minimumRequiredVersion = getMinimumVersion();
        if (minimumRequiredVersion == null) {
            return true;
        }
        MigrationVersion targetVersion = configuration.getTarget();
        if (targetVersion == null) {
            return true;
        }
        return minimumRequiredVersion.compareTo(targetVersion) <= 0;
    }

    @Override
    public boolean canExecuteInTransaction() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Integer getChecksum() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
