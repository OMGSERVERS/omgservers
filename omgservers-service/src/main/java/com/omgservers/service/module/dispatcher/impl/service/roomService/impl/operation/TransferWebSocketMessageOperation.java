package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.operation;

import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.MessageEncodingEnum;
import io.quarkus.websockets.next.WebSocketConnection;
import io.smallrye.mutiny.Uni;

public interface TransferWebSocketMessageOperation {

    Uni<Void> execute(WebSocketConnection webSocketConnection, MessageEncodingEnum type, String message);
}
