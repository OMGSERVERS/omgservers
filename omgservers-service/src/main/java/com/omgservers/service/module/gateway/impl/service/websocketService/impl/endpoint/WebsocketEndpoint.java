package com.omgservers.service.module.gateway.impl.service.websocketService.impl.endpoint;

import com.omgservers.service.module.gateway.impl.service.websocketService.WebsocketService;
import com.omgservers.service.module.gateway.impl.service.websocketService.request.CleanUpRequest;
import com.omgservers.service.module.gateway.impl.service.websocketService.request.ReceiveTextMessageRequest;
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
@ServerEndpoint("/omgservers/ws-gateway/v1/connection")
public class WebsocketEndpoint {

    final WebsocketService websocketService;

    @OnClose
    public void onClose(Session session) {
        final var request = new CleanUpRequest(session);
        websocketService.cleanUp(request);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        final var request = new CleanUpRequest(session);
        websocketService.cleanUp(request);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        final var request = new ReceiveTextMessageRequest(session, message);
        websocketService.receiveTextMessage(request);
    }
}
