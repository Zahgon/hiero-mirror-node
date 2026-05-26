// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.repository.upsert;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.Value;
import org.hiero.mirror.common.domain.Upsertable;

/**
 * Contains the metadata associated with an @Upsertable entity. Used to generate dynamic upsert SQL.
 */
@Value
class EntityMetadata {

    private final String tableName;

    private final Upsertable upsertable;

    private final Set<ColumnMetadata> columns;

    public String column(Predicate<ColumnMetadata> filter, String pattern) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String columns(String pattern) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String columns(Predicate<ColumnMetadata> filter, String pattern) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String columns(Predicate<ColumnMetadata> filter, String pattern, String separator) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String columns(String defaultPattern, Predicate<ColumnMetadata> predicate, String predicatePattern) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
