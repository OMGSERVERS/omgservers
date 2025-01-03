package com.omgservers.dispatcher.entrypoint.impl.service.entrypointService.impl.method;

import com.omgservers.dispatcher.entrypoint.impl.dto.OnErrorEntrypointRequest;
import com.omgservers.dispatcher.module.DispatcherModule;
import com.omgservers.dispatcher.module.impl.dto.OnErrorRequest;
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
    public Uni<Void> execute(final OnErrorEntrypointRequest request) {
        final var webSocketConnection = request.getWebSocketConnection();
        final var throwable = request.getThrowable();

        final var onErrorRequest = new OnErrorRequest(webSocketConnection, throwable);
        return dispatcherModule.getDispatcherService().execute(onErrorRequest);
    }
}
