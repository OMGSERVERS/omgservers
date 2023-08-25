package com.omgservers.application.module.developerModule.impl.service.developerHelpService.impl.method.createTokenMethod;

import com.omgservers.dto.developerModule.CreateTokenDeveloperRequest;
import com.omgservers.dto.developerModule.CreateTokenDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTokenMethod {
    Uni<CreateTokenDeveloperResponse> createToken(CreateTokenDeveloperRequest request);
}
