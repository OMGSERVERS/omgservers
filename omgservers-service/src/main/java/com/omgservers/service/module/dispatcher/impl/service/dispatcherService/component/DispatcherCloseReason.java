package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component;

import io.netty.handler.codec.http.websocketx.WebSocketCloseStatus;
import io.quarkus.websockets.next.CloseReason;

public class DispatcherCloseReason {
    public static final CloseReason ROUTED_CONNECTION_CLOSED =
            new CloseReason(WebSocketCloseStatus.NORMAL_CLOSURE.code(),
                    "Routed connection was closed");

    public static final CloseReason WEBSOCKET_CONNECTION_FAILED =
            new CloseReason(WebSocketCloseStatus.ABNORMAL_CLOSURE.code(),
                    "WebSocket connection failed");
}
