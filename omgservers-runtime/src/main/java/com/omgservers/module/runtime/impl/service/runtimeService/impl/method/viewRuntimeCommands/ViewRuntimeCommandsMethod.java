package com.omgservers.module.runtime.impl.service.runtimeService.impl.method.viewRuntimeCommands;

import com.omgservers.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.dto.runtime.ViewRuntimeCommandsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewRuntimeCommandsMethod {
    Uni<ViewRuntimeCommandsResponse> viewRuntimeCommands(ViewRuntimeCommandsRequest request);
}
