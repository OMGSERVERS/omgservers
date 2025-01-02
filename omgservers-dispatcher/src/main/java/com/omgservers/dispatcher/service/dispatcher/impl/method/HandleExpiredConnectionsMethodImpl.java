package com.omgservers.dispatcher.service.dispatcher.impl.method;

import com.omgservers.dispatcher.operation.getDispatcherConfig.GetDispatcherConfigOperation;
import com.omgservers.dispatcher.service.dispatcher.component.DispatcherCloseReason;
import com.omgservers.dispatcher.service.dispatcher.dto.HandleExpiredConnectionsRequest;
import com.omgservers.dispatcher.service.dispatcher.impl.components.DispatcherConnections;
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
        log.trace("Handle expired connections, request={}", request);

        final var now = Instant.now();

        return Multi.createFrom().iterable(dispatcherConnections.getAll())
                .onItem().transformToUniAndConcatenate(dispatcherConnection -> {
                    final var lastUsage = dispatcherConnection.getLastUsage();
                    final var idleInterval = Duration.between(lastUsage, now).toSeconds();

                    final var idleTimeout = getDispatcherConfigOperation.getDispatcherConfig().idleTimeout();

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
