package com.omgservers.dispatcher.service.handler.impl.method;

import com.omgservers.dispatcher.operation.GetDispatcherConfigOperation;
import com.omgservers.dispatcher.service.handler.component.DispatcherCloseReason;
import com.omgservers.dispatcher.service.handler.dto.HandleExpiredConnectionsRequest;
import com.omgservers.dispatcher.service.handler.impl.components.DispatcherConnections;
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

    final GetDispatcherConfigOperation getDispatcherConfigOperation;

    final DispatcherConnections dispatcherConnections;

    @Override
    public Uni<Void> execute(final HandleExpiredConnectionsRequest request) {
        log.trace("{}", request);

        final var now = Instant.now();

        return Multi.createFrom().iterable(dispatcherConnections.getAll())
                .onItem().transformToUniAndConcatenate(dispatcherConnection -> {
                    final var lastUsage = dispatcherConnection.getLastUsage();
                    final var idleInterval = Duration.between(lastUsage, now).toSeconds();

                    final var idleTimeout = getDispatcherConfigOperation.getDispatcherConfig().expiredConnectionsIdleTimeout();

                    if (idleInterval > idleTimeout) {
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
