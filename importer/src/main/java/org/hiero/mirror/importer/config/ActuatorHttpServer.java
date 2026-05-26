// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import jakarta.inject.Named;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.function.Function;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.health.actuate.endpoint.HttpCodeStatusMapper;
import org.springframework.boot.health.contributor.Status;

@CustomLog
@Named
@RequiredArgsConstructor
@ConditionalOnMissingClass({ "reactor.netty.http.server.HttpServer", "org.apache.catalina.startup.Tomcat" })
final class ActuatorHttpServer implements InitializingBean, DisposableBean {

    private static final String CONTENT_TYPE = "Content-Type";

    private static final String APPLICATION_JSON = "application/json";

    private static final String TEXT_PLAIN_PROMETHEUS = "text/plain; version=0.0.4; charset=utf-8";

    private final Function<String, Status> healthResolver;

    private final PrometheusMeterRegistry prometheusMeterRegistry;

    private final ObjectMapper objectMapper;

    @Value("${server.port:8080}")
    private int port;

    private HttpServer server;

    // Returns the actual bound port (useful when port=0 is used in tests)
    int getPort() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void setPort(int port) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void afterPropertiesSet() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void handleHealth(HttpExchange exchange, String group) throws IOException {
        try (exchange) {
            if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }
            final var resolved = healthResolver.apply(group);
            final var status = resolved != null ? resolved : Status.DOWN;
            final int httpStatus = HttpCodeStatusMapper.DEFAULT.getStatusCode(status);
            final var body = objectMapper.writeValueAsBytes(Map.of("status", status.getCode()));
            exchange.getResponseHeaders().set(CONTENT_TYPE, APPLICATION_JSON);
            exchange.sendResponseHeaders(httpStatus, body.length);
            exchange.getResponseBody().write(body);
        }
    }

    private void handlePrometheus(HttpExchange exchange) throws IOException {
        try (exchange) {
            if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }
            final var body = prometheusMeterRegistry.scrape().getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set(CONTENT_TYPE, TEXT_PLAIN_PROMETHEUS);
            exchange.sendResponseHeaders(200, body.length);
            exchange.getResponseBody().write(body);
        }
    }
}
