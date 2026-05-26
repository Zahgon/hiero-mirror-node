// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.contractlog;

import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.apache.tuweni.bytes.Bytes;
import org.hiero.mirror.common.domain.contract.ContractLog;
import org.hiero.mirror.common.domain.contract.ContractResult;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.domain.transaction.RecordItem;
import org.hiero.mirror.importer.parser.record.entity.EntityListener;
import org.hiero.mirror.importer.parser.record.entity.EntityProperties;
import org.hiero.mirror.importer.parser.record.entity.ParserContext;
import org.springframework.data.util.Version;

@Named
@RequiredArgsConstructor
public class SyntheticContractLogServiceImpl implements SyntheticContractLogService {

    protected static final Version HAPI_SYNTHETIC_LOG_VERSION = new Version(0, 71, 0);

    private final ParserContext parserContext;

    private final EntityListener entityListener;

    private final EntityProperties entityProperties;

    private final byte[] empty = Bytes.of(0).toArray();

    protected static final byte[] CONTRACT_LOG_MARKER = Bytes.of(1).toArray();

    @Override
    public void create(SyntheticContractLog log) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean isContract(RecordItem recordItem) {
        return recordItem.getTransactionRecord().hasContractCallResult() || recordItem.getTransactionRecord().hasContractCreateResult();
    }

    private boolean shouldSkipLogCreation(SyntheticContractLog syntheticLog) {
        final var contractOrigin = isContract(syntheticLog.getRecordItem());
        if (contractOrigin && !(syntheticLog instanceof TransferContractLog)) {
            // Only TransferContractLog synthetic log creation is supported for an operation with contract origin
            return true;
        }
        final var recordItem = syntheticLog.getRecordItem();
        final var tokenTransfersCount = recordItem.getTransactionRecord().getTokenTransferListsCount();
        if (tokenTransfersCount > 2 && !entityProperties.getPersist().isSyntheticContractLogsMulti()) {
            // We have a multi-party fungible transfer scenario and synthetic event creation for
            // such transfers is disabled. We should skip this case no matter if the log is from HAPI or contract
            // origin.
            return true;
        }
        // Skip synthetic log creation for events with contract origin with HAPI versions >= 0.71.0 as the logs are
        // already imported by consensus nodes. We should create logs for events with HAPI origin for any HAPI version.
        return contractOrigin && recordItem.getHapiVersion().isGreaterThanOrEqualTo(HAPI_SYNTHETIC_LOG_VERSION);
    }
}
