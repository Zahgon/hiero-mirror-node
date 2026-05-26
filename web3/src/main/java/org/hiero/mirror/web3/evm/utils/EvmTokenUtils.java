// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.evm.utils;

import static org.hiero.mirror.common.util.DomainUtils.fromEvmAddress;
import static org.hiero.mirror.common.util.DomainUtils.toEvmAddress;
import com.hederahashgraph.api.proto.java.ContractID;
import lombok.experimental.UtilityClass;
import org.apache.tuweni.bytes.Bytes;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hyperledger.besu.datatypes.Address;

@UtilityClass
public class EvmTokenUtils {

    public static Long entityIdNumFromEvmAddress(final Address address) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static EntityId entityIdFromEvmAddress(final Address address) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Address toAddress(final long encodedId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Address toAddress(final EntityId entityId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Address toAddress(final ContractID contractID) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
