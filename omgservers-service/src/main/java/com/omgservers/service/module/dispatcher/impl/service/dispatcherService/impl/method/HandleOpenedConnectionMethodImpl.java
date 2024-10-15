package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.runtimePoolServerContainerRef.RuntimePoolServerContainerRefModel;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerResponse;
import com.omgservers.schema.module.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefRequest;
import com.omgservers.schema.module.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefResponse;
import com.omgservers.service.module.dispatcher.DispatcherModule;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherConnectionTypeEnum;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherConnectionsContainer;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleOpenedConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleOpenedConnectionResponse;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.CreateRoomRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.RouteServerConnectionRequest;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.operation.calculateShard.CalculateShardOperation;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import com.omgservers.service.security.ServiceSecurityAttributesEnum;
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
class HandleOpenedConnectionMethodImpl implements HandleOpenedConnectionMethod {

    final DispatcherModule dispatcherModule;
    final RuntimeModule runtimeModule;
    final PoolModule poolModule;

    final CalculateShardOperation calculateShardOperation;
    final GetConfigOperation getConfigOperation;

    final DispatcherConnectionsContainer dispatcherConnectionsContainer;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<HandleOpenedConnectionResponse> execute(final HandleOpenedConnectionRequest request) {
        log.debug("Handle opened connection, request={}", request);

        final var runtimeId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributesEnum.RUNTIME_ID.getAttributeName());
        final var userRole = securityIdentity
                .<UserRoleEnum>getAttribute(ServiceSecurityAttributesEnum.USER_ROLE.getAttributeName());
        final var clientId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributesEnum.CLIENT_ID.getAttributeName());

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
                                            log.info("Dispatcher connection was established, " +
                                                            "id={}, userRole={}, clientId={}, runtimeId={}",
                                                    webSocketConnection.id(), userRole, clientId, runtimeId);

                                            yield addConnection(securityIdentity, webSocketConnection, runtimeId)
                                                    .invoke(response -> dispatcherConnectionsContainer.put(
                                                            webSocketConnection,
                                                            DispatcherConnectionTypeEnum.SERVER));
                                        } else {
                                            log.info("Dispatcher connection was routed, id={}, " +
                                                            "userRole={}, " +
                                                            "clientId={}, " +
                                                            "runtimeId={}, " +
                                                            "targetServer={}",
                                                    webSocketConnection.id(),
                                                    userRole,
                                                    clientId,
                                                    runtimeId,
                                                    poolServerUri);

                                            yield routeConnection(webSocketConnection, poolServerUri)
                                                    .invoke(response -> dispatcherConnectionsContainer.put(
                                                            webSocketConnection,
                                                            DispatcherConnectionTypeEnum.ROUTED));
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
        return poolModule.getPoolService().getPoolServer(request)
                .map(GetPoolServerResponse::getPoolServer);
    }

    Uni<HandleOpenedConnectionResponse> addConnection(final SecurityIdentity securityIdentity,
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
            createRoomUni = dispatcherModule.getRoomService().createRoom(createRoomRequest)
                    .replaceWithVoid();
        } else {
            createRoomUni = Uni.createFrom().voidItem();
        }

        final var request =
                new com.omgservers.service.module.dispatcher.impl.service.roomService.dto.AddConnectionRequest(
                        webSocketConnection,
                        runtimeId,
                        tokenId,
                        userRole,
                        clientId);
        return createRoomUni.flatMap(voidItem -> dispatcherModule.getRoomService().addConnection(request))
                .replaceWith(new HandleOpenedConnectionResponse());
    }

    Uni<HandleOpenedConnectionResponse> routeConnection(final WebSocketConnection webSocketConnection,
                                                        final URI serverUri) {
        final var request = new RouteServerConnectionRequest(webSocketConnection, serverUri);
        return dispatcherModule.getRouterService().routeServerConnection(request)
                .map(routeServerConnectionResponse -> new HandleOpenedConnectionResponse());
    }
}
