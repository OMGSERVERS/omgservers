package com.omgservers.dispatcher.entrypoint.impl.service.entrypointService.impl.method;

import com.omgservers.dispatcher.entrypoint.impl.dto.OnBinaryMessageEntrypointRequest;
import com.omgservers.dispatcher.module.DispatcherModule;
import com.omgservers.dispatcher.module.impl.dto.OnBinaryMessageRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class OnBinaryMessageMethodImpl implements OnBinaryMessageMethod {

    final DispatcherModule dispatcherModule;

    @Override
    public Uni<Void> execute(final OnBinaryMessageEntrypointRequest request) {
        final var webSocketConnection = request.getWebSocketConnection();
        final var buffer = request.getBuffer();

        final var onBinaryMessageRequest = new OnBinaryMessageRequest(webSocketConnection, buffer);
        return dispatcherModule.getDispatcherService().execute(onBinaryMessageRequest);
    }
}
