package com.omgservers.dispatcher.service.dispatcher.impl.operation;

import com.omgservers.dispatcher.service.handler.component.DispatcherConnection;
import com.omgservers.dispatcher.service.dispatcher.dto.MessageEncodingEnum;
import io.smallrye.mutiny.Uni;

public interface TransferWebSocketMessageOperation {

    Uni<Boolean> execute(DispatcherConnection dispatcherConnection, MessageEncodingEnum type, String message);
}
