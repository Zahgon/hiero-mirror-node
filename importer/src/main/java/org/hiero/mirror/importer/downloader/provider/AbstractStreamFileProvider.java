// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.downloader.provider;

import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.common.CommonProperties;
import org.hiero.mirror.importer.downloader.CommonDownloaderProperties;
import org.jspecify.annotations.NullMarked;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@NullMarked
@RequiredArgsConstructor
abstract class AbstractStreamFileProvider implements StreamFileProvider {

    protected final CommonProperties commonProperties;

    protected final CommonDownloaderProperties downloaderProperties;

    @Override
    public Mono<String> discoverNetwork() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    abstract Flux<String> doDiscoverNetwork();
}
