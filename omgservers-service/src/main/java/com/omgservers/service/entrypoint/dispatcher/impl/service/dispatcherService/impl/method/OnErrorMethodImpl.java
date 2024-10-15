package com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.entrypoint.dispatcher.dto.OnErrorDispatcherRequest;
import com.omgservers.service.module.dispatcher.DispatcherModule;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleFailedConnectionRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class OnErrorMethodImpl implements OnErrorMethod {

    final DispatcherModule dispatcherModule;

    @Override
    public Uni<Void> execute(final OnErrorDispatcherRequest request) {
        final var webSocketConnection = request.getWebSocketConnection();
        final var throwable = request.getThrowable();

        final var handleFailedConnectionRequest = new HandleFailedConnectionRequest(webSocketConnection,
                throwable);
        return dispatcherModule.getDispatcherService().handleFailedConnection(handleFailedConnectionRequest)
                .replaceWithVoid();
    }
}
