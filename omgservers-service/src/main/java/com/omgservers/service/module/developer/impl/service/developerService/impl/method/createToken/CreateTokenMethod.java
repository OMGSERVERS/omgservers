package com.omgservers.service.module.developer.impl.service.developerService.impl.method.createToken;

import com.omgservers.model.dto.developer.CreateTokenDeveloperRequest;
import com.omgservers.model.dto.developer.CreateTokenDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTokenMethod {
    Uni<CreateTokenDeveloperResponse> createToken(CreateTokenDeveloperRequest request);
}
