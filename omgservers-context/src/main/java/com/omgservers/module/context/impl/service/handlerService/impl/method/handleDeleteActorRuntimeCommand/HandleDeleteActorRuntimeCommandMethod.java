package com.omgservers.module.context.impl.service.handlerService.impl.method.handleDeleteActorRuntimeCommand;

import com.omgservers.dto.handler.HandleDeleteActorRuntimeCommandRequest;
import io.smallrye.mutiny.Uni;

public interface HandleDeleteActorRuntimeCommandMethod {
    Uni<Void> handleDeleteActorRuntimeCommand(HandleDeleteActorRuntimeCommandRequest request);
}
