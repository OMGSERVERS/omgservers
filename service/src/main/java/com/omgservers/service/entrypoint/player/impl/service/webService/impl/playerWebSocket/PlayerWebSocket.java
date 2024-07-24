package com.omgservers.service.entrypoint.player.impl.service.webService.impl.playerWebSocket;

import com.omgservers.model.message.MessageModel;
import com.omgservers.model.user.UserRoleEnum;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.websockets.next.HandshakeRequest;
import io.quarkus.websockets.next.OnClose;
import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;
import io.quarkus.websockets.next.WebSocketConnection;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RolesAllowed({UserRoleEnum.Names.PLAYER})
@WebSocket(path = "/omgservers/v1/entrypoint/player/websocket")
@AllArgsConstructor
public class PlayerWebSocket {

    final SecurityIdentity securityIdentity;

    @OnOpen
    public Uni<Void> onOpen(final HandshakeRequest handshakeRequest, final WebSocketConnection webSocketConnection) {
        log.info("WebSocketEndpoint connection is opened, query={}", handshakeRequest.query());
        return Uni.createFrom().voidItem();
    }

    @OnClose
    public Uni<Void> onClose() {
        log.info("WebSocketEndpoint connection is closed");
        return Uni.createFrom().voidItem();
    }

    @OnTextMessage
    public Uni<Void> onTextMessage(final MessageModel message) {
        log.info("WebSocketEndpoint text message was received, {}", message);
        return Uni.createFrom().voidItem();
    }
}
