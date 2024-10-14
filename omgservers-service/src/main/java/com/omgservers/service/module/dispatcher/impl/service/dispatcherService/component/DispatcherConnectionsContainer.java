package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component;

import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class DispatcherConnectionsContainer {

    final Map<WebSocketConnection, DispatcherConnectionTypeEnum> connections;

    public DispatcherConnectionsContainer() {
        connections = new ConcurrentHashMap<>();
    }

    public void put(final WebSocketConnection webSocketConnection,
                    final DispatcherConnectionTypeEnum type) {
        connections.put(webSocketConnection, type);
    }

    public Optional<DispatcherConnectionTypeEnum> getType(final WebSocketConnection webSocketConnection) {
        final var webSocketType = connections.get(webSocketConnection);
        return Optional.ofNullable(webSocketType);
    }

    public void remove(final WebSocketConnection webSocketConnection) {
        connections.remove(webSocketConnection);
    }
}
