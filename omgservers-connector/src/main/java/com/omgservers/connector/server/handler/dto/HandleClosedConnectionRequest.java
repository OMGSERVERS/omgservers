package com.omgservers.connector.server.handler.dto;

import io.quarkus.websockets.next.CloseReason;
import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandleClosedConnectionRequest {

    @NotNull
    @ToString.Exclude
    WebSocketConnection webSocketConnection;

    @NotNull
    CloseReason closeReason;

    @ToString.Include(rank = 1)
    public String id() {
        return webSocketConnection.id();
    }
}
