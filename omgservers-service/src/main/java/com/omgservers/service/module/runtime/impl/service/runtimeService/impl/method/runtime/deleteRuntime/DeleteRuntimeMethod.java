package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtime.deleteRuntime;

import com.omgservers.model.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimeMethod {
    Uni<DeleteRuntimeResponse> deleteRuntime(DeleteRuntimeRequest request);
}
