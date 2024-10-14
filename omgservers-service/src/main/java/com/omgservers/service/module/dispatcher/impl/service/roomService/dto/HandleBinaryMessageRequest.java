package com.omgservers.service.module.dispatcher.impl.service.roomService.dto;

import io.quarkus.websockets.next.WebSocketConnection;
import io.vertx.core.buffer.Buffer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandleBinaryMessageRequest {

    @NotNull
    WebSocketConnection webSocketConnection;

    @NotNull
    Buffer buffer;
}
