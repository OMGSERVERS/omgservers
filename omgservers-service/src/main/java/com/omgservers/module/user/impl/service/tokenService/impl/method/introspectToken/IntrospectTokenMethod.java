package com.omgservers.module.user.impl.service.tokenService.impl.method.introspectToken;

import com.omgservers.dto.user.IntrospectTokenRequest;
import com.omgservers.dto.user.IntrospectTokenResponse;
import io.smallrye.mutiny.Uni;

public interface IntrospectTokenMethod {
    Uni<IntrospectTokenResponse> introspectToken(IntrospectTokenRequest request);
}
