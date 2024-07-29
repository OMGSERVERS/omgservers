package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtime.deleteRuntime;

import com.omgservers.schema.module.runtime.DeleteRuntimeRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimeMethod {
    Uni<DeleteRuntimeResponse> deleteRuntime(DeleteRuntimeRequest request);
}
