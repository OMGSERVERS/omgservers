package com.omgservers.dispatcher.server.handler.impl.method;

import com.omgservers.dispatcher.operation.GetDispatcherConfigOperation;
import com.omgservers.dispatcher.server.handler.component.DispatcherCloseReason;
import com.omgservers.dispatcher.server.handler.dto.HandleIdleConnectionsRequest;
import com.omgservers.dispatcher.server.handler.impl.components.DispatcherConnections;
import com.omgservers.schema.model.user.UserRoleEnum;
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
class HandleIdleConnectionsMethodImpl implements HandleIdleConnectionsMethod {

    final GetDispatcherConfigOperation getDispatcherConfigOperation;

    final DispatcherConnections dispatcherConnections;

    @Override
    public Uni<Void> execute(final HandleIdleConnectionsRequest request) {
        log.debug("{}", request);

        final var now = Instant.now();
        final var idleTimeout = getDispatcherConfigOperation.getDispatcherConfig().idleConnectionTimeout();

        // Ignore runtime connections
        final var playerConnections = dispatcherConnections.getAll().stream()
                .filter(dispatcherConnection ->
                        dispatcherConnection.getUserRole().equals(UserRoleEnum.PLAYER))
                .toList();

        return Multi.createFrom().iterable(playerConnections)
                .onItem().transformToUniAndConcatenate(dispatcherConnection -> {
                    final var lastUsage = dispatcherConnection.getLastUsage();
                    final var idleInterval = Duration.between(lastUsage, now).toSeconds();

                    if (idleInterval > idleTimeout) {
                        final var webSocketConnection = dispatcherConnection.getWebSocketConnection();
                        if (webSocketConnection.isOpen()) {
                            return webSocketConnection.close(DispatcherCloseReason.INACTIVE_CONNECTION)
                                    .replaceWith(Boolean.TRUE);
                        }
                    }

                    return Uni.createFrom().item(Boolean.FALSE);
                })
                .collect().asList()
                .invoke(results -> {
                    final var closed = results.stream().filter(Boolean.TRUE::equals).count();
                    if (closed > 0) {
                        log.warn("{} idle connection/s closed, timeout=\"{}\"", closed, idleTimeout);
                    }
                })
                .replaceWithVoid();
    }
}
