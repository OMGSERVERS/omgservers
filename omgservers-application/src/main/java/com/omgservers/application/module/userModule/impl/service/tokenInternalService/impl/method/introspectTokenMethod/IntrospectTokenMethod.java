package com.omgservers.application.module.userModule.impl.service.tokenInternalService.impl.method.introspectTokenMethod;

import com.omgservers.dto.userModule.IntrospectTokenInternalRequest;
import com.omgservers.dto.userModule.IntrospectTokenInternalResponse;
import io.smallrye.mutiny.Uni;

public interface IntrospectTokenMethod {
    Uni<IntrospectTokenInternalResponse> introspectToken(IntrospectTokenInternalRequest request);
}
