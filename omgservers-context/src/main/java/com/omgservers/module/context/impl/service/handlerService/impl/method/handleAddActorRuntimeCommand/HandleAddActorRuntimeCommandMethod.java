package com.omgservers.module.context.impl.service.handlerService.impl.method.handleAddActorRuntimeCommand;

import com.omgservers.dto.context.HandleAddActorRuntimeCommandRequest;
import com.omgservers.dto.context.HandleAddActorRuntimeCommandResponse;
import io.smallrye.mutiny.Uni;

public interface HandleAddActorRuntimeCommandMethod {
    Uni<HandleAddActorRuntimeCommandResponse> handleAddActorRuntimeCommand(HandleAddActorRuntimeCommandRequest request);
}
