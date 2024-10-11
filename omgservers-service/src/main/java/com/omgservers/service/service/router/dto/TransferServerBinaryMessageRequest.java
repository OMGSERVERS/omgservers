package com.omgservers.service.service.router.dto;

import io.quarkus.websockets.next.WebSocketConnection;
import io.vertx.core.buffer.Buffer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferServerBinaryMessageRequest {

    @NotNull
    WebSocketConnection serverConnection;

    @NotNull
    Buffer buffer;
}
