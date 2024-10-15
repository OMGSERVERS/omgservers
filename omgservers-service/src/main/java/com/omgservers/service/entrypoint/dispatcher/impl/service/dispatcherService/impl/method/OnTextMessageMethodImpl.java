package com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.entrypoint.dispatcher.dto.OnTextMessageDispatcherRequest;
import com.omgservers.service.module.dispatcher.DispatcherModule;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleTextMessageRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class OnTextMessageMethodImpl implements OnTextMessageMethod {

    final DispatcherModule dispatcherModule;

    @Override
    public Uni<Void> execute(final OnTextMessageDispatcherRequest request) {
        final var webSocketConnection = request.getWebSocketConnection();
        final var message = request.getMessage();

        final var handleTextMessageRequest = new HandleTextMessageRequest(webSocketConnection,
                message);
        return dispatcherModule.getDispatcherService().handleTextMessage(handleTextMessageRequest)
                .replaceWithVoid();
    }
}
