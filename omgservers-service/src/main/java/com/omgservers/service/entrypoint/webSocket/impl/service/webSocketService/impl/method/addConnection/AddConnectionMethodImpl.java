package com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl.method.addConnection;

import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.runtimePoolServerContainerRef.RuntimePoolServerContainerRefModel;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerResponse;
import com.omgservers.schema.module.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefRequest;
import com.omgservers.schema.module.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefResponse;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.component.WebSocketConnectionTypeEnum;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.component.WebSocketConnectionsContainer;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.AddConnectionWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.AddConnectionWebSocketResponse;
import com.omgservers.service.module.pool.impl.service.poolService.PoolService;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.operation.calculateShard.CalculateShardOperation;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import com.omgservers.service.security.ServiceSecurityAttributesEnum;
import com.omgservers.service.service.dispatcher.DispatcherService;
import com.omgservers.service.service.dispatcher.dto.AddConnectionRequest;
import com.omgservers.service.service.dispatcher.dto.CreateRoomRequest;
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

    final RuntimeModule runtimeModule;

    final DispatcherService dispatcherService;
    final RouterService routerService;
    final PoolService poolService;

    final CalculateShardOperation calculateShardOperation;
    final GetConfigOperation getConfigOperation;

    final WebSocketConnectionsContainer webSocketConnectionsContainer;

    @Override
    public Uni<AddConnectionWebSocketResponse> addConnection(final AddConnectionWebSocketRequest request) {
        log.debug("Add connection, request={}", request);

        final var securityIdentity = request.getSecurityIdentity();
        final var runtimeId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributesEnum.RUNTIME_ID.getAttributeName());

        return findRuntimePoolServerContainerRef(runtimeId)
                .flatMap(runtimePoolServerContainerRef -> {
                    final var poolId = runtimePoolServerContainerRef.getPoolId();
                    final var serverId = runtimePoolServerContainerRef.getServerId();
                    return getPoolServer(poolId, serverId)
                            .flatMap(poolServer -> {
                                final var webSocketConnection = request.getWebSocketConnection();
                                return switch (poolServer.getQualifier()) {
                                    case DOCKER_HOST -> {
                                        final var poolServerUri = poolServer.getConfig().getServerUri();
                                        final var thisServerUri = getConfigOperation
                                                .getServiceConfig().index().serverUri();
                                        if (poolServerUri.equals(thisServerUri)) {
                                            yield addConnection(securityIdentity, webSocketConnection, runtimeId)
                                                    .invoke(response -> webSocketConnectionsContainer
                                                            .put(webSocketConnection,
                                                                    WebSocketConnectionTypeEnum.SERVER));
                                        } else {
                                            yield routeConnection(securityIdentity, webSocketConnection, poolServerUri)
                                                    .invoke(response -> webSocketConnectionsContainer.put(
                                                            webSocketConnection,
                                                            WebSocketConnectionTypeEnum.ROUTED));
                                        }
                                    }
                                };
                            });
                });
    }

    Uni<RuntimePoolServerContainerRefModel> findRuntimePoolServerContainerRef(final Long runtimeId) {
        final var request = new FindRuntimePoolServerContainerRefRequest(runtimeId);
        return runtimeModule.getService().findRuntimePoolServerContainerRef(request)
                .map(FindRuntimePoolServerContainerRefResponse::getRuntimePoolServerContainerRef);
    }

    Uni<PoolServerModel> getPoolServer(final Long poolId,
                                       final Long serverId) {
        final var request = new GetPoolServerRequest(poolId, serverId);
        return poolService.getPoolServer(request)
                .map(GetPoolServerResponse::getPoolServer);
    }

    Uni<AddConnectionWebSocketResponse> addConnection(final SecurityIdentity securityIdentity,
                                                      final WebSocketConnection webSocketConnection,
                                                      final Long runtimeId) {
        final var clientId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributesEnum.CLIENT_ID.getAttributeName());
        final var tokenId = securityIdentity
                .<String>getAttribute(ServiceSecurityAttributesEnum.TOKEN_ID.getAttributeName());
        final var userRole = securityIdentity
                .<UserRoleEnum>getAttribute(ServiceSecurityAttributesEnum.USER_ROLE.getAttributeName());

        final Uni<Void> createRoomUni;
        if (userRole.equals(UserRoleEnum.RUNTIME)) {
            final var createRoomRequest = new CreateRoomRequest(runtimeId);
            createRoomUni = dispatcherService.createRoom(createRoomRequest)
                    .replaceWithVoid();
        } else {
            createRoomUni = Uni.createFrom().voidItem();
        }

        final var request = new AddConnectionRequest(webSocketConnection,
                runtimeId,
                tokenId,
                userRole,
                clientId);
        return createRoomUni.flatMap(voidItem -> dispatcherService.addConnection(request))
                .replaceWith(new AddConnectionWebSocketResponse());
    }

    Uni<AddConnectionWebSocketResponse> routeConnection(final SecurityIdentity securityIdentity,
                                                        final WebSocketConnection webSocketConnection,
                                                        final URI serverUri) {
        final var request = new RouteServerConnectionRequest(securityIdentity, webSocketConnection, serverUri);
        return routerService.routeServerConnection(request)
                .map(routeServerConnectionResponse -> new AddConnectionWebSocketResponse());
    }
}
