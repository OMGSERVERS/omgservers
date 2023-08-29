package com.omgservers.module.context.impl.service.contextService.impl.method.handleDeletePlayerRuntimeCommand;

import com.omgservers.dto.context.HandleDeletePlayerRuntimeCommandRequest;
import com.omgservers.dto.context.HandleDeletePlayerRuntimeCommandResponse;
import io.smallrye.mutiny.Uni;

public interface HandleDeletePlayerRuntimeCommandMethod {
    Uni<HandleDeletePlayerRuntimeCommandResponse> handleDeletePlayerRuntimeCommand(HandleDeletePlayerRuntimeCommandRequest request);
}
