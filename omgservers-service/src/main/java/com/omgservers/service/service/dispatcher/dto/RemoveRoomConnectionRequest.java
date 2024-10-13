package com.omgservers.service.service.dispatcher.dto;

import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoveRoomConnectionRequest {

    @NotNull
    Long runtimeId;

    @NotNull
    WebSocketConnection webSocketConnection;
}
