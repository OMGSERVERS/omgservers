package com.omgservers.service.server.service.room.impl.component;

import io.netty.handler.codec.http.websocketx.WebSocketCloseStatus;
import io.quarkus.websockets.next.CloseReason;

public class WebsocketCloseReason {
    public static final CloseReason ROOM_WAS_NOT_FOUND =
            new CloseReason(WebSocketCloseStatus.NORMAL_CLOSURE.code(),
                    "Room was not found");

    public static final CloseReason NEW_CONNECTION_WAS_OPENED =
            new CloseReason(WebSocketCloseStatus.NORMAL_CLOSURE.code(),
                    "New connection was opened");

    public static final CloseReason ROOM_WAS_REMOVED =
            new CloseReason(WebSocketCloseStatus.NORMAL_CLOSURE.code(),
                    "Room was removed");
}
