// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.downloader.provider;

import com.google.common.annotations.VisibleForTesting;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import lombok.CustomLog;
import lombok.Value;
import org.hiero.mirror.importer.addressbook.ConsensusNode;
import org.hiero.mirror.importer.domain.StreamFileData;
import org.hiero.mirror.importer.domain.StreamFilename;
import org.hiero.mirror.importer.downloader.CommonDownloaderProperties;
import org.hiero.mirror.importer.downloader.StreamSourceProperties;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Primary;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@CustomLog
@Named
@NullMarked
@Primary
final class CompositeStreamFileProvider implements StreamFileProvider {

    private final List<ProviderHealth> providers;

    public CompositeStreamFileProvider(final CommonDownloaderProperties properties, final List<StreamFileProvider> providers) {
        final var providerHealth = new ArrayList<ProviderHealth>();
        for (int i = 0; i < providers.size(); ++i) {
            final var provider = providers.get(i);
            final var sourceProperties = properties.getSources().get(i);
            providerHealth.add(new ProviderHealth(provider, sourceProperties));
        }
        this.providers = Collections.unmodifiableList(providerHealth);
    }

    @Override
    public Mono<StreamFileData> get(final StreamFilename streamFilename) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Flux<StreamFileData> list(final ConsensusNode consensusNode, final StreamFilename lastFilename) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Mono<String> discoverNetwork() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // Get the next healthy provider
    @Nullable
    private StreamFileProvider getProvider(final AtomicInteger index) {
        for (; index.get() < providers.size(); index.getAndIncrement()) {
            final var provider = providers.get(index.get());
            if (provider.isHealthy()) {
                return provider.getProvider();
            }
        }
        return null;
    }

    private boolean shouldRetry(final Retry.RetrySignal r, final AtomicInteger index) {
        final var exception = r.failure();
        log.warn("Attempt #{} failed: {}", r.totalRetries() + 1, exception.getMessage());
        if (exception instanceof final TransientProviderException t) {
            throw t;
        }
        // Ensure we always keep at least one provider available
        if (index.get() + 1 >= providers.size()) {
            throw Exceptions.propagate(exception);
        }
        final var provider = providers.get(index.getAndIncrement());
        provider.markUnhealthy();
        return true;
    }

    @VisibleForTesting
    boolean isHealthy() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Value
    private class ProviderHealth {

        private final StreamFileProvider provider;

        private final StreamSourceProperties sourceProperties;

        // Zero indicates healthy
        private final AtomicLong readmitTime = new AtomicLong(0L);

        /**
         * Determines if the provider is healthy. This has the side effect of marking an unhealthy provider as healthy
         * again if its readmit time has passed.
         *
         * @return whether the provider is healthy
         */
        boolean isHealthy() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void markUnhealthy() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
