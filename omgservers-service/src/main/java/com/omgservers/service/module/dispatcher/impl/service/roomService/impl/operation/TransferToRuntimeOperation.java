package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.operation;

import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.MessageEncodingEnum;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.component.DispatcherRoom;
import io.smallrye.mutiny.Uni;

public interface TransferToRuntimeOperation {

    Uni<Boolean> execute(DispatcherRoom room,
                         Long clientId,
                         MessageEncodingEnum messageEncoding,
                         String playerMessage);
}
