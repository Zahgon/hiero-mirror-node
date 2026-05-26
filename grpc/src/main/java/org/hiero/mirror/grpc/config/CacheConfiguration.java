// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.grpc.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Set;
import org.hiero.mirror.grpc.GrpcProperties;
import org.hiero.mirror.grpc.service.AddressBookProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration(proxyBeanMethods = false)
@EnableCaching
public class CacheConfiguration {

    public static final String ADDRESS_BOOK_ENTRY_CACHE = "addressBookEntryCache";

    public static final String NODE_STAKE_CACHE = "nodeStakeCache";

    public static final String ENTITY_CACHE = "entityCache";

    public static final String CACHE_NAME = "default";

    @Bean(ADDRESS_BOOK_ENTRY_CACHE)
    CacheManager addressBookEntryCache(AddressBookProperties addressBookProperties) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean(NODE_STAKE_CACHE)
    CacheManager nodeStakeCache(AddressBookProperties addressBookProperties) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean(ENTITY_CACHE)
    @Primary
    CacheManager entityCache(GrpcProperties grpcProperties) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
