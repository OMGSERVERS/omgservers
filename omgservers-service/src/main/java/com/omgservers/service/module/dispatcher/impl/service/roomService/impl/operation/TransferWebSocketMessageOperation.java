package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.operation;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherConnection;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.MessageEncodingEnum;
import io.smallrye.mutiny.Uni;

public interface TransferWebSocketMessageOperation {

    Uni<Boolean> execute(DispatcherConnection dispatcherConnection, MessageEncodingEnum type, String message);
}
