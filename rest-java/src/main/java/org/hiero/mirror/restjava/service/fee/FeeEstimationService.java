// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.restjava.service.fee;

import static com.hedera.hapi.util.HapiUtils.functionOf;
import static com.hedera.node.app.workflows.standalone.TransactionExecutors.TRANSACTION_EXECUTORS;
import com.hedera.hapi.node.base.HederaFunctionality;
import com.hedera.hapi.node.base.SignatureMap;
import com.hedera.hapi.node.base.Transaction;
import com.hedera.hapi.node.transaction.SignedTransaction;
import com.hedera.hapi.node.transaction.TransactionBody;
import com.hedera.hapi.util.UnknownHederaFunctionality;
import com.hedera.node.app.fees.FeeManager;
import com.hedera.node.app.service.entityid.impl.AppEntityIdFactory;
import com.hedera.node.app.spi.fees.FeeContext;
import com.hedera.node.app.spi.fees.SimpleFeeCalculator;
import com.hedera.node.app.spi.fees.SimpleFeeContext;
import com.hedera.node.app.spi.workflows.QueryContext;
import com.hedera.node.app.workflows.standalone.ExecutorComponent;
import com.hedera.pbj.runtime.ParseException;
import com.hedera.pbj.runtime.io.buffer.Bytes;
import jakarta.inject.Named;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import lombok.CustomLog;
import org.hiero.hapi.fees.FeeResult;
import org.hiero.mirror.common.domain.SystemEntity;
import org.hiero.mirror.rest.model.FeeEstimateMode;
import org.hiero.mirror.restjava.repository.FileDataRepository;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

@CustomLog
@Named
public class FeeEstimationService {

    private final ExecutorComponent executor;

    private final FileDataRepository fileDataRepository;

    private final long feeScheduleFileId;

    private final FeeTopicStore feeTopicStore;

    private final FeeTokenStore feeTokenStore;

    private final AtomicLong lastFeeScheduleTimestamp;

    private final FeeManager feeManager;

    public FeeEstimationService(final FeeEstimationState feeEstimationState, final FileDataRepository fileDataRepository, final SystemEntity systemEntity, final FeeTopicStore feeTopicStore, final FeeTokenStore feeTokenStore) {
        this.fileDataRepository = fileDataRepository;
        this.feeScheduleFileId = systemEntity.simpleFeeScheduleFile().getId();
        this.feeTopicStore = feeTopicStore;
        this.feeTokenStore = feeTokenStore;
        this.lastFeeScheduleTimestamp = new AtomicLong(Long.MIN_VALUE);
        this.executor = TRANSACTION_EXECUTORS.newExecutorComponent(feeEstimationState, Map.of(), null, Set.of(), new AppEntityIdFactory(FeeEstimationFeeContext.CONFIGURATION));
        executor.stateNetworkInfo().initFrom(feeEstimationState);
        this.feeManager = Objects.requireNonNull(executor.feeManager());
    }

    @Async
    @EventListener(ApplicationReadyEvent.class)
    public void initFeeSchedule() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Scheduled(fixedDelayString = "${hiero.mirror.rest-java.fee.refresh-interval:PT10M}")
    public void refreshStateCalculator() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public FeeResult estimateFees(@NonNull final Transaction transaction, @NonNull final FeeEstimateMode mode, final int throttleUtilization) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private FeeEstimationFeeContext newFeeContext(final TransactionBody body, final int throttleUtilization) {
        return new FeeEstimationFeeContext(body, feeTopicStore, feeTokenStore, throttleUtilization);
    }

    @SuppressWarnings("NullAway")
    private static final class TransactionFeeContext implements SimpleFeeContext {

        private final Transaction transaction;

        private final TransactionBody body;

        private final int numTxnSignatures;

        @Nullable
        private final FeeContext feeContext;

        TransactionFeeContext(final Transaction transaction) throws ParseException {
            this(transaction, null);
        }

        TransactionFeeContext(final Transaction transaction, @Nullable final FeeContext feeContext) throws ParseException {
            this.transaction = transaction;
            this.feeContext = feeContext;
            if (transaction.signedTransactionBytes().length() > 0) {
                final var signedTransaction = SignedTransaction.PROTOBUF.parse(transaction.signedTransactionBytes());
                this.body = TransactionBody.PROTOBUF.parse(signedTransaction.bodyBytes());
                this.numTxnSignatures = signedTransaction.sigMapOrElse(SignatureMap.DEFAULT).sigPair().size();
            } else if (transaction.bodyBytes().length() > 0) {
                this.body = TransactionBody.PROTOBUF.parse(transaction.bodyBytes());
                this.numTxnSignatures = transaction.sigMapOrElse(SignatureMap.DEFAULT).sigPair().size();
            } else {
                throw new IllegalArgumentException("Transaction must contain body bytes or signed transaction bytes");
            }
        }

        private TransactionFeeContext(final Transaction transaction, final int numTxnSignatures, final TransactionBody body, @Nullable final FeeContext feeContext) {
            this.transaction = transaction;
            this.numTxnSignatures = numTxnSignatures;
            this.body = body;
            this.feeContext = feeContext;
        }

        TransactionFeeContext withFeeContext(@Nullable final FeeContext feeContext) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public int numTxnSignatures() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public int numTxnBytes() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        @Nullable
        public FeeContext feeContext() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        @Nullable
        public QueryContext queryContext() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public HederaFunctionality functionality() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public int getHighVolumeThrottleUtilization(final HederaFunctionality functionality) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        @NonNull
        public TransactionBody body() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
