package com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.deleteRuntime;

import com.omgservers.dto.runtime.DeleteRuntimeShardedRequest;
import com.omgservers.dto.runtime.DeleteRuntimeInternalResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimeMethod {
    Uni<DeleteRuntimeInternalResponse> deleteRuntime(DeleteRuntimeShardedRequest request);
}
