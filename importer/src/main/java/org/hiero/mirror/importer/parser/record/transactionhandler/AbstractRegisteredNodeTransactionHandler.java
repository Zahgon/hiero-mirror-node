// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.transactionhandler;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.hiero.mirror.common.domain.node.RegisteredNode;
import org.hiero.mirror.common.domain.node.RegisteredServiceEndpoint;
import org.hiero.mirror.common.domain.node.RegisteredServiceEndpoint.BlockNodeApi;
import org.hiero.mirror.common.domain.node.RegisteredServiceEndpoint.BlockNodeEndpoint;
import org.hiero.mirror.common.domain.node.RegisteredServiceEndpoint.GeneralServiceEndpoint;
import org.hiero.mirror.common.domain.node.RegisteredServiceEndpoint.MirrorNodeEndpoint;
import org.hiero.mirror.common.domain.node.RegisteredServiceEndpoint.RpcRelayEndpoint;
import org.hiero.mirror.common.domain.transaction.RecordItem;
import org.hiero.mirror.common.domain.transaction.Transaction;
import org.hiero.mirror.common.util.DomainUtils;
import org.hiero.mirror.importer.parser.record.RegisteredNodeChangedEvent;
import org.hiero.mirror.importer.parser.record.entity.EntityListener;
import org.hiero.mirror.importer.util.Utility;
import org.springframework.context.ApplicationEventPublisher;

@RequiredArgsConstructor
abstract class AbstractRegisteredNodeTransactionHandler extends AbstractTransactionHandler {

    private final ApplicationEventPublisher applicationEventPublisher;

    private final EntityListener entityListener;

    protected abstract RegisteredNode parseRegisteredNode(final RecordItem recordItem);

    @Override
    protected void doUpdateTransaction(final Transaction transaction, final RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected static void parseServiceEndpoints(final RegisteredNode.RegisteredNodeBuilder<?, ?> builder, final List<com.hederahashgraph.api.proto.java.RegisteredServiceEndpoint> protoServiceEndpoints) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected static RegisteredServiceEndpoint toRegisteredServiceEndpoint(final com.hederahashgraph.api.proto.java.RegisteredServiceEndpoint proto) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static BlockNodeApi toBlockNodeApi(final com.hederahashgraph.api.proto.java.RegisteredServiceEndpoint.BlockNodeEndpoint.BlockNodeApi proto) {
        return switch(proto) {
            case OTHER ->
                BlockNodeApi.OTHER;
            case PUBLISH ->
                BlockNodeApi.PUBLISH;
            case STATE_PROOF ->
                BlockNodeApi.STATE_PROOF;
            case STATUS ->
                BlockNodeApi.STATUS;
            case SUBSCRIBE_STREAM ->
                BlockNodeApi.SUBSCRIBE_STREAM;
            case UNRECOGNIZED ->
                {
                    Utility.handleRecoverableError("Unrecognized BlockNodeApi enum value");
                    yield BlockNodeApi.UNRECOGNIZED;
                }
        };
    }
}
