package com.omgservers.service.module.user.impl.service.userService.impl.method.user.createToken;

import com.omgservers.model.dto.user.CreateTokenResponse;
import com.omgservers.model.dto.user.CreateTokenRequest;
import io.smallrye.mutiny.Uni;

public interface CreateTokenMethod {

    Uni<CreateTokenResponse> createToken(CreateTokenRequest request);
}
