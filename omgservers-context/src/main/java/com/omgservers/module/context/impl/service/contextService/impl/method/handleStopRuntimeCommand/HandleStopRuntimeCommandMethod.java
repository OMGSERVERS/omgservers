package com.omgservers.module.context.impl.service.contextService.impl.method.handleStopRuntimeCommand;

import com.omgservers.dto.context.HandleStopRuntimeCommandRequest;
import com.omgservers.dto.context.HandleStopRuntimeCommandResponse;
import io.smallrye.mutiny.Uni;

public interface HandleStopRuntimeCommandMethod {
    Uni<HandleStopRuntimeCommandResponse> handleStopRuntimeCommand(HandleStopRuntimeCommandRequest request);
}
