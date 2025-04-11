package com.omgservers.dispatcher.service.dispatcher.impl.operation;

import com.omgservers.dispatcher.service.dispatcher.dto.OutgoingRuntimeMessageDto;
import com.omgservers.dispatcher.service.dispatcher.impl.component.Dispatcher;

import java.util.Optional;

public interface ParseRuntimeOutgoingMessageOperation {

    Optional<OutgoingRuntimeMessageDto> execute(Dispatcher dispatcher,
                                                String runtimeMessage);
}
