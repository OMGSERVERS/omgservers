package com.omgservers.dispatcher.entrypoint.dto;

import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnTextMessageEntrypointRequest {

    @NotNull
    WebSocketConnection webSocketConnection;

    @NotNull
    String message;
}
