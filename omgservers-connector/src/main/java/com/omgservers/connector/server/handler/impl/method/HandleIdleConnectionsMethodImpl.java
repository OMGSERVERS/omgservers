package com.omgservers.connector.server.handler.impl.method;

import com.omgservers.connector.configuration.ConnectorCloseReason;
import com.omgservers.connector.operation.GetConnectorConfigOperation;
import com.omgservers.connector.server.handler.component.ConnectorConnections;
import com.omgservers.connector.server.handler.dto.HandleIdleConnectionsRequest;
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

    final GetConnectorConfigOperation getConnectorConfigOperation;

    final ConnectorConnections connectorConnections;

    @Override
    public Uni<Void> execute(final HandleIdleConnectionsRequest request) {
        log.debug("{}", request);

        final var now = Instant.now();
        final var idleTimeout = getConnectorConfigOperation.getConnectorConfig().idleConnectionTimeout();

        return Multi.createFrom().iterable(connectorConnections.getAll())
                .onItem().transformToUniAndConcatenate(connectorConnection -> {
                    final var lastUsage = connectorConnection.getLastUsage();
                    final var idleInterval = Duration.between(lastUsage, now).toSeconds();

                    if (idleInterval > idleTimeout) {
                        final var webSocketConnection = connectorConnection.getWebSocketConnection();
                        if (webSocketConnection.isOpen()) {
                            return webSocketConnection.close(ConnectorCloseReason.INACTIVE_CONNECTION)
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
