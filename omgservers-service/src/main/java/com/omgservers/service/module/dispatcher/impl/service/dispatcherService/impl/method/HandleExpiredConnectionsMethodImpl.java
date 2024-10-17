package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherCloseReason;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleExpiredConnectionsRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.components.DispatcherConnections;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleExpiredConnectionsMethodImpl implements HandleExpiredConnectionsMethod {

    final GetConfigOperation getConfigOperation;

    final DispatcherConnections dispatcherConnections;

    @Override
    public Uni<Void> execute(final HandleExpiredConnectionsRequest request) {
        log.trace("Handle expired connections, request={}", request);

        final var now = Instant.now();

        return Multi.createFrom().iterable(dispatcherConnections.getAll())
                .onItem().transformToUniAndConcatenate(dispatcherConnection -> {
                    final var lastUsage = dispatcherConnection.getLastUsage();
                    final var idleInterval = Duration.between(lastUsage, now).toSeconds();

                    if (idleInterval > getConfigOperation.getServiceConfig().dispatcher().idleTimeout()) {
                        final var webSocketConnection = dispatcherConnection
                                .getWebSocketConnection();
                        if (webSocketConnection.isOpen()) {
                            return webSocketConnection.close(DispatcherCloseReason.IDLE_TIMED_OUT)
                                    .replaceWith(Boolean.TRUE);
                        }
                    }

                    return Uni.createFrom().item(Boolean.FALSE);
                })
                .collect().asList()
                .invoke(results -> {
                    final var closed = results.stream().filter(Boolean.TRUE::equals).count();
                    if (closed > 0) {
                        log.warn("{} expired dispatcher connections were closed", closed);
                    }
                })
                .replaceWithVoid();
    }
}
