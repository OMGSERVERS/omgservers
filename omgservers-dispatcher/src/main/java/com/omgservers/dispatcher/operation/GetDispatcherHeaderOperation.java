package com.omgservers.dispatcher.operation;

import com.omgservers.schema.model.user.UserRoleEnum;
import io.quarkus.websockets.next.WebSocketConnection;

public interface GetDispatcherHeaderOperation {
    Long getRuntimeId(WebSocketConnection webSocketConnection);

    UserRoleEnum getUserRole(WebSocketConnection webSocketConnection);

    Long getSubject(WebSocketConnection webSocketConnection);
}
