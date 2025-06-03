package com.omgservers.dispatcher.server.handler.dto;

import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandleTextMessageRequest {

    @NotNull
    @ToString.Exclude
    WebSocketConnection webSocketConnection;

    @NotNull
    String message;

    @ToString.Include(rank = 1)
    public String id() {
        return webSocketConnection.id();
    }
}
