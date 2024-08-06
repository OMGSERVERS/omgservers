package com.omgservers.service.entrypoint.webSocket.impl.service.webService;

import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.websockets.next.WebSocketConnection;
import io.smallrye.mutiny.Uni;
import io.vertx.core.buffer.Buffer;

public interface WebService {

    Uni<Void> addConnection(SecurityIdentity securityIdentity, WebSocketConnection webSocketConnection);

    Uni<Void> removeConnection(SecurityIdentity securityIdentity, WebSocketConnection webSocketConnection);

    Uni<Void> handleTextMessage(SecurityIdentity securityIdentity, WebSocketConnection webSocketConnection, String message);

    Uni<Void> handleBinaryMessage(SecurityIdentity securityIdentity, WebSocketConnection webSocketConnection, Buffer message);
}
