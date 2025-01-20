package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeCommand;

import com.omgservers.schema.module.runtime.DeleteRuntimeCommandsRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimeCommandsResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimeCommandsMethod {
    Uni<DeleteRuntimeCommandsResponse> execute(DeleteRuntimeCommandsRequest request);
}
