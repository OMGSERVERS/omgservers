package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.module.dispatcher.DispatcherModule;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherCloseReason;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherConnection;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleBinaryMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.components.DispatcherConnections;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.TransferRoomBinaryMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.TransferRoomBinaryMessageResponse;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferServerBinaryMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferServerBinaryMessageResponse;
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

    final DispatcherModule dispatcherModule;

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
                            .invoke(voidItem -> log.warn("Failed to transfer the dispatcher binary message, id={}",
                                    webSocketConnection.id()));
                } else {
                    return Uni.createFrom().voidItem();
                }
            });
        } else {
            log.error("Corresponding dispatcher connection was not found to handle binary message, closing web socket " +
                    "id={}", webSocketConnection.id());
            return webSocketConnection.close(CloseReason.INTERNAL_SERVER_ERROR);
        }
    }

    Uni<Boolean> transferServerBinaryMessage(final DispatcherConnection dispatcherConnection,
                                             final Buffer buffer) {
        final var request = new TransferServerBinaryMessageRequest(dispatcherConnection, buffer);
        return dispatcherModule.getRouterService().transferServerBinaryMessage(request)
                .map(TransferServerBinaryMessageResponse::getTransferred);
    }

    Uni<Boolean> transferRoomBinaryMessage(final DispatcherConnection dispatcherConnection,
                                           final Buffer buffer) {
        final var request = new TransferRoomBinaryMessageRequest(dispatcherConnection, buffer);
        return dispatcherModule.getRoomService().transferRoomBinaryMessage(request)
                .map(TransferRoomBinaryMessageResponse::getHandled);
    }
}
