// SPDX-License-Identifier: Apache-2.0
package com.hedera.services.utils;

import static org.hiero.mirror.common.util.DomainUtils.fromEvmAddress;
import static org.hiero.mirror.common.util.DomainUtils.toEvmAddress;
import com.hedera.hapi.node.base.AccountID.AccountOneOfType;
import com.hedera.pbj.runtime.OneOf;
import com.hederahashgraph.api.proto.java.AccountID;
import com.hederahashgraph.api.proto.java.ContractID;
import com.hederahashgraph.api.proto.java.TokenID;
import org.apache.tuweni.bytes.Bytes;
import org.hiero.mirror.common.domain.entity.Entity;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hyperledger.besu.datatypes.Address;

public final class EntityIdUtils {

    private EntityIdUtils() {
        throw new UnsupportedOperationException("Utility Class");
    }

    public static AccountID accountIdFromEvmAddress(final Address address) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static AccountID accountIdFromEvmAddress(final byte[] bytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static ContractID contractIdFromEvmAddress(final byte[] bytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static ContractID contractIdFromEvmAddress(final Address address) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Address asTypedEvmAddress(final ContractID id) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Address asTypedEvmAddress(final AccountID id) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Address asTypedEvmAddress(final TokenID id) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static EntityId toEntityId(final com.hedera.hapi.node.base.AccountID accountID) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static EntityId toEntityId(final com.hedera.hapi.node.base.ScheduleID scheduleID) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static EntityId toEntityId(final com.hedera.hapi.node.base.TokenID tokenID) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static EntityId toEntityId(final com.hedera.hapi.node.base.FileID fileID) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static com.hedera.hapi.node.base.AccountID toAccountId(final Long id) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static com.hedera.hapi.node.base.AccountID toAccountId(final long shard, final long realm, final long num) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static com.hedera.hapi.node.base.AccountID toAccountId(final Entity entity) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static com.hedera.hapi.node.base.AccountID toAccountId(final Long shard, final Long realm, final byte[] alias) {
        return com.hedera.hapi.node.base.AccountID.newBuilder().shardNum(shard).realmNum(realm).alias(com.hedera.pbj.runtime.io.buffer.Bytes.wrap(alias)).build();
    }

    public static com.hedera.hapi.node.base.AccountID toAccountId(final EntityId entityId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static com.hedera.hapi.node.base.AccountID toAccountId(final Long shard, final Long realm, final Long num) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static com.hedera.hapi.node.base.TokenID toTokenId(final Long entityId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static com.hedera.hapi.node.base.TokenID toTokenId(final EntityId entityId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static com.hedera.hapi.node.base.ContractID toContractID(final Address address) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static EntityId entityIdFromContractId(final com.hedera.hapi.node.base.ContractID id) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
