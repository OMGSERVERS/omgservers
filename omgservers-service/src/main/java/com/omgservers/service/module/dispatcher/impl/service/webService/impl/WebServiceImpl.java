package com.omgservers.service.module.dispatcher.impl.service.webService.impl;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.DispatcherService;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleBinaryMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleClosedConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleFailedConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleOpenedConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleTextMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.webService.WebService;
import io.opentelemetry.instrumentation.annotations.WithSpan;
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

    @WithSpan
    @Override
    public Uni<Void> onOpen(final WebSocketConnection webSocketConnection) {
        final var request = new HandleOpenedConnectionRequest(webSocketConnection);
        return dispatcherService.handleOpenedConnection(request)
                .replaceWithVoid();
    }

    @WithSpan
    @Override
    public Uni<Void> onClose(final WebSocketConnection webSocketConnection,
                             final CloseReason closeReason) {
        final var request = new HandleClosedConnectionRequest(webSocketConnection, closeReason);
        return dispatcherService.handleClosedConnection(request)
                .replaceWithVoid();
    }

    @WithSpan
    @Override
    public Uni<Void> onError(final WebSocketConnection webSocketConnection,
                             final Throwable throwable) {
        final var request = new HandleFailedConnectionRequest(webSocketConnection, throwable);
        return dispatcherService.handleFailedConnection(request)
                .replaceWithVoid();
    }

    @WithSpan
    @Override
    public Uni<Void> onTextMessage(final WebSocketConnection webSocketConnection,
                                   final String message) {
        final var request = new HandleTextMessageRequest(webSocketConnection, message);
        return dispatcherService.handleTextMessage(request)
                .replaceWithVoid();
    }

    @WithSpan
    @Override
    public Uni<Void> onBinaryMessage(final WebSocketConnection webSocketConnection,
                                     final Buffer buffer) {
        final var request = new HandleBinaryMessageRequest(webSocketConnection, buffer);
        return dispatcherService.handleBinaryMessage(request)
                .replaceWithVoid();
    }
}
