package com.omgservers.service.server.service.router.component;

import io.netty.handler.codec.http.websocketx.WebSocketCloseStatus;
import io.quarkus.websockets.next.CloseReason;

public class RouterWebSocketCloseReasons {
    public static final CloseReason INTERNAL_EXCEPTION_OCCURRED =
            new CloseReason(WebSocketCloseStatus.NORMAL_CLOSURE.code(),
                    "Internal exception occurred");

}
