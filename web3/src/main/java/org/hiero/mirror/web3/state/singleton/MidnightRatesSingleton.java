// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.state.singleton;

import static com.hedera.node.app.fees.schemas.V0490FeeSchema.MIDNIGHT_RATES_STATE_ID;
import com.hedera.hapi.node.base.FileID;
import com.hedera.hapi.node.transaction.ExchangeRateSet;
import com.hedera.node.app.fees.FeeService;
import jakarta.inject.Named;
import lombok.SneakyThrows;
import org.hiero.mirror.common.domain.SystemEntity;
import org.hiero.mirror.web3.common.ContractCallContext;
import org.hiero.mirror.web3.state.SystemFileLoader;
import org.hiero.mirror.web3.state.Utils;

@Named
final class MidnightRatesSingleton implements SingletonState<ExchangeRateSet> {

    private final FileID exchangeRateFileId;

    private final SystemFileLoader systemFileLoader;

    MidnightRatesSingleton(final SystemEntity systemEntity, final SystemFileLoader systemFileLoader) {
        this.exchangeRateFileId = Utils.toFileID(systemEntity.exchangeRateFile());
        this.systemFileLoader = systemFileLoader;
    }

    @Override
    public int getStateId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getServiceName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SneakyThrows
    @Override
    public ExchangeRateSet get() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
