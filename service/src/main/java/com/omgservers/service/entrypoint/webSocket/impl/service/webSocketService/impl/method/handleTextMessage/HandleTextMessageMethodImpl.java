package com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl.method.handleTextMessage;

import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleTextMessageWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleTextMessageWebSocketResponse;
import com.omgservers.service.server.service.room.RoomService;
import com.omgservers.service.server.service.room.dto.HandleTextMessageRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleTextMessageMethodImpl implements HandleTextMessageMethod {

    final RoomService roomService;

    @Override
    public Uni<HandleTextMessageWebSocketResponse> handleTextMessage(final HandleTextMessageWebSocketRequest request) {
        log.debug("Handle text message, request={}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var message = request.getMessage();
        final var handleTextMessageRequest = new HandleTextMessageRequest(webSocketConnection, message);
        return roomService.handleTextMessage(handleTextMessageRequest)
                .replaceWith(new HandleTextMessageWebSocketResponse());
    }
}
