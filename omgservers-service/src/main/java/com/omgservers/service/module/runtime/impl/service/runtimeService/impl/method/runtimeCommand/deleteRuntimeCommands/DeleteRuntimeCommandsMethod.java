package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeCommand.deleteRuntimeCommands;

import com.omgservers.schema.module.runtime.DeleteRuntimeCommandsRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimeCommandsResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimeCommandsMethod {
    Uni<DeleteRuntimeCommandsResponse> deleteRuntimeCommands(DeleteRuntimeCommandsRequest request);
}
