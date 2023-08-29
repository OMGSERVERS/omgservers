package com.omgservers.module.context.impl.service.handlerService.impl.method.handleDeleteActorRuntimeCommand;

import com.omgservers.dto.context.HandleDeleteActorRuntimeCommandRequest;
import com.omgservers.dto.context.HandleDeleteActorRuntimeCommandResponse;
import io.smallrye.mutiny.Uni;

public interface HandleDeleteActorRuntimeCommandMethod {
    Uni<HandleDeleteActorRuntimeCommandResponse> handleDeleteActorRuntimeCommand(HandleDeleteActorRuntimeCommandRequest request);
}
