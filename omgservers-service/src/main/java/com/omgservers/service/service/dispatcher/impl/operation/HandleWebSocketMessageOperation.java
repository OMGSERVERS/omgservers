package com.omgservers.service.service.dispatcher.impl.operation;

import com.omgservers.service.service.dispatcher.dto.MessageEncodingEnum;
import io.quarkus.websockets.next.WebSocketConnection;
import io.smallrye.mutiny.Uni;

public interface HandleWebSocketMessageOperation {

    Uni<Void> execute(WebSocketConnection webSocketConnection, MessageEncodingEnum type, String message);
}
