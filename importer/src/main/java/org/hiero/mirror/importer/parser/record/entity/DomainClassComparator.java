// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.entity;

import static java.util.stream.Collectors.toMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.IntStream;
import org.hiero.mirror.common.domain.token.DissociateTokenTransfer;
import org.hiero.mirror.common.domain.token.Nft;
import org.hiero.mirror.common.domain.token.Token;
import org.hiero.mirror.common.domain.token.TokenAccount;
import org.hiero.mirror.common.domain.token.TokenTransfer;
import org.hiero.mirror.common.domain.transaction.Transaction;

/**
 * A comparator that allows domain objects to be iterated over and persisted in the appropriate order. The ORDER field
 * specifies an explicit ordering of domain classes with earlier entries persisting first. When comparing an item
 * without an explicit order against one with an explicit order, the explicitly ordered one should always sort last.
 * Comparing two that are not explicitly ordered falls back to order by class name.
 */
class DomainClassComparator implements Comparator<Class<?>> {

    // Potentially we could add a dependsOn parameter to @Upsertable and inject the EntityMetadataRegistry for this
    static final List<Class<?>> ORDER = List.of(// Token should persist before TokenAccount
    Token.class, TokenAccount.class, // The next 3 should persist before DissociateTokenTransfer
    Nft.class, Transaction.class, TokenTransfer.class, DissociateTokenTransfer.class);

    private static final Map<Class<?>, Integer> ORDER_MAP = IntStream.range(0, ORDER.size()).boxed().collect(toMap(ORDER::get, Function.identity()));

    @Override
    public int compare(Class<?> left, Class<?> right) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
