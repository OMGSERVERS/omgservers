package com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.entrypoint.dispatcher.dto.OnOpenDispatcherRequest;
import com.omgservers.service.module.dispatcher.DispatcherModule;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleOpenedConnectionRequest;
import com.omgservers.service.operation.getSecurityAttribute.GetSecurityAttributeOperation;
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

    final GetSecurityAttributeOperation getSecurityAttributeOperation;

    @Override
    public Uni<Void> execute(final OnOpenDispatcherRequest request) {
        final var webSocketConnection = request.getWebSocketConnection();

        final var runtimeId = getSecurityAttributeOperation.getRuntimeId();
        final var userRole = getSecurityAttributeOperation.getUserRole();
        final var subject = getSecurityAttributeOperation.getSubject();

        final var handleOpenedConnectionRequest = new HandleOpenedConnectionRequest(webSocketConnection,
                runtimeId, userRole, subject);
        return dispatcherModule.getDispatcherService().handleOpenedConnection(handleOpenedConnectionRequest)
                .invoke(voidItem -> log.info("Dispatcher connection {} to runtime {} was opened by {} with role {}",
                        webSocketConnection.id(), runtimeId, subject, userRole))
                .replaceWithVoid();
    }
}
