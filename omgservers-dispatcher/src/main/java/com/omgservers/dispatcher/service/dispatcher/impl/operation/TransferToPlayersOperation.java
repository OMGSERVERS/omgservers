package com.omgservers.dispatcher.service.dispatcher.impl.operation;

import com.omgservers.dispatcher.service.dispatcher.dto.MessageEncodingEnum;
import com.omgservers.dispatcher.service.dispatcher.impl.component.Dispatcher;
import com.omgservers.dispatcher.service.handler.component.DispatcherConnection;
import io.smallrye.mutiny.Uni;

public interface TransferToPlayersOperation {

    Uni<Boolean> execute(DispatcherConnection dispatcherConnection,
                         Dispatcher dispatcher,
                         MessageEncodingEnum messageEncoding,
                         String runtimeMessage);
}
