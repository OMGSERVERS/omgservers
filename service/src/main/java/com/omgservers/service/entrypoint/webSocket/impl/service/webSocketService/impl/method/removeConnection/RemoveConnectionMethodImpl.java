package com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl.method.removeConnection;

import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.RemoveConnectionWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.RemoveConnectionWebSocketResponse;
import com.omgservers.service.service.room.RoomService;
import com.omgservers.service.service.room.dto.RemoveConnectionRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RemoveConnectionMethodImpl implements RemoveConnectionMethod {

    final RoomService roomService;

    @Override
    public Uni<RemoveConnectionWebSocketResponse> removeConnection(final RemoveConnectionWebSocketRequest request) {
        log.debug("Remove connection, request={}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var removeConnectionRequest = new RemoveConnectionRequest(webSocketConnection);
        return roomService.removeConnection(removeConnectionRequest)
                .replaceWith(new RemoveConnectionWebSocketResponse());
    }

}
