package com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl.method.removeConnection;

import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.RemoveConnectionWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.RemoveConnectionWebSocketResponse;
import io.smallrye.mutiny.Uni;

public interface RemoveConnectionMethod {
    Uni<RemoveConnectionWebSocketResponse> removeConnection(RemoveConnectionWebSocketRequest request);
}
