// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.grpc.config;

import jakarta.inject.Named;
import java.util.concurrent.atomic.AtomicReference;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.boot.health.contributor.Status;
import org.springframework.context.event.EventListener;
import org.springframework.grpc.server.lifecycle.GrpcServerShutdownEvent;
import org.springframework.grpc.server.lifecycle.GrpcServerStartedEvent;
import org.springframework.grpc.server.lifecycle.GrpcServerTerminatedEvent;

@CustomLog
@Named
@RequiredArgsConstructor
public class GrpcHealthIndicator implements HealthIndicator {

    private final AtomicReference<Status> status = new AtomicReference<>(Status.UNKNOWN);

    @Override
    public Health health() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @EventListener
    public void onStart(GrpcServerStartedEvent event) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @EventListener
    public void onStop(GrpcServerShutdownEvent event) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @EventListener
    public void onTermination(GrpcServerTerminatedEvent event) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
