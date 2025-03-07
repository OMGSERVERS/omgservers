package com.omgservers.dispatcher.service.handler.impl.method;

import com.omgservers.dispatcher.component.DispatcherTokenContainer;
import com.omgservers.dispatcher.service.handler.component.ConnectionTypeEnum;
import com.omgservers.dispatcher.service.handler.component.DispatcherCloseReason;
import com.omgservers.dispatcher.service.handler.component.DispatcherConnection;
import com.omgservers.dispatcher.service.handler.dto.HandleOpenedConnectionRequest;
import com.omgservers.dispatcher.service.handler.impl.components.DispatcherConnections;
import com.omgservers.dispatcher.service.room.RoomService;
import com.omgservers.dispatcher.service.room.dto.AddPlayerConnectionRequest;
import com.omgservers.dispatcher.service.room.dto.AddPlayerConnectionResponse;
import com.omgservers.dispatcher.service.room.dto.CreateRoomRequest;
import com.omgservers.dispatcher.service.room.dto.CreateRoomResponse;
import com.omgservers.dispatcher.service.router.RouterService;
import com.omgservers.dispatcher.service.router.dto.RouteServerConnectionRequest;
import com.omgservers.dispatcher.service.router.dto.RouteServerConnectionResponse;
import com.omgservers.dispatcher.service.service.ServiceService;
import com.omgservers.dispatcher.service.service.dto.CalculateShardRequest;
import com.omgservers.schema.model.user.UserRoleEnum;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleOpenedConnectionMethodImpl implements HandleOpenedConnectionMethod {

    final ServiceService serviceService;
    final RouterService routerService;
    final RoomService roomService;

    final DispatcherTokenContainer dispatcherTokenContainer;
    final DispatcherConnections dispatcherConnections;

    @Override
    public Uni<Void> execute(final HandleOpenedConnectionRequest request) {
        log.trace("{}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var runtimeId = request.getRuntimeId();
        final var userRole = request.getUserRole();
        final var subject = request.getSubject();

        log.info("New websocket is opening \"{}\", runtimeId=\"{}\", userRole=\"{}\", subject=\"{}\"",
                webSocketConnection.id(),
                runtimeId,
                userRole,
                subject);

        if (Objects.isNull(dispatcherTokenContainer.getToken())) {
            log.error("Closing websocket \"{}\", dispatcher token was not created yet",
                    webSocketConnection.id());
            return webSocketConnection.close(DispatcherCloseReason.CONNECTING_FAILED);
        }

        final var calculateShardRequest = new CalculateShardRequest(runtimeId.toString());
        return serviceService.execute(calculateShardRequest)
                .flatMap(response -> {
                    final var foreign = response.getForeign();
                    if (foreign) {
                        final var serverUri = response.getServerUri();
                        log.info("Routing websocket \"{}\" to the \"{}\" server", webSocketConnection.id(), serverUri);

                        final var dispatcherConnection = new DispatcherConnection(webSocketConnection,
                                ConnectionTypeEnum.ROUTED, runtimeId, userRole, subject);

                        return routeDispatcherConnection(dispatcherConnection, serverUri)
                                .invoke(routed -> dispatcherConnections.put(dispatcherConnection));
                    } else {
                        final var dispatcherConnection = new DispatcherConnection(webSocketConnection,
                                ConnectionTypeEnum.SERVER, runtimeId, userRole, subject);

                        return handleDispatcherConnection(dispatcherConnection)
                                .invoke(handled -> dispatcherConnections.put(dispatcherConnection));
                    }
                })
                .flatMap(result -> {
                    if (!result) {
                        log.error("Closing websocket \"{}\", dispatcher connection was not opened",
                                webSocketConnection.id());
                        return webSocketConnection.close(DispatcherCloseReason.CONNECTING_FAILED);
                    } else {
                        return Uni.createFrom().voidItem();
                    }
                });
    }

    Uni<Boolean> routeDispatcherConnection(final DispatcherConnection dispatcherConnection,
                                           final URI serverUri) {
        final var request = new RouteServerConnectionRequest(dispatcherConnection, serverUri);
        return routerService.routeServerConnection(request)
                .map(RouteServerConnectionResponse::getRouted);
    }

    Uni<Boolean> handleDispatcherConnection(final DispatcherConnection dispatcherConnection) {
        final var userRole = dispatcherConnection.getUserRole();

        if (userRole.equals(UserRoleEnum.RUNTIME)) {
            final var createRoomRequest = new CreateRoomRequest(dispatcherConnection);
            return roomService.createRoom(createRoomRequest)
                    .map(CreateRoomResponse::getCreated);
        } else {
            final var request = new AddPlayerConnectionRequest(dispatcherConnection);
            return roomService.addPlayerConnection(request)
                    .map(AddPlayerConnectionResponse::getAdded);
        }
    }
}
