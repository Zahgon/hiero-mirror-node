// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.downloader.block;

import io.grpc.DecompressorRegistry;
import io.grpc.ManagedChannelBuilder;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.grpc.MetricCollectingClientInterceptor;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
final class ManagedChannelBuilderProviderImpl implements ManagedChannelBuilderProvider {

    private static final String TAG_SERVER = "server";

    private final MeterRegistry meterRegistry;

    private final ZstdCodec zstdCodec;

    @Override
    public ManagedChannelBuilder<?> get(final String host, final int port, final boolean useTls) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
