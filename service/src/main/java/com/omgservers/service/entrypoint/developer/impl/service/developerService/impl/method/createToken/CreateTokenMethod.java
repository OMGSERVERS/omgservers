package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.createToken;

import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTokenMethod {
    Uni<CreateTokenDeveloperResponse> createToken(CreateTokenDeveloperRequest request);
}
