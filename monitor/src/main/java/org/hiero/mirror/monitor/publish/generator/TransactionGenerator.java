// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.monitor.publish.generator;

import java.util.List;
import org.hiero.mirror.monitor.publish.PublishRequest;
import org.hiero.mirror.monitor.publish.PublishScenario;
import reactor.core.publisher.Flux;

public interface TransactionGenerator {

    /**
     * Gets the next count publish requests. If count > 0, up to count publish requests will be generated; if count <=
     * 0, the generator will determine the actual count.
     *
     * @param count
     * @return
     */
    List<PublishRequest> next(int count);

    default List<PublishRequest> next() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    Flux<PublishScenario> scenarios();
}
