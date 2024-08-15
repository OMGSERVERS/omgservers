package com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl.method.addConnection;

import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.component.WebSocketConnectionTypeEnum;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.component.WebSocketConnectionsContainer;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.AddConnectionWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.AddConnectionWebSocketResponse;
import com.omgservers.service.operation.calculateShard.CalculateShardOperation;
import com.omgservers.service.security.ServiceSecurityAttributes;
import com.omgservers.service.service.room.RoomService;
import com.omgservers.service.service.room.dto.AddConnectionRequest;
import com.omgservers.service.service.router.RouterService;
import com.omgservers.service.service.router.dto.RouteServerConnectionRequest;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.websockets.next.WebSocketConnection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AddConnectionMethodImpl implements AddConnectionMethod {

    final RouterService routerService;
    final RoomService roomService;

    final CalculateShardOperation calculateShardOperation;

    final WebSocketConnectionsContainer webSocketConnectionsContainer;

    @Override
    public Uni<AddConnectionWebSocketResponse> addConnection(final AddConnectionWebSocketRequest request) {
        log.debug("Add connection, request={}", request);

        final var securityIdentity = request.getSecurityIdentity();
        final var runtimeId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributes.RUNTIME_ID.getAttributeName());

        return calculateShardOperation.calculateShard(runtimeId.toString())
                .flatMap(shardModel -> {
                    final var webSocketConnection = request.getWebSocketConnection();
                    if (shardModel.foreign()) {
                        final var serverUri = shardModel.serverUri();
                        return routeConnection(securityIdentity, webSocketConnection, serverUri)
                                .invoke(response -> webSocketConnectionsContainer.put(webSocketConnection,
                                        WebSocketConnectionTypeEnum.ROUTED));
                    } else {
                        return addConnection(securityIdentity, webSocketConnection, runtimeId)
                                .invoke(response -> webSocketConnectionsContainer.put(webSocketConnection,
                                        WebSocketConnectionTypeEnum.SERVER));
                    }
                });
    }

    Uni<AddConnectionWebSocketResponse> routeConnection(final SecurityIdentity securityIdentity,
                                                        final WebSocketConnection webSocketConnection,
                                                        final URI serverUri) {
        final var request = new RouteServerConnectionRequest(securityIdentity, webSocketConnection, serverUri);
        return routerService.routeServerConnection(request)
                .map(routeServerConnectionResponse -> new AddConnectionWebSocketResponse());
    }

    Uni<AddConnectionWebSocketResponse> addConnection(final SecurityIdentity securityIdentity,
                                                      final WebSocketConnection webSocketConnection,
                                                      final Long runtimeId) {
        final var clientId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributes.CLIENT_ID.getAttributeName());
        final var tokenId = securityIdentity
                .<String>getAttribute(ServiceSecurityAttributes.TOKEN_ID.getAttributeName());
        final var userRole = securityIdentity
                .<UserRoleEnum>getAttribute(ServiceSecurityAttributes.USER_ROLE.getAttributeName());

        final var request = new AddConnectionRequest(webSocketConnection,
                runtimeId,
                tokenId,
                userRole,
                clientId);
        return roomService.addConnection(request)
                .replaceWith(new AddConnectionWebSocketResponse());
    }
}
