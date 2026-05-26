// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.contractresult;

import com.hederahashgraph.api.proto.java.TransactionBody;
import com.hederahashgraph.api.proto.java.TransactionRecord;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.apache.tuweni.bytes.Bytes;
import org.hiero.mirror.common.domain.contract.ContractResult;
import org.hiero.mirror.common.domain.transaction.RecordItem;
import org.hiero.mirror.importer.parser.record.entity.EntityListener;
import org.hiero.mirror.importer.parser.record.entity.EntityProperties;

@Named
@RequiredArgsConstructor
public class SyntheticContractResultServiceImpl implements SyntheticContractResultService {

    private final EntityListener entityListener;

    private final EntityProperties entityProperties;

    @Override
    public void create(SyntheticContractResult result) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean isContract(RecordItem recordItem) {
        return recordItem.getTransactionRecord().hasContractCallResult() || recordItem.getTransactionRecord().hasContractCreateResult();
    }
}
