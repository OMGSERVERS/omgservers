package com.omgservers.module.context.impl.service.contextService.impl.method.handleUpdateRuntimeCommand;

import com.omgservers.dto.context.HandleUpdateRuntimeCommandRequest;
import com.omgservers.dto.context.HandleUpdateRuntimeCommandResponse;
import io.smallrye.mutiny.Uni;

public interface HandleUpdateRuntimeCommandMethod {
    Uni<HandleUpdateRuntimeCommandResponse> handleUpdateRuntimeCommand(HandleUpdateRuntimeCommandRequest request);
}
