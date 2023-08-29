package com.omgservers.module.context.impl.service.handlerService.impl.method.handleHandleIncomingRuntimeCommand;

import com.omgservers.dto.context.HandleHandleIncomingRuntimeCommandRequest;
import com.omgservers.dto.context.HandleHandleIncomingRuntimeCommandResponse;
import io.smallrye.mutiny.Uni;

public interface HandleHandleIncomingRuntimeCommandMethod {
    Uni<HandleHandleIncomingRuntimeCommandResponse> handleHandleIncomingRuntimeCommand(HandleHandleIncomingRuntimeCommandRequest request);
}
