package com.omgservers.dispatcher.service.room.impl.operation;

import com.omgservers.dispatcher.service.dispatcher.component.DispatcherConnection;
import com.omgservers.dispatcher.service.room.dto.MessageEncodingEnum;
import io.smallrye.mutiny.Uni;

public interface TransferWebSocketMessageOperation {

    Uni<Boolean> execute(DispatcherConnection dispatcherConnection, MessageEncodingEnum type, String message);
}
