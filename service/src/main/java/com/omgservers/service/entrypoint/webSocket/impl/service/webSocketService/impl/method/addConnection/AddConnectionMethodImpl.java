package com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl.method.addConnection;

import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.AddConnectionWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.AddConnectionWebSocketResponse;
import com.omgservers.service.service.room.RoomService;
import com.omgservers.service.service.room.dto.AddConnectionRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AddConnectionMethodImpl implements AddConnectionMethod {

    final RoomService roomService;

    @Override
    public Uni<AddConnectionWebSocketResponse> addConnection(final AddConnectionWebSocketRequest request) {
        log.debug("Add connection, request={}", request);

        final var webSocketConnection = request.getWebSocketConnection();

        final var securityIdentity = request.getSecurityIdentity();
        final var subject = securityIdentity.<Long>getAttribute("subject");
        final var tokenId = securityIdentity.<String>getAttribute("tokenId");
        final var runtimeId = securityIdentity.<Long>getAttribute("runtimeId");
        final var userRole = securityIdentity.<UserRoleEnum>getAttribute("userRole");

        final var addConnectionRequest = new AddConnectionRequest(webSocketConnection,
                runtimeId,
                tokenId,
                userRole,
                subject);
        return roomService.addConnection(addConnectionRequest)
                .replaceWith(new AddConnectionWebSocketResponse());
    }
}
