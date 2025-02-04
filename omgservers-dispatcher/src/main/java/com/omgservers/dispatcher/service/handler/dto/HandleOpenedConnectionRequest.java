package com.omgservers.dispatcher.service.handler.dto;

import com.omgservers.schema.model.user.UserRoleEnum;
import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandleOpenedConnectionRequest {

    @NotNull
    @ToString.Exclude
    WebSocketConnection webSocketConnection;

    @NotNull
    Long runtimeId;

    @NotNull
    UserRoleEnum userRole;

    @NotNull
    Long subject;

    @ToString.Include(rank = 1)
    public String id() {
        return webSocketConnection.id();
    }
}
