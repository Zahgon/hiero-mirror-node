// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.migration;

import com.google.common.base.Stopwatch;
import com.hedera.services.stream.proto.ContractBytecode;
import jakarta.inject.Named;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.util.DomainUtils;
import org.hiero.mirror.importer.repository.EntityHistoryRepository;
import org.hiero.mirror.importer.repository.EntityRepository;
import org.springframework.jdbc.core.JdbcOperations;

@CustomLog
@Named
@RequiredArgsConstructor
public class SidecarContractMigration {

    private static final int BATCH_SIZE = 100;

    private static final int IN_CLAUSE_LIMIT = 32767;

    private static final String UPDATE_RUNTIME_BYTECODE_SQL = """
        insert into contract (id, runtime_bytecode)
        values (?, ?)
        on conflict (id)
        do update set runtime_bytecode = excluded.runtime_bytecode""";

    private final EntityHistoryRepository entityHistoryRepository;

    private final EntityRepository entityRepository;

    private final JdbcOperations jdbcOperations;

    public void migrate(List<ContractBytecode> contractBytecodes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void updateContractType(Collection<Long> contractIds) {
        if (!contractIds.isEmpty()) {
            entityRepository.updateContractType(contractIds);
            entityHistoryRepository.updateContractType(contractIds);
            contractIds.clear();
        }
    }
}
