// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.grpc.config;

import io.grpc.netty.NettyServerBuilder;
import java.util.concurrent.Executor;
import org.hiero.mirror.grpc.GrpcProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.server.ServerBuilderCustomizer;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration(proxyBeanMethods = false)
class GrpcConfiguration {

    @Bean
    @Qualifier("readOnly")
    TransactionOperations transactionOperationsReadOnly(PlatformTransactionManager transactionManager) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    ServerBuilderCustomizer<NettyServerBuilder> grpcServerConfigurer(GrpcProperties grpcProperties, Executor applicationTaskExecutor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
