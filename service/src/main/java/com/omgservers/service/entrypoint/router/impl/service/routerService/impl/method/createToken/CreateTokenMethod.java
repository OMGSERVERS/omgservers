package com.omgservers.service.entrypoint.router.impl.service.routerService.impl.method.createToken;

import com.omgservers.schema.entrypoint.router.CreateTokenRouterRequest;
import com.omgservers.schema.entrypoint.router.CreateTokenRouterResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTokenMethod {
    Uni<CreateTokenRouterResponse> createToken(CreateTokenRouterRequest request);
}