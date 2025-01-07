package com.omgservers.dispatcher.entrypoint.impl.service.entrypointService.impl.method;

import com.omgservers.dispatcher.entrypoint.impl.dto.OnOpenEntrypointRequest;
import com.omgservers.dispatcher.module.DispatcherModule;
import com.omgservers.dispatcher.module.impl.dto.OnOpenRequest;
import com.omgservers.dispatcher.operation.GetSecurityAttributeOperation;
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
    public Uni<Void> execute(final OnOpenEntrypointRequest request) {
        final var webSocketConnection = request.getWebSocketConnection();

        final var runtimeId = getSecurityAttributeOperation.getRuntimeId();
        final var userRole = getSecurityAttributeOperation.getUserRole();
        final var subject = getSecurityAttributeOperation.<Long>getSubject();

        final var onOpenRequest = new OnOpenRequest(webSocketConnection, runtimeId, userRole, subject);
        return dispatcherModule.getDispatcherService().execute(onOpenRequest);
    }
}
