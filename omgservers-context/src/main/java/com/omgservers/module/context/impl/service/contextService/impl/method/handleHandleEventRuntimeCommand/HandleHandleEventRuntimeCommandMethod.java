package com.omgservers.module.context.impl.service.contextService.impl.method.handleHandleEventRuntimeCommand;

import com.omgservers.dto.context.HandleHandleEventRuntimeCommandRequest;
import com.omgservers.dto.context.HandleHandleEventRuntimeCommandResponse;
import io.smallrye.mutiny.Uni;

public interface HandleHandleEventRuntimeCommandMethod {
    Uni<HandleHandleEventRuntimeCommandResponse> handleHandleEventRuntimeCommand(HandleHandleEventRuntimeCommandRequest request);
}
