package com.omgservers.service.entrypoint.dispatcher.dto;

import io.quarkus.websockets.next.WebSocketConnection;
import io.vertx.core.buffer.Buffer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnBinaryMessageDispatcherRequest {

    @NotNull
    WebSocketConnection webSocketConnection;

    @NotNull
    Buffer buffer;
}
