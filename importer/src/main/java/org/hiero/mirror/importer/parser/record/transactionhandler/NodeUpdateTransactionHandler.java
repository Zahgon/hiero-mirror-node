// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.transactionhandler;

import com.google.common.collect.Range;
import jakarta.inject.Named;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.domain.node.Node;
import org.hiero.mirror.common.domain.transaction.RecordItem;
import org.hiero.mirror.common.domain.transaction.TransactionType;
import org.hiero.mirror.importer.domain.EntityIdService;
import org.hiero.mirror.importer.parser.record.entity.EntityListener;

@Named
class NodeUpdateTransactionHandler extends AbstractNodeTransactionHandler {

    private final EntityIdService entityIdService;

    public NodeUpdateTransactionHandler(EntityListener entityListener, EntityIdService entityIdService) {
        super(entityListener);
        this.entityIdService = entityIdService;
    }

    @Override
    public EntityId getEntity(RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
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
