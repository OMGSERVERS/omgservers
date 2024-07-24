package com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl;

import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.WebSocketService;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.AddConnectionWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.AddConnectionWebSocketResponse;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleBinaryMessageWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleBinaryMessageWebSocketResponse;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleTextMessageWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleTextMessageWebSocketResponse;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.RemoveConnectionWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.RemoveConnectionWebSocketResponse;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl.method.addConnection.AddConnectionMethod;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl.method.handleBinaryMessage.HandleBinaryMessageMethod;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl.method.handleTextMessage.HandleTextMessageMethod;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl.method.removeConnection.RemoveConnectionMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebSocketServiceImpl implements WebSocketService {

    final HandleBinaryMessageMethod handleBinaryMessageMethod;
    final HandleTextMessageMethod handleTextMessageMethod;
    final RemoveConnectionMethod removeConnectionMethod;
    final AddConnectionMethod addConnectionMethod;

    @Override
    public Uni<AddConnectionWebSocketResponse> addConnection(@Valid final AddConnectionWebSocketRequest request) {
        return addConnectionMethod.addConnection(request);
    }

    @Override
    public Uni<RemoveConnectionWebSocketResponse> removeConnection(
            @Valid final RemoveConnectionWebSocketRequest request) {
        return removeConnectionMethod.removeConnection(request);
    }

    @Override
    public Uni<HandleTextMessageWebSocketResponse> handleTextMessage(
            @Valid final HandleTextMessageWebSocketRequest request) {
        return handleTextMessageMethod.handleTextMessage(request);
    }

    @Override
    public Uni<HandleBinaryMessageWebSocketResponse> handleBinaryMessage(
            @Valid final HandleBinaryMessageWebSocketRequest request) {
        return handleBinaryMessageMethod.handleBinaryMessage(request);
    }
}
