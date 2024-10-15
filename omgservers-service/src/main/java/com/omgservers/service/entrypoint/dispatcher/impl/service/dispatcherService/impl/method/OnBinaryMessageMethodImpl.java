package com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.entrypoint.dispatcher.dto.OnBinaryMessageDispatcherRequest;
import com.omgservers.service.module.dispatcher.DispatcherModule;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleBinaryMessageRequest;
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
    public Uni<Void> execute(final OnBinaryMessageDispatcherRequest request) {
        final var webSocketConnection = request.getWebSocketConnection();
        final var buffer = request.getBuffer();

        final var handleBinaryMessageRequest = new HandleBinaryMessageRequest(webSocketConnection,
                buffer);
        return dispatcherModule.getDispatcherService().handleBinaryMessage(handleBinaryMessageRequest)
                .replaceWithVoid();
    }
}
