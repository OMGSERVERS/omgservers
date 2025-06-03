package com.omgservers.connector.entrypoint.impl.service.entrypointService.impl.method;

import com.omgservers.connector.entrypoint.impl.dto.OnErrorEntrypointRequest;
import com.omgservers.connector.server.handler.HandlerService;
import com.omgservers.connector.server.handler.dto.HandleFailedConnectionRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class OnErrorMethodImpl implements OnErrorMethod {

    final HandlerService handlerService;

    @Override
    public Uni<Void> execute(final OnErrorEntrypointRequest request) {
        final var webSocketConnection = request.getWebSocketConnection();
        final var throwable = request.getThrowable();

        final var handleFailedConnectionRequest = new HandleFailedConnectionRequest(webSocketConnection, throwable);
        return handlerService.execute(handleFailedConnectionRequest);
    }
}
