package com.omgservers.dispatcher.entrypoint.impl.dto;

import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnErrorEntrypointRequest {

    @NotNull
    WebSocketConnection webSocketConnection;

    @NotNull
    Throwable throwable;
}