package com.omgservers.dispatcher.entrypoint.impl.service.entrypointService.impl.method;

import com.omgservers.dispatcher.entrypoint.impl.dto.OnBinaryMessageEntrypointRequest;
import com.omgservers.dispatcher.service.handler.HandlerService;
import com.omgservers.dispatcher.service.handler.dto.HandleBinaryMessageRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class OnBinaryMessageMethodImpl implements OnBinaryMessageMethod {

    final HandlerService handlerService;

    @Override
    public Uni<Void> execute(final OnBinaryMessageEntrypointRequest request) {
        final var webSocketConnection = request.getWebSocketConnection();
        final var buffer = request.getBuffer();

        final var handleBinaryMessageRequest = new HandleBinaryMessageRequest(webSocketConnection, buffer);
        return handlerService.execute(handleBinaryMessageRequest);
    }
}
