package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.createRuntimeMethod;

import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.CreateRuntimeInternalRequest;
import io.smallrye.mutiny.Uni;

public interface CreateRuntimeMethod {
    Uni<Void> createRuntime(CreateRuntimeInternalRequest request);
}
