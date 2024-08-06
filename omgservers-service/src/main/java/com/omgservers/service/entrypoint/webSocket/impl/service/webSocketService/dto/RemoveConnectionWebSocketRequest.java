package com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto;

import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoveConnectionWebSocketRequest {

    @NotNull
    SecurityIdentity securityIdentity;

    @NotNull
    WebSocketConnection webSocketConnection;
}
