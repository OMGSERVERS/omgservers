package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.viewRuntimeCommands;

import com.omgservers.model.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewRuntimeCommandsMethod {
    Uni<ViewRuntimeCommandsResponse> viewRuntimeCommands(ViewRuntimeCommandsRequest request);
}
