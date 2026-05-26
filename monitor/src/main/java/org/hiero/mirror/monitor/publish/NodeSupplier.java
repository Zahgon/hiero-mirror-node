// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.monitor.publish;

import static com.hedera.hashgraph.sdk.Status.SUCCESS;
import com.google.common.annotations.VisibleForTesting;
import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.hedera.hashgraph.sdk.Status;
import com.hedera.hashgraph.sdk.TransferTransaction;
import com.hedera.hashgraph.sdk.proto.NodeAddressBook;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.hiero.mirror.monitor.MonitorProperties;
import org.hiero.mirror.monitor.NodeProperties;
import org.hiero.mirror.monitor.subscribe.rest.RestApiClient;
import org.hiero.mirror.rest.model.NetworkNode;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

@CustomLog
@Named
@RequiredArgsConstructor
public class NodeSupplier {

    private final MonitorProperties monitorProperties;

    private final RestApiClient restApiClient;

    private final AtomicLong counter = new AtomicLong(0L);

    private final CopyOnWriteArrayList<NodeProperties> nodes = new CopyOnWriteArrayList<>();

    @PostConstruct
    public void init() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public NodeProperties get() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public synchronized Flux<NodeProperties> refresh() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private Flux<NodeProperties> getAddressBook() {
        if (!monitorProperties.getNodeValidation().isRetrieveAddressBook()) {
            return Flux.empty();
        }
        var count = new AtomicInteger(0);
        return Flux.defer(restApiClient::getNodes).filter(n -> !CollectionUtils.isEmpty(n.getServiceEndpoints())).flatMapIterable(this::toNodeProperties).doOnNext(n -> count.incrementAndGet()).doOnComplete(() -> log.info("Retrieved {} nodes from address book", count));
    }

    private List<NodeProperties> toNodeProperties(NetworkNode networkNode) {
        var nodeValidation = monitorProperties.getNodeValidation();
        var tlsPredicate = nodeValidation.getTls().getPredicate();
        return networkNode.getServiceEndpoints().stream().filter(s -> tlsPredicate.test(s.getPort())).limit(nodeValidation.getMaxEndpointsPerNode()).map(serviceEndpoint -> {
            var host = StringUtils.isNotBlank(serviceEndpoint.getDomainName()) ? serviceEndpoint.getDomainName() : serviceEndpoint.getIpAddressV4();
            var nodeProperties = new NodeProperties();
            nodeProperties.setAccountId(networkNode.getNodeAccountId());
            nodeProperties.setCertHash(Strings.CS.remove(networkNode.getNodeCertHash(), "0x"));
            nodeProperties.setHost(host);
            nodeProperties.setNodeId(networkNode.getNodeId());
            nodeProperties.setPort(serviceEndpoint.getPort());
            return nodeProperties;
        }).toList();
    }

    @SneakyThrows
    private Client toClient(NodeProperties node) {
        var operatorId = AccountId.fromString(monitorProperties.getOperator().getAccountId());
        var operatorPrivateKey = PrivateKey.fromString(monitorProperties.getOperator().getPrivateKey());
        var validationProperties = monitorProperties.getNodeValidation();
        var network = Map.of(node.getEndpoint(), AccountId.fromString(node.getAccountId()));
        var nodeAddress = node.toNodeAddress();
        var nodeAddressBook = NodeAddressBook.newBuilder().addNodeAddress(nodeAddress).build().toByteString();
        var client = Client.forNetwork(Map.of());
        client.setNetworkFromAddressBook(com.hedera.hashgraph.sdk.NodeAddressBook.fromBytes(nodeAddressBook));
        client.setNetwork(network);
        client.setMaxAttempts(validationProperties.getMaxAttempts());
        client.setMaxBackoff(validationProperties.getMaxBackoff());
        client.setMinBackoff(validationProperties.getMinBackoff());
        client.setOperator(operatorId, operatorPrivateKey);
        client.setRequestTimeout(validationProperties.getRequestTimeout());
        return client;
    }

    @VisibleForTesting
    boolean validateNode(NodeProperties node) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
