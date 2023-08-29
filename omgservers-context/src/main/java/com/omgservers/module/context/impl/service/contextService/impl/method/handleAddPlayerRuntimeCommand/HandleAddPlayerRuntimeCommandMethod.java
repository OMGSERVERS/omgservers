package com.omgservers.module.context.impl.service.contextService.impl.method.handleAddPlayerRuntimeCommand;

import com.omgservers.dto.context.HandleAddPlayerRuntimeCommandRequest;
import com.omgservers.dto.context.HandleAddPlayerRuntimeCommandResponse;
import io.smallrye.mutiny.Uni;

public interface HandleAddPlayerRuntimeCommandMethod {
    Uni<HandleAddPlayerRuntimeCommandResponse> handleAddPlayerRuntimeCommand(HandleAddPlayerRuntimeCommandRequest request);
}
