package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.deleteRuntimeMethod;

import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.DeleteRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.DeleteRuntimeInternalResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimeMethod {
    Uni<DeleteRuntimeInternalResponse> deleteRuntime(DeleteRuntimeInternalRequest request);
}
