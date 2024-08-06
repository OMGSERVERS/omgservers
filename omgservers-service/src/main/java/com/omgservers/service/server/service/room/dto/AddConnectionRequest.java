package com.omgservers.service.server.service.room.dto;

import com.omgservers.schema.model.user.UserRoleEnum;
import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddConnectionRequest {

    @NotNull
    WebSocketConnection webSocketConnection;

    @NotNull
    Long runtimeId;

    @NotNull
    String usedTokenId;

    @NotNull
    UserRoleEnum role;

    @NotNull
    Long subject;
}
