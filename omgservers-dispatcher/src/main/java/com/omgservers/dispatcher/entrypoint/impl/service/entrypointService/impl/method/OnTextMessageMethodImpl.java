package com.omgservers.dispatcher.entrypoint.impl.service.entrypointService.impl.method;

import com.omgservers.dispatcher.entrypoint.impl.dto.OnTextMessageEntrypointRequest;
import com.omgservers.dispatcher.module.DispatcherModule;
import com.omgservers.dispatcher.module.impl.dto.OnTextMessageRequest;
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
    public Uni<Void> execute(final OnTextMessageEntrypointRequest request) {
        final var webSocketConnection = request.getWebSocketConnection();
        final var message = request.getMessage();

        final var onTextMessageRequest = new OnTextMessageRequest(webSocketConnection, message);
        return dispatcherModule.getDispatcherService().execute(onTextMessageRequest);
    }
}
