package com.omgservers.dispatcher.entrypoint.impl.service.entrypointService.impl.method;

import com.omgservers.dispatcher.entrypoint.dto.OnCloseEntrypointRequest;
import com.omgservers.dispatcher.service.dispatcher.DispatcherService;
import com.omgservers.dispatcher.service.dispatcher.dto.HandleClosedConnectionRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class OnCloseMethodImpl implements OnCloseMethod {

    final DispatcherService dispatcherService;

    @Override
    public Uni<Void> execute(final OnCloseEntrypointRequest request) {
        final var webSocketConnection = request.getWebSocketConnection();
        final var closeReason = request.getCloseReason();

        final var handleClosedConnectionRequest = new HandleClosedConnectionRequest(webSocketConnection, closeReason);
        return dispatcherService.handleClosedConnection(handleClosedConnectionRequest)
                .invoke(voidItem -> log.info("The dispatcher connection \"{}\" was closed with the reason {}",
                        webSocketConnection.id(), closeReason))
                .replaceWithVoid();
    }
}
