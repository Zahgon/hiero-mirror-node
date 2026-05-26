// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.repository.upsert;

import java.io.StringWriter;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

@CustomLog
@RequiredArgsConstructor
public class GenericUpsertQueryGenerator implements UpsertQueryGenerator {

    private static final String UPSERT_TEMPLATE = "/db/template/upsert.vm";

    private static final String UPSERT_HISTORY_TEMPLATE = "/db/template/upsert_history.vm";

    private final EntityMetadata metadata;

    @Override
    public String getFinalTableName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Constructs an upsert query using a velocity template with replacement variables for table and column names
     * constructed from the `EntityMetadata` metadata.
     *
     * @return the upsert query
     */
    @Override
    public String getUpsertQuery() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private String closeRange(String input) {
        return input.replace("e_timestamp_range", "int8range(lower(e_timestamp_range), lower(timestamp_range)) as timestamp_range");
    }
}
