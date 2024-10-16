package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto;

import com.omgservers.schema.model.user.UserRoleEnum;
import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandleOpenedConnectionRequest {

    @NotNull
    WebSocketConnection webSocketConnection;

    @NotNull
    Long runtimeId;

    @NotNull
    UserRoleEnum userRole;

    @NotNull
    Long subject;
}
