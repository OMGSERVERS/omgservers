package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeCommand.deleteRuntimeCommands;

import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimeCommandsMethod {
    Uni<DeleteRuntimeCommandsResponse> deleteRuntimeCommands(DeleteRuntimeCommandsRequest request);
}
