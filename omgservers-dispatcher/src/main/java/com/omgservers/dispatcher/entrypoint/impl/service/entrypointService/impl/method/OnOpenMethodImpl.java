package com.omgservers.dispatcher.entrypoint.impl.service.entrypointService.impl.method;

import com.omgservers.dispatcher.entrypoint.impl.dto.OnOpenEntrypointRequest;
import com.omgservers.dispatcher.operation.GetSecurityAttributeOperation;
import com.omgservers.dispatcher.server.handler.HandlerService;
import com.omgservers.dispatcher.server.handler.dto.HandleOpenedConnectionRequest;
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

        final var runtimeId = getSecurityAttributeOperation.getRuntimeId();
        final var userRole = getSecurityAttributeOperation.getUserRole();
        final var subject = getSecurityAttributeOperation.<Long>getSubject();

        final var handleOpenedConnectionRequest = new HandleOpenedConnectionRequest(webSocketConnection,
                runtimeId,
                userRole,
                subject);
        return handlerService.execute(handleOpenedConnectionRequest);
    }
}
