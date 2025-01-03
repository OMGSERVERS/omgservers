package com.omgservers.dispatcher.module.impl.service.dispatcherService.impl.method;

import com.omgservers.dispatcher.module.impl.dto.OnTextMessageRequest;
import com.omgservers.dispatcher.service.handler.HandlerService;
import com.omgservers.dispatcher.service.handler.dto.HandleTextMessageRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class OnTextMessageMethodImpl implements OnTextMessageMethod {

    final HandlerService handlerService;

    @Override
    public Uni<Void> execute(final OnTextMessageRequest request) {
        final var webSocketConnection = request.getWebSocketConnection();
        final var message = request.getMessage();

        final var handleTextMessageRequest = new HandleTextMessageRequest(webSocketConnection, message);
        return handlerService.handleTextMessage(handleTextMessageRequest).replaceWithVoid();
    }
}
