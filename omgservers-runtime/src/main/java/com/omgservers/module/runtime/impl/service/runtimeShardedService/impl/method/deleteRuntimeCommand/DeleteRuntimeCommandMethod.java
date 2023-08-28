package com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.deleteRuntimeCommand;

import com.omgservers.dto.runtime.DeleteRuntimeCommandShardedRequest;
import com.omgservers.dto.runtime.DeleteRuntimeCommandShardedResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimeCommandMethod {
    Uni<DeleteRuntimeCommandShardedResponse> deleteRuntimeCommand(DeleteRuntimeCommandShardedRequest request);
}
