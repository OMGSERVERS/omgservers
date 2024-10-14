package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.module.dispatcher.DispatcherModule;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherCloseReason;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherConnectionsContainer;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleClosedConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleClosedConnectionResponse;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemoveConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.CloseClientConnectionRequest;
import io.quarkus.websockets.next.CloseReason;
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

    final DispatcherConnectionsContainer dispatcherConnectionsContainer;

    final DispatcherModule dispatcherModule;

    @Override
    public Uni<HandleClosedConnectionResponse> execute(
            final HandleClosedConnectionRequest request) {
        log.debug("Handle closed connection, request={}", request);

        final var webSocketConnection = request.getWebSocketConnection();

        final var webSocketType = dispatcherConnectionsContainer.getType(webSocketConnection);
        if (webSocketType.isPresent()) {
            return switch (webSocketType.get()) {
                case ROUTED -> closeRoutedConnection(webSocketConnection);
                case SERVER -> removeRoomConnection(webSocketConnection);
            };
        } else {
            return webSocketConnection.close(CloseReason.INTERNAL_SERVER_ERROR)
                    .replaceWith(new HandleClosedConnectionResponse());
        }
    }

    Uni<HandleClosedConnectionResponse> closeRoutedConnection(final WebSocketConnection serverConnection) {
        final var request = new CloseClientConnectionRequest(serverConnection,
                DispatcherCloseReason.ROUTED_CONNECTION_CLOSED);
        return dispatcherModule.getRouterService().closeClientConnection(request)
                .replaceWith(new HandleClosedConnectionResponse());
    }

    Uni<HandleClosedConnectionResponse> removeRoomConnection(final WebSocketConnection serverConnection) {
        final var request = new RemoveConnectionRequest(serverConnection);
        return dispatcherModule.getRoomService().removeConnection(request)
                .replaceWith(new HandleClosedConnectionResponse());
    }

}
