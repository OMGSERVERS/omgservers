package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component;

import com.omgservers.schema.model.user.UserRoleEnum;
import io.quarkus.websockets.next.WebSocketConnection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class DispatcherConnection {

    @ToString.Exclude
    final WebSocketConnection webSocketConnection;

    final ConnectionTypeEnum connectionType;
    final Long runtimeId;
    final UserRoleEnum userRole;
    final Long subject;

    @ToString.Include(rank = 1)
    public String id() {
        return webSocketConnection.id();
    }
}
