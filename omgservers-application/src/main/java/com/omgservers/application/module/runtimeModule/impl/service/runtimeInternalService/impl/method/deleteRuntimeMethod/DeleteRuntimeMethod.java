package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.deleteRuntimeMethod;

import com.omgservers.dto.runtimeModule.DeleteRuntimeInternalRequest;
import com.omgservers.dto.runtimeModule.DeleteRuntimeInternalResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimeMethod {
    Uni<DeleteRuntimeInternalResponse> deleteRuntime(DeleteRuntimeInternalRequest request);
}
