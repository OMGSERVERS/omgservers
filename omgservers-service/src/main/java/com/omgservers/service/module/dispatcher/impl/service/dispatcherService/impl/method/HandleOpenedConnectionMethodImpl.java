package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.schema.model.user.UserRoleEnum;
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
import com.omgservers.service.operation.calculateShard.CalculateShardOperation;
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

    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<Void> execute(final HandleOpenedConnectionRequest request) {
        log.debug("Requested, {}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var runtimeId = request.getRuntimeId();
        final var userRole = request.getUserRole();
        final var subject = request.getSubject();

        return calculateShardOperation.calculateShard(runtimeId.toString())
                .flatMap(shard -> {
                    if (shard.foreign()) {
                        final var dispatcherConnection = new DispatcherConnection(webSocketConnection,
                                ConnectionTypeEnum.ROUTED, runtimeId, userRole, subject);

                        return routeDispatcherConnection(dispatcherConnection, shard.serverUri())
                                .invoke(routed -> dispatcherConnections.put(dispatcherConnection));
                    } else {
                        final var dispatcherConnection = new DispatcherConnection(webSocketConnection,
                                ConnectionTypeEnum.SERVER, runtimeId, userRole, subject);

                        return handleDispatcherConnection(dispatcherConnection)
                                .invoke(response -> dispatcherConnections.put(dispatcherConnection));
                    }
                })
                .flatMap(result -> {
                    if (!result) {
                        return webSocketConnection.close(DispatcherCloseReason.CONNECTING_FAILED)
                                .invoke(voidItem -> log.warn("Failed to open dispatcher connection, id={}",
                                        webSocketConnection.id()));
                    } else {
                        return Uni.createFrom().voidItem();
                    }
                });
    }

    Uni<Boolean> routeDispatcherConnection(final DispatcherConnection dispatcherConnection,
                                           final URI serverUri) {
        final var request = new RouteServerConnectionRequest(dispatcherConnection, serverUri);
        return dispatcherModule.getRouterService().routeServerConnection(request)
                .map(RouteServerConnectionResponse::getRouted);
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
}
