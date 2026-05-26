// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.entity.staking;

import com.google.common.base.Stopwatch;
import jakarta.inject.Named;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.common.domain.SystemEntity;
import org.hiero.mirror.importer.parser.record.entity.EntityProperties;
import org.hiero.mirror.importer.repository.EntityStakeRepository;
import org.springframework.transaction.support.TransactionOperations;

@CustomLog
@Named
@RequiredArgsConstructor
public class EntityStakeCalculatorImpl implements EntityStakeCalculator {

    private final EntityProperties entityProperties;

    private final EntityStakeRepository entityStakeRepository;

    private final AtomicBoolean running = new AtomicBoolean(false);

    private final TransactionOperations transactionOperations;

    private final SystemEntity systemEntity;

    @Override
    public void calculate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
