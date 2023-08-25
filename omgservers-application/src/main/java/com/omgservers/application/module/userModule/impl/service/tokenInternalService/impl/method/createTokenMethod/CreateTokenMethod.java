package com.omgservers.application.module.userModule.impl.service.tokenInternalService.impl.method.createTokenMethod;

import com.omgservers.dto.userModule.CreateTokenInternalRequest;
import com.omgservers.dto.userModule.CreateTokenInternalResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTokenMethod {

    Uni<CreateTokenInternalResponse> createToken(CreateTokenInternalRequest request);
}
