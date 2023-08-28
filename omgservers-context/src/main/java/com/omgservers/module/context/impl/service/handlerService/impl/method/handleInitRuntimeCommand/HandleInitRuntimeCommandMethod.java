package com.omgservers.module.context.impl.service.handlerService.impl.method.handleInitRuntimeCommand;

import com.omgservers.dto.handler.HandleInitRuntimeCommandRequest;
import io.smallrye.mutiny.Uni;

public interface HandleInitRuntimeCommandMethod {
    Uni<Void> handleInitRuntimeCommand(HandleInitRuntimeCommandRequest request);
}
