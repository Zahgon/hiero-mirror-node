// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.restjava.mapper;

import org.hiero.mirror.common.util.DomainUtils;
import org.hiero.mirror.rest.model.NetworkSupplyResponse;
import org.hiero.mirror.restjava.dto.NetworkSupply;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfiguration.class)
public interface NetworkSupplyMapper {

    long DECIMALS_IN_HBARS = 100_000_000L;

    int DECIMAL_DIGITS = String.valueOf(DECIMALS_IN_HBARS).length() - 1;

    default NetworkSupplyResponse map(NetworkSupply networkSupply) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default String convertToCurrencyFormat(long valueInTinyCoins) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
