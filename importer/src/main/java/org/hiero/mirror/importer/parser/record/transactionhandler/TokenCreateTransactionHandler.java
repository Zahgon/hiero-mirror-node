// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.transactionhandler;

import static org.hiero.mirror.common.domain.token.TokenFreezeStatusEnum.FROZEN;
import static org.hiero.mirror.common.domain.token.TokenFreezeStatusEnum.NOT_APPLICABLE;
import static org.hiero.mirror.common.domain.token.TokenFreezeStatusEnum.UNFROZEN;
import static org.hiero.mirror.common.domain.transaction.RecordFile.HAPI_VERSION_0_49_0;
import com.hederahashgraph.api.proto.java.TokenAssociation;
import jakarta.inject.Named;
import lombok.CustomLog;
import org.hiero.mirror.common.domain.entity.Entity;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.domain.entity.EntityType;
import org.hiero.mirror.common.domain.token.Token;
import org.hiero.mirror.common.domain.token.TokenAccount;
import org.hiero.mirror.common.domain.token.TokenKycStatusEnum;
import org.hiero.mirror.common.domain.token.TokenPauseStatusEnum;
import org.hiero.mirror.common.domain.token.TokenSupplyTypeEnum;
import org.hiero.mirror.common.domain.token.TokenTypeEnum;
import org.hiero.mirror.common.domain.transaction.RecordItem;
import org.hiero.mirror.common.domain.transaction.Transaction;
import org.hiero.mirror.common.domain.transaction.TransactionType;
import org.hiero.mirror.common.util.DomainUtils;
import org.hiero.mirror.importer.domain.EntityIdService;
import org.hiero.mirror.importer.parser.record.entity.EntityListener;
import org.hiero.mirror.importer.parser.record.entity.EntityProperties;
import org.hiero.mirror.importer.util.Utility;

@CustomLog
@Named
class TokenCreateTransactionHandler extends AbstractEntityCrudTransactionHandler {

    private final EntityProperties entityProperties;

    private final TokenFeeScheduleUpdateTransactionHandler tokenFeeScheduleUpdateTransactionHandler;

    TokenCreateTransactionHandler(EntityIdService entityIdService, EntityListener entityListener, EntityProperties entityProperties, TokenFeeScheduleUpdateTransactionHandler tokenFeeScheduleUpdateTransactionHandler) {
        super(entityIdService, entityListener, TransactionType.TOKENCREATION);
        this.entityProperties = entityProperties;
        this.tokenFeeScheduleUpdateTransactionHandler = tokenFeeScheduleUpdateTransactionHandler;
    }

    @Override
    public EntityId getEntity(RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void doUpdateEntity(Entity entity, RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @SuppressWarnings("java:S3776")
    protected void doUpdateTransaction(Transaction transaction, RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
