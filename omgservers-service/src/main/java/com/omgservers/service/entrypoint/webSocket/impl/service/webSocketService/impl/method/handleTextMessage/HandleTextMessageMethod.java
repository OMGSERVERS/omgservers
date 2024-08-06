package com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl.method.handleTextMessage;

import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleTextMessageWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleTextMessageWebSocketResponse;
import io.smallrye.mutiny.Uni;

public interface HandleTextMessageMethod {
    Uni<HandleTextMessageWebSocketResponse> handleTextMessage(HandleTextMessageWebSocketRequest request);
}
