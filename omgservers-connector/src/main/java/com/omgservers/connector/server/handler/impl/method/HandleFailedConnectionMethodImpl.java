package com.omgservers.connector.server.handler.impl.method;

import com.omgservers.connector.server.handler.component.ConnectorConnections;
import com.omgservers.connector.server.handler.dto.HandleFailedConnectionRequest;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpClosedException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleFailedConnectionMethodImpl implements HandleFailedConnectionMethod {

    final ConnectorConnections connectorConnections;

    @Override
    public Uni<Void> execute(final HandleFailedConnectionRequest request) {
        log.debug("{}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var t = request.getThrowable();

        if (!t.getClass().equals(HttpClosedException.class)) {
            final var connectorConnection = connectorConnections.get(webSocketConnection);
            if (Objects.nonNull(connectorConnection)) {
                log.warn("Websocket \"{}\" failed, {}, {}:{}",
                        webSocketConnection.id(),
                        connectorConnection,
                        t.getClass().getSimpleName(),
                        t.getMessage());
            } else {
                log.error("Websocket \"{}\" failed, {}:{}",
                        webSocketConnection.id(),
                        t.getClass().getSimpleName(),
                        t.getMessage());
            }
        }

        return Uni.createFrom().voidItem();
    }
}
