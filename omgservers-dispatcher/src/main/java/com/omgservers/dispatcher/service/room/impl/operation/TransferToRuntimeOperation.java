package com.omgservers.dispatcher.service.room.impl.operation;

import com.omgservers.dispatcher.service.room.dto.MessageEncodingEnum;
import com.omgservers.dispatcher.service.room.impl.component.DispatcherRoom;
import io.smallrye.mutiny.Uni;

public interface TransferToRuntimeOperation {

    Uni<Boolean> execute(DispatcherRoom room,
                         Long clientId,
                         MessageEncodingEnum messageEncoding,
                         String playerMessage);
}
