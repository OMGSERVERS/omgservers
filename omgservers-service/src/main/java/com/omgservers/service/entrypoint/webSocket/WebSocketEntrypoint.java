package com.omgservers.service.entrypoint.webSocket;

import com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.WebSocketService;

public interface WebSocketEntrypoint {

    WebSocketService getWebSocketService();
}
