package com.omgservers.module.runtime.impl.service.runtimeService.impl.method.deleteRuntimeCommand;

import com.omgservers.dto.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.dto.runtime.DeleteRuntimeCommandResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimeCommandMethod {
    Uni<DeleteRuntimeCommandResponse> deleteRuntimeCommand(DeleteRuntimeCommandRequest request);
}
