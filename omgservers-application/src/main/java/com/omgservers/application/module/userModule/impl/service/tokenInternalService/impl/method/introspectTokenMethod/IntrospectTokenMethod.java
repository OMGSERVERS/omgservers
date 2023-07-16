package com.omgservers.application.module.userModule.impl.service.tokenInternalService.impl.method.introspectTokenMethod;

import com.omgservers.application.module.userModule.impl.service.tokenInternalService.request.IntrospectTokenInternalRequest;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.response.IntrospectTokenInternalResponse;
import io.smallrye.mutiny.Uni;

public interface IntrospectTokenMethod {
    Uni<IntrospectTokenInternalResponse> introspectToken(IntrospectTokenInternalRequest request);
}
