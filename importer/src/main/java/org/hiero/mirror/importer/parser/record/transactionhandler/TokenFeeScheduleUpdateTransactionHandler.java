// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.transactionhandler;

import static org.hiero.mirror.common.domain.transaction.TransactionType.TOKENCREATION;
import com.google.common.collect.Range;
import jakarta.inject.Named;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.domain.token.AbstractFee;
import org.hiero.mirror.common.domain.token.CustomFee;
import org.hiero.mirror.common.domain.token.FallbackFee;
import org.hiero.mirror.common.domain.token.FixedFee;
import org.hiero.mirror.common.domain.token.FractionalFee;
import org.hiero.mirror.common.domain.token.RoyaltyFee;
import org.hiero.mirror.common.domain.transaction.RecordItem;
import org.hiero.mirror.common.domain.transaction.Transaction;
import org.hiero.mirror.common.domain.transaction.TransactionType;
import org.hiero.mirror.importer.parser.record.entity.EntityListener;
import org.hiero.mirror.importer.parser.record.entity.EntityProperties;
import org.hiero.mirror.importer.util.Utility;

@CustomLog
@Named
@RequiredArgsConstructor
class TokenFeeScheduleUpdateTransactionHandler extends AbstractTransactionHandler {

    private final EntityListener entityListener;

    private final EntityProperties entityProperties;

    @Override
    public EntityId getEntity(RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public TransactionType getType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void doUpdateTransaction(Transaction transaction, RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Handles custom fees. Returns the list of collectors automatically associated with the newly created token if the
     * custom fees are from a token create transaction
     *
     * @param protoCustomFees protobuf custom fee list
     * @return A list of collectors automatically associated with the token if it's a token create transaction
     */
    Set<EntityId> updateCustomFees(Collection<com.hederahashgraph.api.proto.java.CustomFee> protoCustomFees, RecordItem recordItem, Transaction transaction) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Parse protobuf FixedFee object to domain FixedFee object.
     *
     * @param protoFixedFee the protobuf FixedFee object
     * @param tokenId       the attached token id
     * @return whether the fee is paid in the attached token
     */
    private FixedFee parseFixedFee(com.hederahashgraph.api.proto.java.FixedFee protoFixedFee, EntityId tokenId) {
        var fixedFee = new FixedFee();
        fixedFee.setAmount(protoFixedFee.getAmount());
        if (protoFixedFee.hasDenominatingTokenId()) {
            var denominatingTokenId = EntityId.of(protoFixedFee.getDenominatingTokenId());
            denominatingTokenId = denominatingTokenId == EntityId.EMPTY ? tokenId : denominatingTokenId;
            fixedFee.setDenominatingTokenId(denominatingTokenId);
        }
        return fixedFee;
    }

    /**
     * Parse protobuf FractionalFee object to domain FractionalFee object.
     *
     * @param protoFractionalFee the protobuf FractionalFee object
     */
    private FractionalFee parseFractionalFee(com.hederahashgraph.api.proto.java.FractionalFee protoFractionalFee) {
        var fractionalFee = new FractionalFee();
        fractionalFee.setDenominator(protoFractionalFee.getFractionalAmount().getDenominator());
        long maximumAmount = protoFractionalFee.getMaximumAmount();
        if (maximumAmount != 0) {
            fractionalFee.setMaximumAmount(maximumAmount);
        }
        fractionalFee.setMinimumAmount(protoFractionalFee.getMinimumAmount());
        fractionalFee.setNumerator(protoFractionalFee.getFractionalAmount().getNumerator());
        fractionalFee.setNetOfTransfers(protoFractionalFee.getNetOfTransfers());
        return fractionalFee;
    }

    /**
     * Parse protobuf RoyaltyFee object to domain RoyaltyFee object.
     *
     * @param protoRoyaltyFee the protobuf RoyaltyFee object
     */
    private RoyaltyFee parseRoyaltyFee(com.hederahashgraph.api.proto.java.RoyaltyFee protoRoyaltyFee, EntityId tokenId) {
        var royaltyFee = new RoyaltyFee();
        royaltyFee.setDenominator(protoRoyaltyFee.getExchangeValueFraction().getDenominator());
        royaltyFee.setNumerator(protoRoyaltyFee.getExchangeValueFraction().getNumerator());
        if (protoRoyaltyFee.hasFallbackFee()) {
            var fallbackFee = new FallbackFee();
            fallbackFee.setAmount(protoRoyaltyFee.getFallbackFee().getAmount());
            if (protoRoyaltyFee.getFallbackFee().hasDenominatingTokenId()) {
                var denominatingTokenId = EntityId.of(protoRoyaltyFee.getFallbackFee().getDenominatingTokenId());
                denominatingTokenId = denominatingTokenId == EntityId.EMPTY ? tokenId : denominatingTokenId;
                fallbackFee.setDenominatingTokenId(denominatingTokenId);
            }
            royaltyFee.setFallbackFee(fallbackFee);
        }
        return royaltyFee;
    }
}
