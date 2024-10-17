package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.operation;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherConnection;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.component.DispatcherRoom;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface SendRuntimeTextMessageOperation {

    Uni<Boolean> execute(DispatcherRoom room, List<DispatcherConnection> playerConnections, String message);
}
