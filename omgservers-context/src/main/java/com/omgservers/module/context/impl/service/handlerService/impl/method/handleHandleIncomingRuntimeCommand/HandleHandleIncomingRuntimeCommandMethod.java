package com.omgservers.module.context.impl.service.handlerService.impl.method.handleHandleIncomingRuntimeCommand;

import com.omgservers.dto.handler.HandleHandleIncomingRuntimeCommandRequest;
import io.smallrye.mutiny.Uni;

public interface HandleHandleIncomingRuntimeCommandMethod {
    Uni<Void> handleHandleIncomingRuntimeCommand(HandleHandleIncomingRuntimeCommandRequest request);
}
