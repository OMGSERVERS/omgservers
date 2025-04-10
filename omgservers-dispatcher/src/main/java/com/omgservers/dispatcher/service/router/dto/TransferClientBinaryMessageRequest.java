package com.omgservers.dispatcher.service.router.dto;

import io.quarkus.websockets.next.WebSocketClientConnection;
import io.vertx.core.buffer.Buffer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferClientBinaryMessageRequest {

    @NotNull
    WebSocketClientConnection clientConnection;

    @NotNull
    Buffer buffer;
}
