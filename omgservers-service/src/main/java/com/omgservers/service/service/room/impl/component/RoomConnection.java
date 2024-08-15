package com.omgservers.service.service.room.impl.component;

import com.omgservers.schema.model.user.UserRoleEnum;
import io.quarkus.websockets.next.WebSocketConnection;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomConnection {

    final WebSocketConnection webSocketConnection;
    final UserRoleEnum role;
    final String usedTokenId;
    final Long runtimeId;
    final Long clientId;
}
