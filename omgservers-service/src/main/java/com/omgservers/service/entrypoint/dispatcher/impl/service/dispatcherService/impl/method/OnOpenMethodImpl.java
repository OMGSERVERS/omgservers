package com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.entrypoint.dispatcher.dto.OnOpenDispatcherRequest;
import com.omgservers.service.module.dispatcher.DispatcherModule;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleOpenedConnectionRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class OnOpenMethodImpl implements OnOpenMethod {

    final DispatcherModule dispatcherModule;

    @Override
    public Uni<Void> execute(final OnOpenDispatcherRequest request) {
        final var webSocketConnection = request.getWebSocketConnection();

        final var handleOpenedConnectionRequest = new HandleOpenedConnectionRequest(webSocketConnection);
        return dispatcherModule.getDispatcherService().handleOpenedConnection(handleOpenedConnectionRequest)
                .replaceWithVoid();
    }
}
