package com.omgservers.service.module.user.impl.service.tokenService.impl.method.introspectToken;

import com.omgservers.model.dto.user.IntrospectTokenRequest;
import com.omgservers.model.dto.user.IntrospectTokenResponse;
import io.smallrye.mutiny.Uni;

public interface IntrospectTokenMethod {
    Uni<IntrospectTokenResponse> introspectToken(IntrospectTokenRequest request);
}
