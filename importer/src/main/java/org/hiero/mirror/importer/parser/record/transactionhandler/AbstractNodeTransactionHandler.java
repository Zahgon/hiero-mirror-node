// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.transactionhandler;

import java.net.InetAddress;
import java.net.UnknownHostException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hiero.mirror.common.domain.node.Node;
import org.hiero.mirror.common.domain.node.ServiceEndpoint;
import org.hiero.mirror.common.domain.transaction.RecordItem;
import org.hiero.mirror.common.domain.transaction.Transaction;
import org.hiero.mirror.common.util.DomainUtils;
import org.hiero.mirror.importer.parser.record.entity.EntityListener;
import org.hiero.mirror.importer.util.Utility;

@RequiredArgsConstructor
public abstract class AbstractNodeTransactionHandler extends AbstractTransactionHandler {

    private final EntityListener entityListener;

    public abstract Node parseNode(RecordItem recordItem);

    @Override
    protected void doUpdateTransaction(Transaction transaction, RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected final ServiceEndpoint toServiceEndpoint(long consensusTimestamp, com.hederahashgraph.api.proto.java.ServiceEndpoint proto) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
