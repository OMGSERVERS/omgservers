package com.omgservers.application.module.gatewayModule.impl.service.websocketEndpointService.impl.endpoint;

import com.omgservers.application.module.gatewayModule.impl.service.websocketEndpointService.WebsocketEndpointService;
import com.omgservers.application.module.gatewayModule.impl.service.websocketEndpointService.request.CreateConnectionHelpRequest;
import com.omgservers.application.module.gatewayModule.impl.service.websocketEndpointService.request.DeleteConnectionHelpRequest;
import com.omgservers.application.module.gatewayModule.impl.service.websocketEndpointService.request.ReceiveTextMessageHelpRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
@ServerEndpoint("/omgservers/gateway")
public class WebsocketEndpoint {

    final WebsocketEndpointService websocketEndpointService;

    @OnOpen
    public void onOpen(Session session) {
        final var request = new CreateConnectionHelpRequest(session);
        websocketEndpointService.createConnection(request);
        log.info("Session was opened, sessionId={}", session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        final var request = new DeleteConnectionHelpRequest(session);
        websocketEndpointService.deleteConnection(request);
        log.info("Session was closed, sessionId={}", session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        final var request = new DeleteConnectionHelpRequest(session);
        websocketEndpointService.deleteConnection(request);
        log.info("Session failed, sessionId={}, {}", session.getId(), throwable.getMessage());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        final var request = new ReceiveTextMessageHelpRequest(session, message);
        websocketEndpointService.receiveTextMessage(request);
    }
}
