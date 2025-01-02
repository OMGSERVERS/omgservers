package com.omgservers.dispatcher.entrypoint.impl.service.entrypointService.impl.method;

import com.omgservers.dispatcher.entrypoint.dto.OnTextMessageEntrypointRequest;
import com.omgservers.dispatcher.service.dispatcher.DispatcherService;
import com.omgservers.dispatcher.service.dispatcher.dto.HandleTextMessageRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class OnTextMessageMethodImpl implements OnTextMessageMethod {

    final DispatcherService dispatcherService;

    @Override
    public Uni<Void> execute(final OnTextMessageEntrypointRequest request) {
        final var webSocketConnection = request.getWebSocketConnection();
        final var message = request.getMessage();

        final var handleTextMessageRequest = new HandleTextMessageRequest(webSocketConnection, message);
        return dispatcherService.handleTextMessage(handleTextMessageRequest).replaceWithVoid();
    }
}
