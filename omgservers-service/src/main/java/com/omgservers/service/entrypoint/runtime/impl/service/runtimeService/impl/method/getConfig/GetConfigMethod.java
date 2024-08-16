package com.omgservers.service.entrypoint.runtime.impl.service.runtimeService.impl.method.getConfig;

import com.omgservers.schema.entrypoint.runtime.GetConfigRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.GetConfigRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface GetConfigMethod {
    Uni<GetConfigRuntimeResponse> getConfig(GetConfigRuntimeRequest request);
}
