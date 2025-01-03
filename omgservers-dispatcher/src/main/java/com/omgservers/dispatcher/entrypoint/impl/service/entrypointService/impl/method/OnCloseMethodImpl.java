package com.omgservers.dispatcher.entrypoint.impl.service.entrypointService.impl.method;

import com.omgservers.dispatcher.entrypoint.impl.dto.OnCloseEntrypointRequest;
import com.omgservers.dispatcher.module.DispatcherModule;
import com.omgservers.dispatcher.module.impl.dto.OnCloseRequest;
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
    public Uni<Void> execute(final OnCloseEntrypointRequest request) {
        final var webSocketConnection = request.getWebSocketConnection();
        final var closeReason = request.getCloseReason();

        final var onCloseRequest = new OnCloseRequest(webSocketConnection, closeReason);
        return dispatcherModule.getDispatcherService().execute(onCloseRequest)
                .replaceWithVoid();
    }
}
