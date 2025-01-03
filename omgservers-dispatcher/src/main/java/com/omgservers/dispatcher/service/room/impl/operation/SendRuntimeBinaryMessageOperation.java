package com.omgservers.dispatcher.service.room.impl.operation;

import com.omgservers.dispatcher.service.handler.component.DispatcherConnection;
import com.omgservers.dispatcher.service.room.impl.component.DispatcherRoom;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface SendRuntimeBinaryMessageOperation {

    Uni<Boolean> execute(DispatcherRoom room,
                         List<DispatcherConnection> playerConnections,
                         String message);
}
