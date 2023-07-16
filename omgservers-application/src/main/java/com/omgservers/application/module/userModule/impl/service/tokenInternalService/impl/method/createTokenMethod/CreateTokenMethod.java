package com.omgservers.application.module.userModule.impl.service.tokenInternalService.impl.method.createTokenMethod;

import com.omgservers.application.module.userModule.impl.service.tokenInternalService.request.CreateTokenInternalRequest;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.response.CreateTokenInternalResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTokenMethod {

    Uni<CreateTokenInternalResponse> createToken(CreateTokenInternalRequest request);
}
