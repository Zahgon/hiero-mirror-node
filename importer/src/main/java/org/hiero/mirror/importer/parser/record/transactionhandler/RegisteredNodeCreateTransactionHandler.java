// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.transactionhandler;

import com.google.common.collect.Range;
import jakarta.inject.Named;
import org.hiero.mirror.common.domain.node.RegisteredNode;
import org.hiero.mirror.common.domain.transaction.RecordItem;
import org.hiero.mirror.common.domain.transaction.TransactionType;
import org.hiero.mirror.importer.parser.record.entity.EntityListener;
import org.hiero.mirror.importer.util.Utility;
import org.springframework.context.ApplicationEventPublisher;

@Named
final class RegisteredNodeCreateTransactionHandler extends AbstractRegisteredNodeTransactionHandler {

    RegisteredNodeCreateTransactionHandler(final ApplicationEventPublisher applicationEventPublisher, final EntityListener entityListener) {
        super(applicationEventPublisher, entityListener);
    }

    @Override
    public TransactionType getType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public RegisteredNode parseRegisteredNode(final RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
