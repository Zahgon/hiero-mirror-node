// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.common;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.hibernate.cfg.AvailableSettings;
import org.hiero.mirror.common.config.CommonRuntimeHints;
import org.hiero.mirror.common.converter.CustomJsonFormatMapper;
import org.hiero.mirror.common.domain.SystemEntity;
import org.hiero.mirror.common.util.DatabaseWaiter;
import org.hiero.mirror.common.util.SpelHelper;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.hibernate.autoconfigure.HibernatePropertiesCustomizer;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.jdbc.autoconfigure.JdbcConnectionDetails;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.context.annotation.Lazy;

@Configuration(proxyBeanMethods = false)
@ConfigurationPropertiesScan("org.hiero.mirror")
@EnableConfigurationProperties(CommonProperties.class)
@EntityScan("org.hiero.mirror.common.domain")
@ImportRuntimeHints(CommonRuntimeHints.class)
public final class CommonConfiguration {

    @Bean
    SystemEntity systemEntity(CommonProperties commonProperties) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    DatabaseWaiter dbWaiter(CommonProperties commonProperties) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean("spelHelper")
    SpelHelper spelHelper() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    HikariConfig hikariConfig() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    @ConditionalOnMissingBean(DataSource.class)
    @Lazy
    DataSource dataSource(DataSourceProperties dataSourceProperties, HikariConfig hikariConfig, DatabaseWaiter databaseWaiter, ObjectProvider<JdbcConnectionDetails> detailsProvider) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
