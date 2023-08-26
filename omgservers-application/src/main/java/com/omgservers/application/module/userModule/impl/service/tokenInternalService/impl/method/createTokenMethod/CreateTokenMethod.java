package com.omgservers.application.module.userModule.impl.service.tokenInternalService.impl.method.createTokenMethod;

import com.omgservers.dto.userModule.CreateTokenRoutedRequest;
import com.omgservers.dto.userModule.CreateTokenInternalResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTokenMethod {

    Uni<CreateTokenInternalResponse> createToken(CreateTokenRoutedRequest request);
}
