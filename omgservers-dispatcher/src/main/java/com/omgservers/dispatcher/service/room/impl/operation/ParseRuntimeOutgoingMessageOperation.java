package com.omgservers.dispatcher.service.room.impl.operation;

import com.omgservers.dispatcher.service.room.dto.OutgoingRuntimeMessageDto;
import com.omgservers.dispatcher.service.room.impl.component.DispatcherRoom;

import java.util.Optional;

public interface ParseRuntimeOutgoingMessageOperation {

    Optional<OutgoingRuntimeMessageDto> execute(DispatcherRoom room, String runtimeMessage);
}
