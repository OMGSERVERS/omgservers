package com.omgservers.dispatcher.service.router.dto;

import io.quarkus.websockets.next.CloseReason;
import io.quarkus.websockets.next.WebSocketClientConnection;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CloseServerConnectionRequest {

    @NotNull
    WebSocketClientConnection clientConnection;

    @NotNull
    CloseReason closeReason;
}
