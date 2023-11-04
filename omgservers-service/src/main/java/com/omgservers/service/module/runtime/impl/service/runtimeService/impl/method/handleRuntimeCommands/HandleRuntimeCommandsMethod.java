package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.handleRuntimeCommands;

import com.omgservers.model.dto.runtime.HandleRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.HandleRuntimeCommandsResponse;
import io.smallrye.mutiny.Uni;

public interface HandleRuntimeCommandsMethod {
    Uni<HandleRuntimeCommandsResponse> handleRuntimeCommands(HandleRuntimeCommandsRequest request);
}
