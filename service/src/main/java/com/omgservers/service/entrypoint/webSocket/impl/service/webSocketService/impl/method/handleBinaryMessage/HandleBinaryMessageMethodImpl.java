package com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl.method.handleBinaryMessage;

import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleBinaryMessageWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleBinaryMessageWebSocketResponse;
import com.omgservers.service.server.service.room.RoomService;
import com.omgservers.service.server.service.room.dto.HandleBinaryMessageRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleBinaryMessageMethodImpl implements HandleBinaryMessageMethod {

    final RoomService roomService;

    @Override
    public Uni<HandleBinaryMessageWebSocketResponse> handleBinaryMessage(
            final HandleBinaryMessageWebSocketRequest request) {
        log.debug("Handle binary message, request={}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var message = request.getMessage();
        final var handleBinaryMessageRequest = new HandleBinaryMessageRequest(webSocketConnection, message);
        return roomService.handleBinaryMessage(handleBinaryMessageRequest)
                .replaceWith(new HandleBinaryMessageWebSocketResponse());
    }
}
