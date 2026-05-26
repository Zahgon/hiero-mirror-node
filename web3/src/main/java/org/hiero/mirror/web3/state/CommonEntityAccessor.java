// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.state;

import static com.hedera.services.utils.EntityIdUtils.toEntityId;
import static org.hiero.mirror.common.util.DomainUtils.EVM_ADDRESS_LENGTH;
import static org.hiero.mirror.web3.evm.utils.EvmTokenUtils.entityIdNumFromEvmAddress;
import static org.hiero.mirror.web3.evm.utils.EvmTokenUtils.toAddress;
import com.hedera.hapi.node.base.AccountID;
import com.hedera.hapi.node.base.TokenID;
import com.hedera.node.app.service.contract.impl.utils.ConversionUtils;
import com.hedera.pbj.runtime.io.buffer.Bytes;
import jakarta.inject.Named;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.common.domain.entity.Entity;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.exception.InvalidEntityException;
import org.hiero.mirror.web3.repository.EntityRepository;
import org.hyperledger.besu.datatypes.Address;
import org.jspecify.annotations.NonNull;

@Named
@RequiredArgsConstructor
public class CommonEntityAccessor {

    private final EntityRepository entityRepository;

    @NonNull
    public Optional<Entity> get(@NonNull final Address address, final Optional<Long> timestamp) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @NonNull
    public Optional<Entity> get(@NonNull final AccountID accountID, final Optional<Long> timestamp) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @NonNull
    public Optional<Entity> get(@NonNull final Bytes alias, final Optional<Long> timestamp) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @NonNull
    public Optional<Entity> get(@NonNull final TokenID tokenID, final Optional<Long> timestamp) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @NonNull
    public Optional<Entity> get(@NonNull final EntityId entityId, final Optional<Long> timestamp) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Optional<Entity> getEntityByEvmAddressAndTimestamp(final byte[] addressBytes, final Optional<Long> timestamp) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private Optional<Entity> getEntityByMirrorAddressAndTimestamp(Address address, final Optional<Long> timestamp) {
        final var entityId = entityIdNumFromEvmAddress(address);
        return timestamp.map(t -> entityRepository.findActiveByIdAndTimestamp(entityId, t)).orElseGet(() -> entityRepository.findByIdAndDeletedIsFalse(entityId));
    }

    private Optional<Entity> getEntityByEvmAddressTimestamp(byte[] addressBytes, final Optional<Long> timestamp) {
        return timestamp.map(t -> entityRepository.findActiveByEvmAddressAndTimestamp(addressBytes, t)).orElseGet(() -> entityRepository.findByEvmAddressAndDeletedIsFalse(addressBytes));
    }

    public Address evmAddressFromId(EntityId entityId, final Optional<Long> timestamp) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
