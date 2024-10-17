package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.operation;

import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.OutgoingRuntimeMessageDto;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.component.DispatcherRoom;

import java.util.Optional;

public interface ParseRuntimeOutgoingMessageOperation {

    Optional<OutgoingRuntimeMessageDto> execute(DispatcherRoom room, String runtimeMessage);
}
