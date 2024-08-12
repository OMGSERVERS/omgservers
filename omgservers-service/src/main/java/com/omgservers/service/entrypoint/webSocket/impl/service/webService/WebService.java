package com.omgservers.service.entrypoint.webSocket.impl.service.webService;

import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.AddConnectionWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.AddConnectionWebSocketResponse;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleClosedConnectionWebSocketResponse;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleBinaryMessageWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleBinaryMessageWebSocketResponse;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleClosedConnectionWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleTextMessageWebSocketRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleTextMessageWebSocketResponse;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleWebSocketErrorRequest;
import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.dto.HandleWebSocketErrorResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<AddConnectionWebSocketResponse> addConnection(AddConnectionWebSocketRequest request);

    Uni<HandleClosedConnectionWebSocketResponse> handleCloseConnection(HandleClosedConnectionWebSocketRequest request);

    Uni<HandleWebSocketErrorResponse> handleWebSocketError(HandleWebSocketErrorRequest request);

    Uni<HandleTextMessageWebSocketResponse> handleTextMessage(HandleTextMessageWebSocketRequest request);

    Uni<HandleBinaryMessageWebSocketResponse> handleBinaryMessage(HandleBinaryMessageWebSocketRequest request);
}
