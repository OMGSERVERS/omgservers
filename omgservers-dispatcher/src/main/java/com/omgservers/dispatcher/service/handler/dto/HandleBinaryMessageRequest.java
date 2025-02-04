package com.omgservers.dispatcher.service.handler.dto;

import io.quarkus.websockets.next.WebSocketConnection;
import io.vertx.core.buffer.Buffer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandleBinaryMessageRequest {

    @NotNull
    @ToString.Exclude
    WebSocketConnection webSocketConnection;

    @NotNull
    Buffer buffer;

    @ToString.Include(rank = 1)
    public String id() {
        return webSocketConnection.id();
    }
}
