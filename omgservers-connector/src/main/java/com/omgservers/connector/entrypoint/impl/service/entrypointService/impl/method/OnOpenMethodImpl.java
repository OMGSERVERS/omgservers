package com.omgservers.connector.entrypoint.impl.service.entrypointService.impl.method;

import com.omgservers.connector.entrypoint.impl.dto.OnOpenEntrypointRequest;
import com.omgservers.connector.operation.GetSecurityAttributeOperation;
import com.omgservers.connector.server.handler.HandlerService;
import com.omgservers.connector.server.handler.dto.HandleOpenedConnectionRequest;
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
    public Uni<Void> execute(final OnOpenEntrypointRequest request) {
        final var webSocketConnection = request.getWebSocketConnection();

        final var userRole = getSecurityAttributeOperation.getUserRole();
        final var clientId = getSecurityAttributeOperation.getClientId();

        final var handleOpenedConnectionRequest = new HandleOpenedConnectionRequest(webSocketConnection,
                userRole,
                clientId);
        return handlerService.execute(handleOpenedConnectionRequest);
    }
}
