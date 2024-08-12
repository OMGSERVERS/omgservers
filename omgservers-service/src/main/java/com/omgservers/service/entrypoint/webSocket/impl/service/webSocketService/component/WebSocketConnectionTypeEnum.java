package com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.component;

public enum WebSocketConnectionTypeEnum {
    /**
     * Connection was routed to another server.
     */
    ROUTED,

    /**
     * Connection is handled by this server.
     */
    SERVER,
}
