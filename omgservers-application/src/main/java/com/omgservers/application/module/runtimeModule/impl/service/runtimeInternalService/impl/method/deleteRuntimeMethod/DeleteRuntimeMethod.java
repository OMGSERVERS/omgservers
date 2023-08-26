package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.deleteRuntimeMethod;

import com.omgservers.dto.runtimeModule.DeleteRuntimeShardRequest;
import com.omgservers.dto.runtimeModule.DeleteRuntimeInternalResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimeMethod {
    Uni<DeleteRuntimeInternalResponse> deleteRuntime(DeleteRuntimeShardRequest request);
}
