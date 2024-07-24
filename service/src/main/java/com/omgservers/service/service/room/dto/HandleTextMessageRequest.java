package com.omgservers.service.service.room.dto;

import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandleTextMessageRequest {

    @NotNull
    WebSocketConnection webSocketConnection;

    @NotNull
    String message;
}
