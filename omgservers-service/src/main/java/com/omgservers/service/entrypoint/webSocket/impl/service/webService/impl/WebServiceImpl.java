package com.omgservers.service.entrypoint.webSocket.impl.service.webService.impl;

import com.omgservers.service.entrypoint.webSocket.impl.service.webService.WebService;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.WebSocketService;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.AddConnectionWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.AddConnectionWebSocketResponse;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleClosedConnectionWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleClosedConnectionWebSocketResponse;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleBinaryMessageWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleBinaryMessageWebSocketResponse;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleTextMessageWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleTextMessageWebSocketResponse;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleWebSocketErrorRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleWebSocketErrorResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final WebSocketService webSocketService;

    @Override
    public Uni<AddConnectionWebSocketResponse> addConnection(final AddConnectionWebSocketRequest request) {
        return webSocketService.addConnection(request);
    }

    @Override
    public Uni<HandleClosedConnectionWebSocketResponse> handleCloseConnection(final HandleClosedConnectionWebSocketRequest request) {
        return webSocketService.handleClosedConnection(request);
    }

    @Override
    public Uni<HandleWebSocketErrorResponse> handleWebSocketError(final HandleWebSocketErrorRequest request) {
        return webSocketService.handleWebSocketError(request);
    }

    @Override
    public Uni<HandleTextMessageWebSocketResponse> handleTextMessage(final HandleTextMessageWebSocketRequest request) {
        return webSocketService.handleTextMessage(request);
    }

    @Override
    public Uni<HandleBinaryMessageWebSocketResponse> handleBinaryMessage(
            final HandleBinaryMessageWebSocketRequest request) {
        return webSocketService.handleBinaryMessage(request);
    }
}
