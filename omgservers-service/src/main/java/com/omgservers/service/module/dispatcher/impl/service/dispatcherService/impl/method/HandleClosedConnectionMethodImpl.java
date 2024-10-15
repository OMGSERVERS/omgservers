package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.module.dispatcher.DispatcherModule;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherCloseReason;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherConnectionsContainer;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleClosedConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleClosedConnectionResponse;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemoveConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemoveRoomRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.CloseClientConnectionRequest;
import com.omgservers.service.security.ServiceSecurityAttributesEnum;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.websockets.next.WebSocketConnection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleClosedConnectionMethodImpl implements HandleClosedConnectionMethod {

    final DispatcherModule dispatcherModule;

    final DispatcherConnectionsContainer dispatcherConnectionsContainer;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<HandleClosedConnectionResponse> execute(
            final HandleClosedConnectionRequest request) {
        log.debug("Handle closed connection, request={}", request);

        final var userRole = securityIdentity
                .<UserRoleEnum>getAttribute(ServiceSecurityAttributesEnum.USER_ROLE.getAttributeName());
        final var runtimeId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributesEnum.RUNTIME_ID.getAttributeName());

        final var webSocketConnection = request.getWebSocketConnection();

        final var webSocketType = dispatcherConnectionsContainer.getType(webSocketConnection);
        if (webSocketType.isPresent()) {
            return (switch (webSocketType.get()) {
                case ROUTED -> closeRoutedConnection(webSocketConnection)
                        .invoke(handleClosedConnectionResponse -> log.info("Routed connection was closed, " +
                                "id={}, userRole={}, runtimeId={}", webSocketConnection.id(), userRole, runtimeId));
                case SERVER -> removeRoomConnection(userRole, runtimeId, webSocketConnection)
                        .invoke(handleClosedConnectionResponse -> log.info("Dispatcher connection was closed, " +
                                "id={}, userRole={}, runtimeId={}", webSocketConnection.id(), userRole, runtimeId));
            });
        }

        return Uni.createFrom().item(new HandleClosedConnectionResponse());
    }

    Uni<HandleClosedConnectionResponse> closeRoutedConnection(final WebSocketConnection serverConnection) {
        final var request = new CloseClientConnectionRequest(serverConnection,
                DispatcherCloseReason.ROUTED_CONNECTION_CLOSED);
        return dispatcherModule.getRouterService().closeClientConnection(request)
                .replaceWith(new HandleClosedConnectionResponse());
    }

    Uni<HandleClosedConnectionResponse> removeRoomConnection(final UserRoleEnum userRole,
                                                             final Long runtimeId,
                                                             final WebSocketConnection serverConnection) {
        final Uni<HandleClosedConnectionResponse> uni;

        if (userRole.equals(UserRoleEnum.RUNTIME)) {
            final var request = new RemoveRoomRequest(runtimeId);
            uni = dispatcherModule.getRoomService().removeRoom(request)
                    .replaceWith(new HandleClosedConnectionResponse());
        } else {
            final var request = new RemoveConnectionRequest(serverConnection);
            uni = dispatcherModule.getRoomService().removeConnection(request)
                    .replaceWith(new HandleClosedConnectionResponse());
        }

        return uni;
    }

}
