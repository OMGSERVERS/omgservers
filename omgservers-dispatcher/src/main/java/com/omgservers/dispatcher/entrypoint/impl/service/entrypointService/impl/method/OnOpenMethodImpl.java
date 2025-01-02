package com.omgservers.dispatcher.entrypoint.impl.service.entrypointService.impl.method;

import com.omgservers.dispatcher.entrypoint.dto.OnOpenEntrypointRequest;
import com.omgservers.dispatcher.operation.GetSecurityAttributeOperation;
import com.omgservers.dispatcher.service.dispatcher.DispatcherService;
import com.omgservers.dispatcher.service.dispatcher.dto.HandleOpenedConnectionRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class OnOpenMethodImpl implements OnOpenMethod {

    final DispatcherService dispatcherService;

    final GetSecurityAttributeOperation getSecurityAttributeOperation;

    @Override
    public Uni<Void> execute(final OnOpenEntrypointRequest request) {
        final var webSocketConnection = request.getWebSocketConnection();

        final var runtimeId = getSecurityAttributeOperation.getRuntimeId();
        final var userRole = getSecurityAttributeOperation.getUserRole();
        final var subject = getSecurityAttributeOperation.getSubject();

        final var handleOpenedConnectionRequest = new HandleOpenedConnectionRequest(webSocketConnection,
                runtimeId, userRole, subject);
        return dispatcherService.handleOpenedConnection(handleOpenedConnectionRequest)
                .invoke(voidItem -> log.info("The dispatcher connection \"{}\" " +
                                "to the runtime dispatcher \"{}\" " +
                                "was opened by the \"{}\" with role {}",
                        webSocketConnection.id(), runtimeId, subject, userRole))
                .replaceWithVoid();
    }
}
