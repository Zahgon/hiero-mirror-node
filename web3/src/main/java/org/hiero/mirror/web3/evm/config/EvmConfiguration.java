// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.evm.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.hedera.hapi.node.base.SemanticVersion;
import com.hedera.node.app.service.contract.impl.exec.gas.CustomGasCalculator;
import com.hedera.node.app.service.entityid.EntityIdFactory;
import com.hedera.node.app.service.entityid.impl.AppEntityIdFactory;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.web3.evm.properties.EvmProperties;
import org.hiero.mirror.web3.repository.properties.CacheProperties;
import org.hyperledger.besu.evm.gascalculator.GasCalculator;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration(proxyBeanMethods = false)
@EnableCaching
@RequiredArgsConstructor
public class EvmConfiguration {

    public static final String CACHE_MANAGER_CONTRACT = "contract";

    public static final String CACHE_MANAGER_CONTRACT_SLOTS = "contractSlots";

    public static final String CACHE_MANAGER_CONTRACT_STATE = "contractState";

    public static final String CACHE_MANAGER_ENTITY = "entity";

    public static final String CACHE_MANAGER_RECORD_FILE_LATEST = "recordFileLatest";

    public static final String CACHE_MANAGER_RECORD_FILE_EARLIEST = "recordFileEarliest";

    public static final String CACHE_MANAGER_RECORD_FILE_INDEX = "recordFileIndex";

    public static final String CACHE_MANAGER_RECORD_FILE_TIMESTAMP = "recordFileTimestamp";

    public static final String CACHE_MANAGER_SLOTS_PER_CONTRACT = "slotsPerContract";

    public static final String CACHE_MANAGER_SYSTEM_FILE = "systemFile";

    public static final String CACHE_MANAGER_EXCHANGE_RATES_SYSTEM_FILE = "exchangeRate";

    public static final String CACHE_MANAGER_SYSTEM_ACCOUNT = "systemAccount";

    public static final String CACHE_MANAGER_TOKEN = "token";

    public static final String CACHE_MANAGER_TOKEN_TYPE = "tokenType";

    public static final String CACHE_NAME = "default";

    public static final String CACHE_NAME_CONTRACT = "contract";

    public static final String CACHE_NAME_EVM_ADDRESS = "evmAddress";

    public static final String CACHE_NAME_ALIAS = "alias";

    public static final String CACHE_NAME_NFT = "nft";

    public static final String CACHE_NAME_NFT_ALLOWANCE = "nftAllowance";

    public static final String CACHE_NAME_RECORD_FILE_LATEST = "latest";

    public static final String CACHE_NAME_TOKEN = "token";

    public static final String CACHE_NAME_TOKEN_ACCOUNT = "tokenAccount";

    public static final String CACHE_NAME_TOKEN_ACCOUNT_COUNT = "tokenAccountCount";

    public static final String CACHE_NAME_TOKEN_ALLOWANCE = "tokenAllowance";

    public static final String CACHE_NAME_TOKEN_AIRDROP = "tokenAirdrop";

    public static final SemanticVersion EVM_VERSION_0_30 = new SemanticVersion(0, 30, 0, "", "");

    public static final SemanticVersion EVM_VERSION_0_34 = new SemanticVersion(0, 34, 0, "", "");

    public static final SemanticVersion EVM_VERSION_0_38 = new SemanticVersion(0, 38, 0, "", "");

    public static final SemanticVersion EVM_VERSION_0_46 = new SemanticVersion(0, 46, 0, "", "");

    public static final SemanticVersion EVM_VERSION_0_50 = new SemanticVersion(0, 50, 0, "", "");

    public static final SemanticVersion EVM_VERSION_0_51 = new SemanticVersion(0, 51, 0, "", "");

    public static final SemanticVersion EVM_VERSION_0_65 = new SemanticVersion(0, 65, 0, "", "");

    public static final SemanticVersion EVM_VERSION_0_66 = new SemanticVersion(0, 66, 0, "", "");

    public static final SemanticVersion EVM_VERSION_0_67 = new SemanticVersion(0, 67, 0, "", "");

    public static final SemanticVersion EVM_VERSION = EVM_VERSION_0_67;

    private final CacheProperties cacheProperties;

    private final EvmProperties evmProperties;

    @Bean(CACHE_MANAGER_CONTRACT)
    CacheManager cacheManagerContract() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean(CACHE_MANAGER_CONTRACT_SLOTS)
    CacheManager cacheManagerContractSlots() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean(CACHE_MANAGER_CONTRACT_STATE)
    CacheManager cacheManagerContractState() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean(CACHE_MANAGER_SYSTEM_ACCOUNT)
    CacheManager cacheManagerSystemAccount() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean(CACHE_MANAGER_ENTITY)
    CacheManager cacheManagerEntity() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean(CACHE_MANAGER_SLOTS_PER_CONTRACT)
    CaffeineCacheManager cacheManagerSlotsPerContract() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean(CACHE_MANAGER_TOKEN)
    CacheManager cacheManagerToken() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean(CACHE_MANAGER_TOKEN_TYPE)
    CacheManager cacheManagerTokenType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean(CACHE_MANAGER_SYSTEM_FILE)
    CacheManager cacheManagerSystemFile() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean(CACHE_MANAGER_EXCHANGE_RATES_SYSTEM_FILE)
    CacheManager cacheManagerSystemFileExchangeRates() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean(CACHE_MANAGER_RECORD_FILE_INDEX)
    @Primary
    CacheManager cacheManagerRecordFileIndex() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean(CACHE_MANAGER_RECORD_FILE_TIMESTAMP)
    CacheManager cacheManagerRecordFileTimestamp() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean(CACHE_MANAGER_RECORD_FILE_LATEST)
    CacheManager cacheManagerRecordFileLatest() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean(CACHE_MANAGER_RECORD_FILE_EARLIEST)
    CacheManager cacheManagerRecordFileEarliest() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    public GasCalculator provideGasCalculator() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    public EntityIdFactory entityIdFactory() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
