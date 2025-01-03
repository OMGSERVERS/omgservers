package com.omgservers.dispatcher.service.handler.component;

import io.netty.handler.codec.http.websocketx.WebSocketCloseStatus;
import io.quarkus.websockets.next.CloseReason;

public class DispatcherCloseReason {
    public static final CloseReason CONNECTING_FAILED =
            new CloseReason(WebSocketCloseStatus.PROTOCOL_ERROR.code(),
                    "Failed to open the dispatcher connection");

    public static final CloseReason TRANSFER_FAILED =
            new CloseReason(WebSocketCloseStatus.PROTOCOL_ERROR.code(),
                    "Failed to transfer the message");

    public static final CloseReason IDLE_TIMED_OUT =
            new CloseReason(WebSocketCloseStatus.PROTOCOL_ERROR.code(),
                    "Connection timed out due to inactivity.");

    public static final CloseReason ROOM_REMOVED =
            new CloseReason(WebSocketCloseStatus.ENDPOINT_UNAVAILABLE.code(),
                    "Room was removed");
}
