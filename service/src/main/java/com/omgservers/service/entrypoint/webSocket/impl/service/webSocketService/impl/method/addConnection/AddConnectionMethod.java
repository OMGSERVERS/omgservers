package com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl.method.addConnection;

import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.AddConnectionWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.AddConnectionWebSocketResponse;
import io.smallrye.mutiny.Uni;

public interface AddConnectionMethod {
    Uni<AddConnectionWebSocketResponse> addConnection(AddConnectionWebSocketRequest request);
}
