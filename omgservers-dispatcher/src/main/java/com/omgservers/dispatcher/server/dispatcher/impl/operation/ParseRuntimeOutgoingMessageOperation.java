package com.omgservers.dispatcher.server.dispatcher.impl.operation;

import com.omgservers.dispatcher.server.dispatcher.dto.OutgoingRuntimeMessageDto;
import com.omgservers.dispatcher.server.dispatcher.impl.component.Dispatcher;

import java.util.Optional;

public interface ParseRuntimeOutgoingMessageOperation {

    Optional<OutgoingRuntimeMessageDto> execute(Dispatcher dispatcher,
                                                String runtimeMessage);
}
