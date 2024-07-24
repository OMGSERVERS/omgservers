package com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl.method.handleBinaryMessage;

import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleBinaryMessageWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleBinaryMessageWebSocketResponse;
import io.smallrye.mutiny.Uni;

public interface HandleBinaryMessageMethod {
    Uni<HandleBinaryMessageWebSocketResponse> handleBinaryMessage(HandleBinaryMessageWebSocketRequest request);
}
