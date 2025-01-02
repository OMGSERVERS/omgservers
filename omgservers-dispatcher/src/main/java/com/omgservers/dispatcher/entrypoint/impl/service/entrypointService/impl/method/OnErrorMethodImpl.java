package com.omgservers.dispatcher.entrypoint.impl.service.entrypointService.impl.method;

import com.omgservers.dispatcher.entrypoint.dto.OnErrorEntrypointRequest;
import com.omgservers.dispatcher.service.dispatcher.DispatcherService;
import com.omgservers.dispatcher.service.dispatcher.dto.HandleFailedConnectionRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class OnErrorMethodImpl implements OnErrorMethod {

    final DispatcherService dispatcherService;

    @Override
    public Uni<Void> execute(final OnErrorEntrypointRequest request) {
        final var webSocketConnection = request.getWebSocketConnection();
        final var throwable = request.getThrowable();

        final var handleFailedConnectionRequest = new HandleFailedConnectionRequest(webSocketConnection, throwable);
        return dispatcherService.handleFailedConnection(handleFailedConnectionRequest).replaceWithVoid();
    }
}
