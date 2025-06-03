package com.omgservers.dispatcher.server.dispatcher.impl.operation;

import com.omgservers.dispatcher.server.handler.component.DispatcherConnection;
import com.omgservers.dispatcher.server.dispatcher.dto.MessageEncodingEnum;
import io.smallrye.mutiny.Uni;

public interface TransferWebSocketMessageOperation {

    Uni<Boolean> execute(DispatcherConnection dispatcherConnection, MessageEncodingEnum type, String message);
}
