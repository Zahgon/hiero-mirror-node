// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.config;

import static org.hibernate.cfg.JdbcSettings.STATEMENT_INSPECTOR;
import java.util.Map;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.hiero.mirror.web3.Web3Properties;
import org.hiero.mirror.web3.common.ContractCallContext;
import org.springframework.boot.hibernate.autoconfigure.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.QueryTimeoutException;

@CustomLog
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
class HibernateConfiguration implements HibernatePropertiesCustomizer {

    private final Web3Properties web3Properties;

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    StatementInspector statementInspector() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
