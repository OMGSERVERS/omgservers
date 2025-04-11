package com.omgservers.dispatcher.service.handler.component;

import io.netty.handler.codec.http.websocketx.WebSocketCloseStatus;
import io.quarkus.websockets.next.CloseReason;

public class DispatcherCloseReason {
    public static final CloseReason FAILED_TO_OPEN =
            new CloseReason(WebSocketCloseStatus.PROTOCOL_ERROR.code(),
                    "Failed to open");

    public static final CloseReason INACTIVE_CONNECTION =
            new CloseReason(WebSocketCloseStatus.PROTOCOL_ERROR.code(),
                    "Inactive connection");

    public static final CloseReason DISPATCHER_DELETED =
            new CloseReason(WebSocketCloseStatus.ENDPOINT_UNAVAILABLE.code(),
                    "Dispatcher deleted");
}
