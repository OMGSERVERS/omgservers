package com.omgservers.dispatcher.server.dispatcher.impl.operation;

import com.omgservers.dispatcher.server.dispatcher.dto.MessageEncodingEnum;
import com.omgservers.dispatcher.server.dispatcher.impl.component.Dispatcher;
import com.omgservers.dispatcher.server.handler.component.DispatcherConnection;
import io.smallrye.mutiny.Uni;

public interface TransferToPlayersOperation {

    Uni<Boolean> execute(DispatcherConnection dispatcherConnection,
                         Dispatcher dispatcher,
                         MessageEncodingEnum messageEncoding,
                         String runtimeMessage);
}
