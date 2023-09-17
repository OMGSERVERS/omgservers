package com.omgservers.module.runtime.impl.service.runtimeService.impl.method.markRuntimeCommands;

import com.omgservers.dto.runtime.MarkRuntimeCommandsRequest;
import com.omgservers.dto.runtime.MarkRuntimeCommandsResponse;
import io.smallrye.mutiny.Uni;

public interface MarkRuntimeCommandsMethod {
    Uni<MarkRuntimeCommandsResponse> markRuntimeCommands(MarkRuntimeCommandsRequest request);
}
