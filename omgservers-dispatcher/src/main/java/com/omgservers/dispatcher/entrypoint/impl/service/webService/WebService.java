package com.omgservers.dispatcher.entrypoint.impl.service.webService;

import io.quarkus.websockets.next.CloseReason;
import io.quarkus.websockets.next.WebSocketConnection;
import io.smallrye.mutiny.Uni;
import io.vertx.core.buffer.Buffer;

public interface WebService {

    Uni<Void> onOpen(WebSocketConnection webSocketConnection);

    Uni<Void> onClose(WebSocketConnection webSocketConnection, CloseReason closeReason);

    Uni<Void> onError(WebSocketConnection webSocketConnection, Throwable throwable);

    Uni<Void> onTextMessage(WebSocketConnection webSocketConnection, String message);

    Uni<Void> onBinaryMessage(WebSocketConnection webSocketConnection, Buffer buffer);
}
