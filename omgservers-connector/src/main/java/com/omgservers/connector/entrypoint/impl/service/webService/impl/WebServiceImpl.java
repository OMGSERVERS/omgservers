package com.omgservers.connector.entrypoint.impl.service.webService.impl;

import com.omgservers.connector.entrypoint.impl.dto.OnBinaryMessageEntrypointRequest;
import com.omgservers.connector.entrypoint.impl.dto.OnCloseEntrypointRequest;
import com.omgservers.connector.entrypoint.impl.dto.OnErrorEntrypointRequest;
import com.omgservers.connector.entrypoint.impl.dto.OnOpenEntrypointRequest;
import com.omgservers.connector.entrypoint.impl.dto.OnTextMessageEntrypointRequest;
import com.omgservers.connector.entrypoint.impl.service.entrypointService.EntrypointService;
import com.omgservers.connector.entrypoint.impl.service.webService.WebService;
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

    final EntrypointService entrypointService;

    @Override
    public Uni<Void> onOpen(final WebSocketConnection webSocketConnection) {
        final var request = new OnOpenEntrypointRequest(webSocketConnection);
        return entrypointService.onOpen(request);
    }

    @Override
    public Uni<Void> onClose(final WebSocketConnection webSocketConnection,
                             final CloseReason closeReason) {
        final var request = new OnCloseEntrypointRequest(webSocketConnection, closeReason);
        return entrypointService.onClose(request);
    }

    @Override
    public Uni<Void> onError(final WebSocketConnection webSocketConnection,
                             final Throwable throwable) {
        final var request = new OnErrorEntrypointRequest(webSocketConnection, throwable);
        return entrypointService.onError(request);
    }

    @Override
    public Uni<Void> onTextMessage(final WebSocketConnection webSocketConnection,
                                   final String message) {
        final var request = new OnTextMessageEntrypointRequest(webSocketConnection, message);
        return entrypointService.onTextMessage(request);
    }

    @Override
    public Uni<Void> onBinaryMessage(final WebSocketConnection webSocketConnection,
                                     final Buffer buffer) {
        final var request = new OnBinaryMessageEntrypointRequest(webSocketConnection, buffer);
        return entrypointService.onBinaryMessage(request);
    }
}
