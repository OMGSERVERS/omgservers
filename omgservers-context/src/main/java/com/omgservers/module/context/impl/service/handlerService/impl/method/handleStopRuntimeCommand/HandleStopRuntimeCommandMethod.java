package com.omgservers.module.context.impl.service.handlerService.impl.method.handleStopRuntimeCommand;

import com.omgservers.dto.handler.HandleInitRuntimeCommandRequest;
import com.omgservers.dto.handler.HandleStopRuntimeCommandRequest;
import io.smallrye.mutiny.Uni;

public interface HandleStopRuntimeCommandMethod {
    Uni<Void> handleStopRuntimeCommand(HandleStopRuntimeCommandRequest request);
}
