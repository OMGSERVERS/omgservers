package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.runtimePoolServerContainerRef.RuntimePoolServerContainerRefModel;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerResponse;
import com.omgservers.schema.module.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefRequest;
import com.omgservers.schema.module.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefResponse;
import com.omgservers.service.module.dispatcher.DispatcherModule;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.ConnectionTypeEnum;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherCloseReason;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherConnection;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleOpenedConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.components.DispatcherConnections;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.AddPlayerConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.AddPlayerConnectionResponse;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.CreateRoomRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.CreateRoomResponse;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.RouteServerConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.RouteServerConnectionResponse;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
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

    final GetConfigOperation getConfigOperation;

    final DispatcherConnections dispatcherConnections;

    @Override
    public Uni<Void> execute(final HandleOpenedConnectionRequest request) {
        log.debug("Handle opened connection, request={}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var runtimeId = request.getRuntimeId();
        final var userRole = request.getUserRole();
        final var subject = request.getSubject();

        return getRuntimeServer(runtimeId)
                .flatMap(poolServer -> switch (poolServer.getQualifier()) {
                    case DOCKER_HOST -> {
                        final var poolServerUri = poolServer.getConfig().getServerUri();
                        final var thisServerUri = getConfigOperation
                                .getServiceConfig().index().serverUri();
                        if (poolServerUri.equals(thisServerUri)) {
                            final var dispatcherConnection = new DispatcherConnection(webSocketConnection,
                                    ConnectionTypeEnum.SERVER, runtimeId, userRole, subject);

                            yield handleDispatcherConnection(dispatcherConnection)
                                    .invoke(response -> dispatcherConnections.put(dispatcherConnection));
                        } else {
                            final var dispatcherConnection = new DispatcherConnection(webSocketConnection,
                                    ConnectionTypeEnum.ROUTED, runtimeId, userRole, subject);

                            yield routeDispatcherConnection(dispatcherConnection, poolServerUri)
                                    .invoke(routed -> dispatcherConnections.put(dispatcherConnection));
                        }
                    }
                })
                .flatMap(result -> {
                    if (!result) {
                        return webSocketConnection.close(DispatcherCloseReason.DISPATCHER_CONNECTION_FAILURE)
                                .invoke(voidItem -> log.warn("Failed to open dispatcher connection, id={}",
                                        webSocketConnection.id()));
                    } else {
                        return Uni.createFrom().voidItem();
                    }
                });
    }

    Uni<PoolServerModel> getRuntimeServer(final Long runtimeId) {
        return findRuntimePoolServerContainerRef(runtimeId)
                .flatMap(runtimePoolServerContainerRef -> {
                    final var poolId = runtimePoolServerContainerRef.getPoolId();
                    final var serverId = runtimePoolServerContainerRef.getServerId();
                    return getRuntimeServer(poolId, serverId);
                });
    }

    Uni<RuntimePoolServerContainerRefModel> findRuntimePoolServerContainerRef(final Long runtimeId) {
        final var request = new FindRuntimePoolServerContainerRefRequest(runtimeId);
        return runtimeModule.getService().findRuntimePoolServerContainerRef(request)
                .map(FindRuntimePoolServerContainerRefResponse::getRuntimePoolServerContainerRef);
    }

    Uni<PoolServerModel> getRuntimeServer(final Long poolId,
                                          final Long serverId) {
        final var request = new GetPoolServerRequest(poolId, serverId);
        return poolModule.getPoolService().getPoolServer(request)
                .map(GetPoolServerResponse::getPoolServer);
    }

    Uni<Boolean> handleDispatcherConnection(final DispatcherConnection dispatcherConnection) {
        final var userRole = dispatcherConnection.getUserRole();

        if (userRole.equals(UserRoleEnum.RUNTIME)) {
            final var createRoomRequest = new CreateRoomRequest(dispatcherConnection);
            return dispatcherModule.getRoomService().createRoom(createRoomRequest)
                    .map(CreateRoomResponse::getCreated);
        } else {
            final var request = new AddPlayerConnectionRequest(dispatcherConnection);
            return dispatcherModule.getRoomService().addPlayerConnection(request)
                    .map(AddPlayerConnectionResponse::getAdded);
        }
    }

    Uni<Boolean> routeDispatcherConnection(final DispatcherConnection dispatcherConnection,
                                           final URI serverUri) {
        final var request = new RouteServerConnectionRequest(dispatcherConnection, serverUri);
        return dispatcherModule.getRouterService().routeServerConnection(request)
                .map(RouteServerConnectionResponse::getRouted);
    }
}
