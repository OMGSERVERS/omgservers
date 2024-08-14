package com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.component;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideInternalException;
import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class WebSocketConnectionsContainer {

    final Map<WebSocketConnection, WebSocketConnectionTypeEnum> connections;

    public WebSocketConnectionsContainer() {
        connections = new ConcurrentHashMap<>();
    }

    public void put(final WebSocketConnection webSocketConnection,
                    final WebSocketConnectionTypeEnum type) {
        connections.put(webSocketConnection, type);
    }

    public Optional<WebSocketConnectionTypeEnum> getType(final WebSocketConnection webSocketConnection) {
        final var webSocketType = connections.get(webSocketConnection);
        return Optional.ofNullable(webSocketType);
    }

    public void remove(final WebSocketConnection webSocketConnection) {
        connections.remove(webSocketConnection);
    }
}
