// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.common.util.DatabaseWaiter;
import org.hiero.mirror.importer.ImporterProperties;
import org.hiero.mirror.importer.db.DBProperties;
import org.hiero.mirror.importer.downloader.block.BlockProperties;
import org.hiero.mirror.importer.downloader.record.RecordDownloaderProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.flyway.autoconfigure.FlywayAutoConfiguration;
import org.springframework.boot.flyway.autoconfigure.FlywayConfigurationCustomizer;
import org.springframework.boot.flyway.autoconfigure.FlywayDataSource;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.jdbc.autoconfigure.JdbcConnectionDetails;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.resilience.annotation.EnableResilientMethods;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration(proxyBeanMethods = false)
@EnableAsync
@EnableResilientMethods
@EntityScan("org.hiero.mirror.importer.repository.upsert")
@CustomLog
@RequiredArgsConstructor
// Since this configuration creates FlywayConfigurationCustomizer
@AutoConfigureBefore(FlywayAutoConfiguration.class)
class ImporterConfiguration {

    private final BlockProperties blockProperties;

    private final ImporterProperties importerProperties;

    private final RecordDownloaderProperties recordDownloaderProperties;

    private final DatabaseWaiter dbWaiter;

    @Bean(defaultCandidate = false)
    @FlywayDataSource
    DataSource flywayDataSource(DBProperties dbProperties, DataSourceProperties dataSourceProperties, HikariConfig hikariConfig, ObjectProvider<JdbcConnectionDetails> detailsObjectProvider) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    FlywayConfigurationCustomizer flywayConfigurationCustomizer() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @PostConstruct
    void init() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(prefix = "spring.task.scheduling", name = "enabled", havingValue = "true", matchIfMissing = true)
    @EnableScheduling
    protected static class // This toggle exists only to disable scheduling for test execution and shouldn't be modified by operators
    SchedulingConfiguration {
    }
}
