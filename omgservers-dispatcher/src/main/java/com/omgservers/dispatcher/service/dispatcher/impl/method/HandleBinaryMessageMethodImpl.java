package com.omgservers.dispatcher.service.dispatcher.impl.method;

import com.omgservers.dispatcher.service.dispatcher.component.DispatcherCloseReason;
import com.omgservers.dispatcher.service.dispatcher.component.DispatcherConnection;
import com.omgservers.dispatcher.service.dispatcher.dto.HandleBinaryMessageRequest;
import com.omgservers.dispatcher.service.dispatcher.impl.components.DispatcherConnections;
import com.omgservers.dispatcher.service.room.RoomService;
import com.omgservers.dispatcher.service.room.dto.TransferRoomBinaryMessageRequest;
import com.omgservers.dispatcher.service.room.dto.TransferRoomBinaryMessageResponse;
import com.omgservers.dispatcher.service.router.RouterService;
import com.omgservers.dispatcher.service.router.dto.TransferServerBinaryMessageRequest;
import com.omgservers.dispatcher.service.router.dto.TransferServerBinaryMessageResponse;
import io.quarkus.websockets.next.CloseReason;
import io.smallrye.mutiny.Uni;
import io.vertx.core.buffer.Buffer;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleBinaryMessageMethodImpl implements HandleBinaryMessageMethod {

    final RouterService routerService;
    final RoomService roomService;

    final DispatcherConnections dispatcherConnections;

    @Override
    public Uni<Void> execute(final HandleBinaryMessageRequest request) {
        log.trace("Handle binary message, request={}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var buffer = request.getBuffer();

        final var dispatcherConnection = dispatcherConnections.get(webSocketConnection);
        if (Objects.nonNull(dispatcherConnection)) {
            return (switch (dispatcherConnection.getConnectionType()) {
                case ROUTED -> transferServerBinaryMessage(dispatcherConnection, buffer);
                case SERVER -> transferRoomBinaryMessage(dispatcherConnection, buffer);
            }).flatMap(result -> {
                if (!result) {
                    return webSocketConnection.close(DispatcherCloseReason.TRANSFER_FAILED)
                            .invoke(voidItem -> log.warn("Failed to transfer binary message, id={}",
                                    webSocketConnection.id()));
                } else {
                    return Uni.createFrom().voidItem();
                }
            });
        } else {
            log.error("Corresponding dispatcher connection was not found to handle binary message, closing websocket " +
                    "id={}", webSocketConnection.id());
            return webSocketConnection.close(CloseReason.INTERNAL_SERVER_ERROR);
        }
    }

    Uni<Boolean> transferServerBinaryMessage(final DispatcherConnection dispatcherConnection,
                                             final Buffer buffer) {
        final var request = new TransferServerBinaryMessageRequest(dispatcherConnection, buffer);
        return routerService.transferServerBinaryMessage(request)
                .map(TransferServerBinaryMessageResponse::getTransferred);
    }

    Uni<Boolean> transferRoomBinaryMessage(final DispatcherConnection dispatcherConnection,
                                           final Buffer buffer) {
        final var request = new TransferRoomBinaryMessageRequest(dispatcherConnection, buffer);
        return roomService.transferRoomBinaryMessage(request)
                .map(TransferRoomBinaryMessageResponse::getHandled);
    }
}
