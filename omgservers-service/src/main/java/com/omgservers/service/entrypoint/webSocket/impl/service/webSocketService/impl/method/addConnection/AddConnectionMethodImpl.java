package com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl.method.addConnection;

import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.component.WebSocketConnectionTypeEnum;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.component.WebSocketConnectionsContainer;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.AddConnectionWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.AddConnectionWebSocketResponse;
import com.omgservers.service.server.operation.calculateShard.CalculateShardOperation;
import com.omgservers.service.server.service.room.RoomService;
import com.omgservers.service.server.service.room.dto.AddConnectionRequest;
import com.omgservers.service.server.service.router.RouterService;
import com.omgservers.service.server.service.router.dto.RouteServerConnectionRequest;
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
        final var runtimeId = securityIdentity.<Long>getAttribute("runtimeId");

        return calculateShardOperation.calculateShard(runtimeId.toString())
                .flatMap(shardModel -> {
                    final var webSocketConnection = request.getWebSocketConnection();
                    if (shardModel.foreign()) {
                        final var serverUri = shardModel.serverUri();
                        return routeConnection(webSocketConnection, serverUri)
                                .invoke(response -> webSocketConnectionsContainer.put(webSocketConnection,
                                        WebSocketConnectionTypeEnum.ROUTED));
                    } else {
                        return addConnection(securityIdentity, webSocketConnection, runtimeId)
                                .invoke(response -> webSocketConnectionsContainer.put(webSocketConnection,
                                        WebSocketConnectionTypeEnum.SERVER));
                    }
                });
    }

    Uni<AddConnectionWebSocketResponse> routeConnection(final WebSocketConnection webSocketConnection,
                                                        final URI serverUri) {
        final var request = new RouteServerConnectionRequest(webSocketConnection, serverUri);
        return routerService.routeServerConnection(request)
                .map(routeServerConnectionResponse -> new AddConnectionWebSocketResponse());
    }

    Uni<AddConnectionWebSocketResponse> addConnection(final SecurityIdentity securityIdentity,
                                                      final WebSocketConnection webSocketConnection,
                                                      final Long runtimeId) {
        final var subject = securityIdentity.<Long>getAttribute("subject");
        final var tokenId = securityIdentity.<String>getAttribute("tokenId");
        final var userRole = securityIdentity.<UserRoleEnum>getAttribute("userRole");

        final var request = new AddConnectionRequest(webSocketConnection,
                runtimeId,
                tokenId,
                userRole,
                subject);
        return roomService.addConnection(request)
                .replaceWith(new AddConnectionWebSocketResponse());
    }
}
