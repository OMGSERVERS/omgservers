package com.omgservers.module.context.impl.service.handlerService.impl.method.handleAddActorRuntimeCommand;

import com.omgservers.dto.handler.HandleAddActorRuntimeCommandRequest;
import io.smallrye.mutiny.Uni;

public interface HandleAddActorRuntimeCommandMethod {
    Uni<Void> handleAddActorRuntimeCommand(HandleAddActorRuntimeCommandRequest request);
}
