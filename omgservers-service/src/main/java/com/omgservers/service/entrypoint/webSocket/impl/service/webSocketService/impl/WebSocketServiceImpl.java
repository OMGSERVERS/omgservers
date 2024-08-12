package com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl;

import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.WebSocketService;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.AddConnectionWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.AddConnectionWebSocketResponse;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleClosedConnectionWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleBinaryMessageWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleBinaryMessageWebSocketResponse;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleTextMessageWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleTextMessageWebSocketResponse;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleWebSocketErrorRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleWebSocketErrorResponse;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleClosedConnectionWebSocketResponse;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl.method.addConnection.AddConnectionMethod;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl.method.handleBinaryMessage.HandleBinaryMessageMethod;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl.method.handleTextMessage.HandleTextMessageMethod;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl.method.handleWebSocketError.HandleWebSocketErrorMethod;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.impl.method.handleClosedConnection.HandleClosedConnectionMethod;
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

    final HandleClosedConnectionMethod handleClosedConnectionMethod;
    final HandleWebSocketErrorMethod handleWebSocketErrorMethod;
    final HandleBinaryMessageMethod handleBinaryMessageMethod;
    final HandleTextMessageMethod handleTextMessageMethod;
    final AddConnectionMethod addConnectionMethod;

    @Override
    public Uni<AddConnectionWebSocketResponse> addConnection(@Valid final AddConnectionWebSocketRequest request) {
        return addConnectionMethod.addConnection(request);
    }

    @Override
    public Uni<HandleClosedConnectionWebSocketResponse> handleClosedConnection(
            @Valid final HandleClosedConnectionWebSocketRequest request) {
        return handleClosedConnectionMethod.handleClosedConnection(request);
    }

    @Override
    public Uni<HandleWebSocketErrorResponse> handleWebSocketError(@Valid final HandleWebSocketErrorRequest request) {
        return handleWebSocketErrorMethod.handleWebSocketError(request);
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
