package com.omgservers.service.entrypoint.runtime.impl.service.runtimeService.impl.method.createToken;

import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTokenMethod {
    Uni<CreateTokenRuntimeResponse> execute(CreateTokenRuntimeRequest request);
}
