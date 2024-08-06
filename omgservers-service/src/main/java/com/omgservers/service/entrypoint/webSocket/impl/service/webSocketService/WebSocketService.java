package com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService;

import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.AddConnectionWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.AddConnectionWebSocketResponse;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleBinaryMessageWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleBinaryMessageWebSocketResponse;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleTextMessageWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleTextMessageWebSocketResponse;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.RemoveConnectionWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.RemoveConnectionWebSocketResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface WebSocketService {

    Uni<AddConnectionWebSocketResponse> addConnection(@Valid AddConnectionWebSocketRequest request);

    Uni<RemoveConnectionWebSocketResponse> removeConnection(@Valid RemoveConnectionWebSocketRequest request);

    Uni<HandleTextMessageWebSocketResponse> handleTextMessage(@Valid HandleTextMessageWebSocketRequest request);

    Uni<HandleBinaryMessageWebSocketResponse> handleBinaryMessage(@Valid HandleBinaryMessageWebSocketRequest request);
}
