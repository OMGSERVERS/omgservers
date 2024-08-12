package com.omgservers.service.server.service.router.dto;

import io.quarkus.websockets.next.WebSocketClientConnection;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferClientTextMessageRequest {

    @NotNull
    WebSocketClientConnection clientConnection;

    @NotNull
    String message;
}
