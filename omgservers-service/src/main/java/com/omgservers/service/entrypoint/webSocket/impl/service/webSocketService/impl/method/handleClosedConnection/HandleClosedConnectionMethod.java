package com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl.method.handleClosedConnection;

import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleClosedConnectionWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleClosedConnectionWebSocketResponse;
import io.smallrye.mutiny.Uni;

public interface HandleClosedConnectionMethod {
    Uni<HandleClosedConnectionWebSocketResponse> handleClosedConnection(HandleClosedConnectionWebSocketRequest request);
}
