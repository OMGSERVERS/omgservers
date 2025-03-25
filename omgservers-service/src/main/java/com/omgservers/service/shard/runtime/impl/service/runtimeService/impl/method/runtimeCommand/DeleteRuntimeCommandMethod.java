package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeCommand;

import com.omgservers.schema.module.runtime.runtimeCommand.DeleteRuntimeCommandRequest;
import com.omgservers.schema.module.runtime.runtimeCommand.DeleteRuntimeCommandResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimeCommandMethod {
    Uni<DeleteRuntimeCommandResponse> execute(DeleteRuntimeCommandRequest request);
}
