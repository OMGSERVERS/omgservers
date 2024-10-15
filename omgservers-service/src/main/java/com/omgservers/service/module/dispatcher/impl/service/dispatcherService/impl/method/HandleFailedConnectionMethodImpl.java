package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleFailedConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleFailedConnectionResponse;
import com.omgservers.service.security.ServiceSecurityAttributesEnum;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleFailedConnectionMethodImpl implements HandleFailedConnectionMethod {

    final HandleClosedConnectionMethod handleClosedConnectionMethod;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<HandleFailedConnectionResponse> execute(final HandleFailedConnectionRequest request) {
        log.debug("Handle failed connection, request={}", request);

        final var userRole = securityIdentity
                .<UserRoleEnum>getAttribute(ServiceSecurityAttributesEnum.USER_ROLE.getAttributeName());
        final var runtimeId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributesEnum.RUNTIME_ID.getAttributeName());

        final var webSocketConnection = request.getWebSocketConnection();
        final var t = request.getThrowable();

        log.warn("Dispatcher connection failed, id={}, userRole={}, runtimeId={}, {}:{}",
                webSocketConnection.id(), userRole, runtimeId, t.getClass().getSimpleName(), t.getMessage());

        return Uni.createFrom().item(new HandleFailedConnectionResponse());
    }
}
