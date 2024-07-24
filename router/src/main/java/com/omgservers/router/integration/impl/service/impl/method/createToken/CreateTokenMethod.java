package com.omgservers.router.integration.impl.service.impl.method.createToken;

import com.omgservers.model.dto.router.CreateTokenRouterRequest;
import com.omgservers.model.dto.router.CreateTokenRouterResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTokenMethod {
    Uni<CreateTokenRouterResponse> createToken(CreateTokenRouterRequest request);
}



