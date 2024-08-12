package com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl.method.handleWebSocketError;

import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleWebSocketErrorRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleWebSocketErrorResponse;
import io.smallrye.mutiny.Uni;

public interface HandleWebSocketErrorMethod {
    Uni<HandleWebSocketErrorResponse> handleWebSocketError(HandleWebSocketErrorRequest request);
}
