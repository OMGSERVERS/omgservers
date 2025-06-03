package com.omgservers.connector.configuration;

import io.netty.handler.codec.http.websocketx.WebSocketCloseStatus;
import io.quarkus.websockets.next.CloseReason;

public class ConnectorCloseReason {
    public static final CloseReason FAILED_TO_OPEN =
            new CloseReason(WebSocketCloseStatus.PROTOCOL_ERROR.code(),
                    "Failed to open");

    public static final CloseReason BINARY_UNSUPPORTED =
            new CloseReason(WebSocketCloseStatus.PROTOCOL_ERROR.code(),
                    "Binary unsupported");

    public static final CloseReason INACTIVE_CONNECTION =
            new CloseReason(WebSocketCloseStatus.PROTOCOL_ERROR.code(),
                    "Inactive connection");
}
