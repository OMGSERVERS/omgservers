package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.operation;

import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.MessageEncodingEnum;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.component.DispatcherRoom;
import io.smallrye.mutiny.Uni;

public interface TransferToPlayersOperation {

    Uni<Boolean> execute(DispatcherRoom room,
                         MessageEncodingEnum messageEncoding,
                         String runtimeMessage);
}
