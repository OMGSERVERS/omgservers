package com.omgservers.service.service.room.impl.operation;

import com.omgservers.service.service.room.dto.MessageEncodingEnum;
import io.quarkus.websockets.next.WebSocketConnection;
import io.smallrye.mutiny.Uni;

public interface HandleWebSocketMessageOperation {

    Uni<Void> execute(WebSocketConnection webSocketConnection, MessageEncodingEnum type, String message);
}
