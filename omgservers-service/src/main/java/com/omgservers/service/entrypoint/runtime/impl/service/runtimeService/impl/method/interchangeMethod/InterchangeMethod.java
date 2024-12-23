package com.omgservers.service.entrypoint.runtime.impl.service.runtimeService.impl.method.interchangeMethod;

import com.omgservers.schema.entrypoint.runtime.InterchangeRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.InterchangeRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface InterchangeMethod {
    Uni<InterchangeRuntimeResponse> execute(InterchangeRuntimeRequest request);
}
