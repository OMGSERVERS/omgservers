package com.omgservers.dispatcher.service.dispatcher.impl.operation;

import com.omgservers.dispatcher.service.dispatcher.impl.component.Dispatcher;
import com.omgservers.dispatcher.service.handler.component.DispatcherConnection;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface SendRuntimeTextMessageOperation {

    Uni<Boolean> execute(Dispatcher dispatcher,
                         List<DispatcherConnection> playerConnections,
                         String message);
}
