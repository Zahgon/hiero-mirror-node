// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.monitor.config;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.function.Function;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.monitor.publish.PublishException;
import org.hiero.mirror.monitor.publish.PublishMetrics;
import org.hiero.mirror.monitor.publish.PublishProperties;
import org.hiero.mirror.monitor.publish.PublishRequest;
import org.hiero.mirror.monitor.publish.TransactionPublisher;
import org.hiero.mirror.monitor.publish.generator.TransactionGenerator;
import org.hiero.mirror.monitor.subscribe.MirrorSubscriber;
import org.hiero.mirror.monitor.subscribe.SubscribeMetrics;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.scheduler.Schedulers;

@CustomLog
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
final class MonitorConfiguration {

    static {
        // Avoid logging a stack trace when the SDK thread throws an exception right after the flux timeout
        Hooks.onErrorDropped(t -> {
            if (!(t instanceof CompletionException)) {
                log.warn("onErrorDropped: {}", t.getMessage());
            }
        });
    }

    private final MirrorSubscriber mirrorSubscriber;

    private final PublishMetrics publishMetrics;

    private final PublishProperties publishProperties;

    private final SubscribeMetrics subscribeMetrics;

    private final TransactionGenerator transactionGenerator;

    private final TransactionPublisher transactionPublisher;

    @Bean
    KubernetesClient kubernetesClient() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Constructs a reactive flow for publishing transactions. The transaction generator will run on a single thread and
     * generate transactions as fast as possible. Next, a parallel Flux will concurrently publish those transactions to
     * the main nodes. Once the response is received, it will be sent to subscribers in case they need to sample them to
     * validate whether that transaction was received by the mirror node APIs. Finally, metrics will be collected for
     * every published transaction.
     *
     * @return the publishing flow's Disposable
     */
    @Bean(destroyMethod = "dispose")
    @ConditionalOnProperty(value = "hiero.mirror.monitor.publish.enabled", havingValue = "true", matchIfMissing = true)
    Disposable publish() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Starts subscribing to mirror node APIs to receive data, sending the results to the metrics collector.
     *
     * @return the subscribing flow's Disposable
     */
    @Bean(destroyMethod = "dispose")
    @ConditionalOnProperty(value = "hiero.mirror.monitor.subscribe.enabled", havingValue = "true", matchIfMissing = true)
    Disposable subscribe() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
