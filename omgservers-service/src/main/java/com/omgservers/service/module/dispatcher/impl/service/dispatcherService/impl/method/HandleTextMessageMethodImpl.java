package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.module.dispatcher.DispatcherModule;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherCloseReason;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherConnection;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleTextMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.components.DispatcherConnections;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.TransferRoomTextMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.TransferRoomTextMessageResponse;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferServerTextMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferServerTextMessageResponse;
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

    final DispatcherModule dispatcherModule;

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
                            .invoke(voidItem -> log.warn("Failed to transfer the dispatcher text message, id={}",
                                    webSocketConnection.id()));
                } else {
                    return Uni.createFrom().voidItem();
                }
            });
        } else {
            log.error("Corresponding dispatcher connection was not found to handle text message, closing web socket " +
                    "id={}", webSocketConnection.id());
            return webSocketConnection.close(CloseReason.INTERNAL_SERVER_ERROR);
        }
    }

    Uni<Boolean> transferServerTextMessage(final DispatcherConnection dispatcherConnection,
                                           final String message) {
        final var request = new TransferServerTextMessageRequest(dispatcherConnection, message);
        return dispatcherModule.getRouterService().transferServerTextMessage(request)
                .map(TransferServerTextMessageResponse::getTransferred);
    }

    Uni<Boolean> transferRoomTextMessage(final DispatcherConnection dispatcherConnection,
                                         final String message) {
        final var request = new TransferRoomTextMessageRequest(dispatcherConnection, message);
        return dispatcherModule.getRoomService().transferRoomTextMessage(request)
                .map(TransferRoomTextMessageResponse::getHandled);
    }
}
