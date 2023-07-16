package com.omgservers.application.module.userModule.impl.service.tokenInternalService;

import com.omgservers.application.module.userModule.impl.service.tokenInternalService.request.CreateTokenInternalRequest;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.request.IntrospectTokenInternalRequest;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.response.CreateTokenInternalResponse;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.response.IntrospectTokenInternalResponse;
import io.smallrye.mutiny.Uni;

public interface TokenInternalService {

    Uni<CreateTokenInternalResponse> createToken(CreateTokenInternalRequest request);

    Uni<IntrospectTokenInternalResponse> introspectToken(IntrospectTokenInternalRequest request);
}
