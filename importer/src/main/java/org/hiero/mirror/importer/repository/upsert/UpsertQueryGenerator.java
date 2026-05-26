// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.repository.upsert;

public interface UpsertQueryGenerator {

    String TEMP_SUFFIX = "_temp";

    String getFinalTableName();

    default String getTemporaryTableName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    String getUpsertQuery();
}
