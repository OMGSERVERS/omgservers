package com.omgservers.service.entrypoint.webSocket.impl.service.webService.impl.webSocket;

import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.entrypoint.webSocket.impl.service.webService.WebService;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.websockets.next.OnBinaryMessage;
import io.quarkus.websockets.next.OnClose;
import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;
import io.quarkus.websockets.next.WebSocketConnection;
import io.smallrye.mutiny.Uni;
import io.vertx.core.buffer.Buffer;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RolesAllowed({UserRoleEnum.Names.WEBSOCKET})
@WebSocket(path = "/omgservers/v1/entrypoint/websocket/connection")
public class WebSocketEndpoint {

    final SecurityIdentity securityIdentity;
    final WebService webService;

    @OnOpen
    public Uni<Void> onOpen(final WebSocketConnection webSocketConnection) {
        return webService.addConnection(securityIdentity, webSocketConnection);
    }

    @OnClose
    public Uni<Void> onClose(final WebSocketConnection webSocketConnection) {
        return webService.removeConnection(securityIdentity, webSocketConnection);
    }

    @OnTextMessage
    public Uni<Void> onTextMessage(final WebSocketConnection webSocketConnection,
                                   final String message) {
        return webService.handleTextMessage(securityIdentity, webSocketConnection, message);
    }

    @OnBinaryMessage
    public Uni<Void> onBinaryMessage(final WebSocketConnection webSocketConnection,
                                     final Buffer message) {
        return webService.handleBinaryMessage(securityIdentity, webSocketConnection, message);
    }
}
