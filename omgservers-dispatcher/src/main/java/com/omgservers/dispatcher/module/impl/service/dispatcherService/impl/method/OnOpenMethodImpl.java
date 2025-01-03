package com.omgservers.dispatcher.module.impl.service.dispatcherService.impl.method;

import com.omgservers.dispatcher.module.impl.dto.OnOpenRequest;
import com.omgservers.dispatcher.operation.GetSecurityAttributeOperation;
import com.omgservers.dispatcher.service.handler.HandlerService;
import com.omgservers.dispatcher.service.handler.dto.HandleOpenedConnectionRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class OnOpenMethodImpl implements OnOpenMethod {

    final HandlerService handlerService;

    final GetSecurityAttributeOperation getSecurityAttributeOperation;

    @Override
    public Uni<Void> execute(final OnOpenRequest request) {
        final var webSocketConnection = request.getWebSocketConnection();

        final var runtimeId = request.getRuntimeId();
        final var userRole = request.getUserRole();
        final var subject = request.getSubject();

        final var handleOpenedConnectionRequest = new HandleOpenedConnectionRequest(webSocketConnection,
                runtimeId, userRole, subject);
        return handlerService.handleOpenedConnection(handleOpenedConnectionRequest)
                .replaceWithVoid();
    }
}
