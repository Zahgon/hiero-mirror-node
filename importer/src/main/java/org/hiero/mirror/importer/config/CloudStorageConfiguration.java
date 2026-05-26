// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.config;

import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hiero.mirror.common.CommonProperties;
import org.hiero.mirror.importer.downloader.CommonDownloaderProperties;
import org.hiero.mirror.importer.downloader.StreamSourceProperties;
import org.hiero.mirror.importer.downloader.block.BlockProperties;
import org.hiero.mirror.importer.downloader.provider.LocalStreamFileProperties;
import org.hiero.mirror.importer.downloader.provider.LocalStreamFileProvider;
import org.hiero.mirror.importer.downloader.provider.S3StreamFileProvider;
import org.hiero.mirror.importer.downloader.provider.StreamFileProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AnonymousCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.core.interceptor.Context;
import software.amazon.awssdk.core.interceptor.ExecutionAttributes;
import software.amazon.awssdk.core.interceptor.ExecutionInterceptor;
import software.amazon.awssdk.http.SdkHttpRequest;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;

@Configuration(proxyBeanMethods = false)
@CustomLog
@RequiredArgsConstructor
class CloudStorageConfiguration {

    private final BlockProperties blockProperties;

    private final CommonProperties commonProperties;

    private final CommonDownloaderProperties commonDownloaderProperties;

    private final LocalStreamFileProperties localProperties;

    private final MetricsExecutionInterceptor metricsExecutionInterceptor;

    @Bean
    List<StreamFileProvider> streamFileProviders() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private AwsCredentialsProvider awsCredentialsProvider(StreamSourceProperties sourceProperties) {
        var type = sourceProperties.getType();
        if (commonDownloaderProperties.isAnonymousCredentials()) {
            log.info("Setting up {} client using anonymous credentials", type);
            return AnonymousCredentialsProvider.create();
        } else if (sourceProperties.isStaticCredentials()) {
            log.info("Setting up {} client using provided access/secret key", type);
            var credentials = sourceProperties.getCredentials();
            return StaticCredentialsProvider.create(AwsBasicCredentials.create(credentials.getAccessKey(), credentials.getSecretKey()));
        }
        log.info("Setting up {} client using default credentials provider", type);
        return DefaultCredentialsProvider.builder().build();
    }

    private S3AsyncClient s3Client(StreamSourceProperties sourceProperties) {
        var httpClient = NettyNioAsyncHttpClient.builder().connectionTimeout(sourceProperties.getConnectionTimeout()).maxConcurrency(sourceProperties.getMaxConcurrency()).connectionMaxIdleTime(// https://github.com/aws/aws-sdk-java-v2/issues/1122
        Duration.ofSeconds(5)).build();
        if (sourceProperties.getUri() == null) {
            sourceProperties.setUri(URI.create(sourceProperties.getType().getEndpoint()));
        }
        return S3AsyncClient.builder().credentialsProvider(awsCredentialsProvider(sourceProperties)).endpointOverride(sourceProperties.getUri()).forcePathStyle(true).httpClient(httpClient).overrideConfiguration(overrideConfiguration(sourceProperties)).region(Region.of(sourceProperties.getRegion())).build();
    }

    private ClientOverrideConfiguration overrideConfiguration(StreamSourceProperties sourceProperties) {
        var projectId = sourceProperties.getProjectId();
        var builder = ClientOverrideConfiguration.builder().addExecutionInterceptor(metricsExecutionInterceptor);
        if (StringUtils.isNotBlank(projectId)) {
            builder.addExecutionInterceptor(new ExecutionInterceptor() {

                @Override
                public SdkHttpRequest modifyHttpRequest(Context.ModifyHttpRequest context, ExecutionAttributes executionAttributes) {
                    throw new UnsupportedOperationException("STUB: not implemented");
                }
            });
        }
        return builder.build();
    }
}
