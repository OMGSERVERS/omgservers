package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto;

import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandleFailedConnectionRequest {

    @NotNull
    WebSocketConnection webSocketConnection;

    @NotNull
    Throwable throwable;
}
