package com.omgservers.dispatcher.service.dispatcher.impl.method;

import com.omgservers.dispatcher.service.dispatcher.component.DispatcherCloseReason;
import com.omgservers.dispatcher.service.dispatcher.component.DispatcherConnection;
import com.omgservers.dispatcher.service.dispatcher.dto.HandleTextMessageRequest;
import com.omgservers.dispatcher.service.dispatcher.impl.components.DispatcherConnections;
import com.omgservers.dispatcher.service.room.RoomService;
import com.omgservers.dispatcher.service.room.dto.TransferRoomTextMessageRequest;
import com.omgservers.dispatcher.service.room.dto.TransferRoomTextMessageResponse;
import com.omgservers.dispatcher.service.router.RouterService;
import com.omgservers.dispatcher.service.router.dto.TransferServerTextMessageRequest;
import com.omgservers.dispatcher.service.router.dto.TransferServerTextMessageResponse;
import io.quarkus.websockets.next.CloseReason;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleTextMessageMethodImpl implements HandleTextMessageMethod {

    final RouterService routerService;
    final RoomService roomService;

    final DispatcherConnections dispatcherConnections;

    @Override
    public Uni<Void> execute(final HandleTextMessageRequest request) {
        log.trace("Handle text message, request={}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var message = request.getMessage();

        final var dispatcherConnection = dispatcherConnections.get(webSocketConnection);
        if (Objects.nonNull(dispatcherConnection)) {
            return (switch (dispatcherConnection.getConnectionType()) {
                case ROUTED -> transferServerTextMessage(dispatcherConnection, message);
                case SERVER -> transferRoomTextMessage(dispatcherConnection, message);
            }).flatMap(result -> {
                if (!result) {
                    return webSocketConnection.close(DispatcherCloseReason.TRANSFER_FAILED)
                            .invoke(voidItem -> log.warn("Failed to transfer text message, id={}",
                                    webSocketConnection.id()));
                } else {
                    return Uni.createFrom().voidItem();
                }
            });
        } else {
            log.error("Corresponding dispatcher connection was not found to handle text message, closing websocket " +
                    "id={}", webSocketConnection.id());
            return webSocketConnection.close(CloseReason.INTERNAL_SERVER_ERROR);
        }
    }

    Uni<Boolean> transferServerTextMessage(final DispatcherConnection dispatcherConnection,
                                           final String message) {
        final var request = new TransferServerTextMessageRequest(dispatcherConnection, message);
        return routerService.transferServerTextMessage(request)
                .map(TransferServerTextMessageResponse::getTransferred);
    }

    Uni<Boolean> transferRoomTextMessage(final DispatcherConnection dispatcherConnection,
                                         final String message) {
        final var request = new TransferRoomTextMessageRequest(dispatcherConnection, message);
        return roomService.transferRoomTextMessage(request)
                .map(TransferRoomTextMessageResponse::getHandled);
    }
}
