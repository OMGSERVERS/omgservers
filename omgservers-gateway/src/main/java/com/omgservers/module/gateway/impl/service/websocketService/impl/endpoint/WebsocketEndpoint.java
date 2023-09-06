package com.omgservers.module.gateway.impl.service.websocketService.impl.endpoint;

import com.omgservers.module.gateway.impl.service.websocketService.WebsocketService;
import com.omgservers.module.gateway.impl.service.websocketService.request.CleanUpRequest;
import com.omgservers.module.gateway.impl.service.websocketService.request.ReceiveTextMessageRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
@ServerEndpoint("/omgservers/gateway")
public class WebsocketEndpoint {

    final WebsocketService websocketService;

    @OnClose
    public void onClose(Session session) {
        log.info("Session was closed, sessionId={}", session.getId());
        final var request = new CleanUpRequest(session);
        websocketService.cleanUp(request);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.info("Session failed, sessionId={}, {}", session.getId(), throwable.getMessage());
        final var request = new CleanUpRequest(session);
        websocketService.cleanUp(request);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("Incoming message, sessionId={}, message={}", session.getId(), message);
        final var request = new ReceiveTextMessageRequest(session, message);
        websocketService.receiveTextMessage(request);
    }
}
