package com.omgservers.service.entrypoint.webSocket.impl.service.webService.impl;

import com.omgservers.service.entrypoint.webSocket.impl.service.webService.WebService;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.WebSocketService;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.AddConnectionWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleBinaryMessageWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleTextMessageWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.RemoveConnectionWebSocketRequest;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.websockets.next.WebSocketConnection;
import io.smallrye.mutiny.Uni;
import io.vertx.core.buffer.Buffer;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final WebSocketService webSocketService;

    @Override
    public Uni<Void> addConnection(final SecurityIdentity securityIdentity,
                                   final WebSocketConnection webSocketConnection) {
        final var request = new AddConnectionWebSocketRequest(securityIdentity, webSocketConnection);
        return webSocketService.addConnection(request)
                .replaceWithVoid();
    }

    @Override
    public Uni<Void> removeConnection(final SecurityIdentity securityIdentity,
                                      final WebSocketConnection webSocketConnection) {
        final var request = new RemoveConnectionWebSocketRequest(securityIdentity, webSocketConnection);
        return webSocketService.removeConnection(request)
                .replaceWithVoid();
    }

    @Override
    public Uni<Void> handleTextMessage(final SecurityIdentity securityIdentity,
                                       final WebSocketConnection webSocketConnection,
                                       final String message) {
        final var request = new HandleTextMessageWebSocketRequest(securityIdentity, webSocketConnection, message);
        return webSocketService.handleTextMessage(request)
                .replaceWithVoid();
    }

    @Override
    public Uni<Void> handleBinaryMessage(final SecurityIdentity securityIdentity,
                                         final WebSocketConnection webSocketConnection,
                                         final Buffer message) {
        final var request = new HandleBinaryMessageWebSocketRequest(securityIdentity, webSocketConnection, message);
        return webSocketService.handleBinaryMessage(request)
                .replaceWithVoid();
    }
}
