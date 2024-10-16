package com.omgservers.service.entrypoint.dispatcher.impl.service.webService.impl;

import com.omgservers.service.entrypoint.dispatcher.dto.OnBinaryMessageDispatcherRequest;
import com.omgservers.service.entrypoint.dispatcher.dto.OnCloseDispatcherRequest;
import com.omgservers.service.entrypoint.dispatcher.dto.OnErrorDispatcherRequest;
import com.omgservers.service.entrypoint.dispatcher.dto.OnOpenDispatcherRequest;
import com.omgservers.service.entrypoint.dispatcher.dto.OnTextMessageDispatcherRequest;
import com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.DispatcherService;
import com.omgservers.service.entrypoint.dispatcher.impl.service.webService.WebService;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.websockets.next.CloseReason;
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

    final DispatcherService dispatcherService;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<Void> onOpen(WebSocketConnection webSocketConnection) {
        final var request = new OnOpenDispatcherRequest(webSocketConnection);
        return dispatcherService.onOpen(request);
    }

    @Override
    public Uni<Void> onClose(WebSocketConnection webSocketConnection, CloseReason closeReason) {
        final var request = new OnCloseDispatcherRequest(webSocketConnection, closeReason);
        return dispatcherService.onClose(request);
    }

    @Override
    public Uni<Void> onError(WebSocketConnection webSocketConnection, Throwable throwable) {
        final var request = new OnErrorDispatcherRequest(webSocketConnection, throwable);
        return dispatcherService.onError(request);
    }

    @Override
    public Uni<Void> onTextMessage(WebSocketConnection webSocketConnection, String message) {
        final var request = new OnTextMessageDispatcherRequest(webSocketConnection, message);
        return dispatcherService.onTextMessage(request);
    }

    @Override
    public Uni<Void> onBinaryMessage(WebSocketConnection webSocketConnection, Buffer buffer) {
        final var request = new OnBinaryMessageDispatcherRequest(webSocketConnection, buffer);
        return dispatcherService.onBinaryMessage(request);
    }
}
