package com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.entrypoint.dispatcher.dto.OnCloseDispatcherRequest;
import com.omgservers.service.module.dispatcher.DispatcherModule;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleClosedConnectionRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class OnCloseMethodImpl implements OnCloseMethod {

    final DispatcherModule dispatcherModule;

    @Override
    public Uni<Void> execute(final OnCloseDispatcherRequest request) {
        final var webSocketConnection = request.getWebSocketConnection();
        final var closeReason = request.getCloseReason();

        final var handleClosedConnectionRequest = new HandleClosedConnectionRequest(webSocketConnection,
                closeReason);
        return dispatcherModule.getDispatcherService().handleClosedConnection(handleClosedConnectionRequest)
                .invoke(voidItem -> log.info("Dispatcher connection {} was closed with reason {}",
                        webSocketConnection.id(), closeReason))
                .replaceWithVoid();
    }
}
