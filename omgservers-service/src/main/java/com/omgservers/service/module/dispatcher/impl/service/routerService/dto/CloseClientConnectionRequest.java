package com.omgservers.service.module.dispatcher.impl.service.routerService.dto;

import io.quarkus.websockets.next.CloseReason;
import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CloseClientConnectionRequest {

    @NotNull
    WebSocketConnection serverConnection;

    @NotNull
    CloseReason closeReason;
}
