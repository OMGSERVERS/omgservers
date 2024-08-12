package com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.component;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideInternalException;
import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;
import java.util.Objects;
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

    public WebSocketConnectionTypeEnum getType(final WebSocketConnection webSocketConnection) {
        final var webSocketType = connections.get(webSocketConnection);
        if (Objects.isNull(webSocketType)) {
            throw new ServerSideInternalException(ExceptionQualifierEnum.INTERNAL_EXCEPTION_OCCURRED,
                    "type of websocket was not found");
        }

        return webSocketType;
    }

    public void remove(final WebSocketConnection webSocketConnection) {
        connections.remove(webSocketConnection);
    }
}
