package com.omgservers.application.module.userModule.impl.service.tokenInternalService;

import com.omgservers.dto.userModule.CreateTokenInternalRequest;
import com.omgservers.dto.userModule.CreateTokenInternalResponse;
import com.omgservers.dto.userModule.IntrospectTokenInternalRequest;
import com.omgservers.dto.userModule.IntrospectTokenInternalResponse;
import io.smallrye.mutiny.Uni;

public interface TokenInternalService {

    Uni<CreateTokenInternalResponse> createToken(CreateTokenInternalRequest request);

    Uni<IntrospectTokenInternalResponse> introspectToken(IntrospectTokenInternalRequest request);
}
