package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component;

import io.netty.handler.codec.http.websocketx.WebSocketCloseStatus;
import io.quarkus.websockets.next.CloseReason;

public class DispatcherCloseReason {
    public static final CloseReason CONNECTING_FAILED =
            new CloseReason(WebSocketCloseStatus.PROTOCOL_ERROR.code(),
                    "Failed to open the dispatcher connection");

    public static final CloseReason MESSAGE_TRANSFER_FAILURE =
            new CloseReason(WebSocketCloseStatus.NORMAL_CLOSURE.code(),
                    "Failed to transfer the message");

    public static final CloseReason ROOM_REMOVED =
            new CloseReason(WebSocketCloseStatus.ENDPOINT_UNAVAILABLE.code(),
                    "Room was removed");
}
