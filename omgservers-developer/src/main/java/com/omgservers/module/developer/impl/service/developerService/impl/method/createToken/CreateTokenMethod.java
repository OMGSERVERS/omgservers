package com.omgservers.module.developer.impl.service.developerService.impl.method.createToken;

import com.omgservers.dto.developer.CreateTokenDeveloperRequest;
import com.omgservers.dto.developer.CreateTokenDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTokenMethod {
    Uni<CreateTokenDeveloperResponse> createToken(CreateTokenDeveloperRequest request);
}
