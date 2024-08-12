package com.omgservers.service.entrypoint.webSocket.impl.service.webSocketService.component;

import io.netty.handler.codec.http.websocketx.WebSocketCloseStatus;
import io.quarkus.websockets.next.CloseReason;

public class WebSocketCloseReason {
    public static final CloseReason ROUTED_CONNECTION_CLOSED =
            new CloseReason(WebSocketCloseStatus.NORMAL_CLOSURE.code(),
                    "Routed connection was closed");
}
