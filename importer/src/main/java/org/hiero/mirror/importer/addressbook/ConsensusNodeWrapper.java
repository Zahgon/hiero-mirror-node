// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.addressbook;

import java.security.PublicKey;
import java.util.Objects;
import lombok.Value;
import org.hiero.mirror.common.domain.addressbook.AddressBookEntry;
import org.hiero.mirror.common.domain.addressbook.NodeStake;
import org.hiero.mirror.common.domain.entity.EntityId;

@Value
final class ConsensusNodeWrapper implements ConsensusNode {

    private final AddressBookEntry addressBookEntry;

    private final NodeStake nodeStake;

    private final long nodeCount;

    private final long totalStake;

    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public long getNodeId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public EntityId getNodeAccountId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public PublicKey getPublicKey() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public long getStake() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public long getTotalStake() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
