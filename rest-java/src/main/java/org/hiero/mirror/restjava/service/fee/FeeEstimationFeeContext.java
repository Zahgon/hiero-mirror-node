// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.restjava.service.fee;

import static com.hedera.hapi.util.HapiUtils.functionOf;
import com.hedera.hapi.node.base.AccountID;
import com.hedera.hapi.node.base.ContractID;
import com.hedera.hapi.node.base.FileID;
import com.hedera.hapi.node.base.HederaFunctionality;
import com.hedera.hapi.node.base.NftID;
import com.hedera.hapi.node.base.TokenID;
import com.hedera.hapi.node.state.contract.Bytecode;
import com.hedera.hapi.node.state.contract.SlotKey;
import com.hedera.hapi.node.state.contract.SlotValue;
import com.hedera.hapi.node.state.file.File;
import com.hedera.hapi.node.state.token.Account;
import com.hedera.hapi.node.state.token.Nft;
import com.hedera.hapi.node.state.token.TokenRelation;
import com.hedera.hapi.node.transaction.ExchangeRate;
import com.hedera.hapi.node.transaction.TransactionBody;
import com.hedera.hapi.util.UnknownHederaFunctionality;
import com.hedera.node.app.authorization.AuthorizerImpl;
import com.hedera.node.app.authorization.PrivilegesVerifier;
import com.hedera.node.app.config.ConfigProviderImpl;
import com.hedera.node.app.service.consensus.ReadableTopicStore;
import com.hedera.node.app.service.contract.impl.state.ContractStateStore;
import com.hedera.node.app.service.file.FileMetadata;
import com.hedera.node.app.service.file.ReadableFileStore;
import com.hedera.node.app.service.token.ReadableAccountStore;
import com.hedera.node.app.service.token.ReadableNftStore;
import com.hedera.node.app.service.token.ReadableTokenRelationStore;
import com.hedera.node.app.service.token.ReadableTokenStore;
import com.hedera.node.app.spi.authorization.Authorizer;
import com.hedera.node.app.spi.fees.FeeCalculatorFactory;
import com.hedera.node.app.spi.fees.FeeContext;
import com.hedera.node.app.spi.fees.Fees;
import com.hedera.node.app.spi.fees.SimpleFeeCalculator;
import com.hedera.node.app.spi.store.ReadableStoreFactory;
import com.hedera.pbj.runtime.io.buffer.Bytes;
import com.swirlds.config.api.Configuration;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.common.CommonProperties;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@RequiredArgsConstructor
final class FeeEstimationFeeContext implements FeeContext {

    private static final ConfigProviderImpl CONFIG_PROVIDER = new ConfigProviderImpl(false, null, Map.of("hedera.shard", String.valueOf(CommonProperties.getInstance().getShard()), "hedera.realm", String.valueOf(CommonProperties.getInstance().getRealm())));

    static final Configuration CONFIGURATION = CONFIG_PROVIDER.getConfiguration();

    private static final Authorizer FEE_AUTHORIZER = new AuthorizerImpl(CONFIG_PROVIDER, new PrivilegesVerifier(CONFIG_PROVIDER));

    // Congestion multiplier reads these in STATE mode; return 0 so multiplier stays at 1x.
    // TODO: remove once CN fixes standalone executor to use null congestionMultipliers.
    private static final ReadableAccountStore EMPTY_ACCOUNT_STORE = new ReadableAccountStore() {

        @Override
        public Account getAccountById(final AccountID id) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Account getAliasedAccountById(final AccountID id) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public AccountID getAccountIDByAlias(final long shardNum, final long realmNum, final Bytes alias) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public boolean containsAlias(final long shardNum, final long realmNum, final Bytes alias) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public boolean contains(final AccountID id) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public long getNumberOfAccounts() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public long sizeOfAccountState() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };

    private static final ContractStateStore EMPTY_CONTRACT_STATE_STORE = new ContractStateStore() {

        @Override
        public Bytecode getBytecode(final ContractID contractID) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void putBytecode(final ContractID contractID, final Bytecode code) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void removeSlot(final SlotKey key) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void adjustSlotCount(final long delta) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void putSlot(final SlotKey key, final SlotValue value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Set<SlotKey> getModifiedSlotKeys() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public SlotValue getSlotValue(final SlotKey key) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public SlotValue getOriginalSlotValue(final SlotKey key) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public long getNumSlots() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public long getNumBytecodes() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };

    private static final ReadableFileStore EMPTY_FILE_STORE = new ReadableFileStore() {

        @Override
        public FileMetadata getFileMetadata(final FileID id) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public File getFileLeaf(final FileID id) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public long sizeOfState() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };

    private static final ReadableNftStore EMPTY_NFT_STORE = new ReadableNftStore() {

        @Override
        public Nft get(final NftID id) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public long sizeOfState() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };

    private static final ReadableTokenRelationStore EMPTY_TOKEN_RELATION_STORE = new ReadableTokenRelationStore() {

        @Override
        public TokenRelation get(final AccountID accountId, final TokenID tokenId) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public long sizeOfState() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };

    private final TransactionBody body;

    private final FeeTopicStore topicStore;

    private final FeeTokenStore tokenStore;

    private final int throttleUtilization;

    @Override
    @SuppressWarnings("unchecked")
    public <T> T readableStore(@NonNull final Class<T> storeInterface) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @NonNull
    public ReadableStoreFactory readableStoreFactory() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @NonNull
    public Configuration configuration() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @NonNull
    public AccountID payer() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @NonNull
    public TransactionBody body() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @NonNull
    public FeeCalculatorFactory feeCalculatorFactory() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @Nullable
    public SimpleFeeCalculator getSimpleFeeCalculator() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @NonNull
    public Authorizer authorizer() {
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
    @NonNull
    public Fees dispatchComputeFees(@NonNull final TransactionBody txBody, @NonNull final AccountID syntheticPayerId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @Nullable
    public ExchangeRate activeRate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public long getGasPriceInTinycents() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @NonNull
    public HederaFunctionality functionality() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int getHighVolumeThrottleUtilization(@NonNull final HederaFunctionality functionality) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
