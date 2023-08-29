package com.omgservers.module.context.impl.service.handlerService.impl.method.handleInitRuntimeCommand;

import com.omgservers.dto.context.HandleInitRuntimeCommandRequest;
import com.omgservers.dto.context.HandleInitRuntimeCommandResponse;
import io.smallrye.mutiny.Uni;

public interface HandleInitRuntimeCommandMethod {
    Uni<HandleInitRuntimeCommandResponse> handleInitRuntimeCommand(HandleInitRuntimeCommandRequest request);
}
