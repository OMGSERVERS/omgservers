package com.omgservers.module.user.impl.service.tokenService.impl.method.createToken;

import com.omgservers.dto.user.CreateTokenResponse;
import com.omgservers.dto.user.CreateTokenRequest;
import io.smallrye.mutiny.Uni;

public interface CreateTokenMethod {

    Uni<CreateTokenResponse> createToken(CreateTokenRequest request);
}
