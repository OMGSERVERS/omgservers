package com.omgservers.dispatcher.module.impl.service.dispatcherService.impl.method;

import com.omgservers.dispatcher.module.impl.dto.OnCloseRequest;
import com.omgservers.dispatcher.service.handler.HandlerService;
import com.omgservers.dispatcher.service.handler.dto.HandleClosedConnectionRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class OnCloseMethodImpl implements OnCloseMethod {

    final HandlerService handlerService;

    @Override
    public Uni<Void> execute(final OnCloseRequest request) {
        final var webSocketConnection = request.getWebSocketConnection();
        final var closeReason = request.getCloseReason();

        final var handleClosedConnectionRequest = new HandleClosedConnectionRequest(webSocketConnection, closeReason);
        return handlerService.handleClosedConnection(handleClosedConnectionRequest)
                .replaceWithVoid();
    }
}
