package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeCommand;

import com.omgservers.schema.module.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimeCommandResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimeCommandMethod {
    Uni<DeleteRuntimeCommandResponse> execute(DeleteRuntimeCommandRequest request);
}
