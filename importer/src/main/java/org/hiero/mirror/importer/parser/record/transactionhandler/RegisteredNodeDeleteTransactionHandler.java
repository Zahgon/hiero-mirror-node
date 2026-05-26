// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.transactionhandler;

import com.google.common.collect.Range;
import jakarta.inject.Named;
import org.hiero.mirror.common.domain.node.RegisteredNode;
import org.hiero.mirror.common.domain.transaction.RecordItem;
import org.hiero.mirror.common.domain.transaction.TransactionType;
import org.hiero.mirror.importer.parser.record.entity.EntityListener;
import org.springframework.context.ApplicationEventPublisher;

@Named
final class RegisteredNodeDeleteTransactionHandler extends AbstractRegisteredNodeTransactionHandler {

    RegisteredNodeDeleteTransactionHandler(ApplicationEventPublisher applicationEventPublisher, EntityListener entityListener) {
        super(applicationEventPublisher, entityListener);
    }

    @Override
    public TransactionType getType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public RegisteredNode parseRegisteredNode(RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
