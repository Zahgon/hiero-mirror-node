// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.transactionhandler;

import com.google.common.collect.Range;
import jakarta.inject.Named;
import org.hiero.mirror.common.domain.node.Node;
import org.hiero.mirror.common.domain.transaction.RecordItem;
import org.hiero.mirror.common.domain.transaction.TransactionType;
import org.hiero.mirror.importer.parser.record.entity.EntityListener;

@Named
class NodeDeleteTransactionHandler extends AbstractNodeTransactionHandler {

    public NodeDeleteTransactionHandler(EntityListener entityListener) {
        super(entityListener);
    }

    @Override
    public TransactionType getType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Node parseNode(RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
