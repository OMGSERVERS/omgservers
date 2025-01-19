package com.omgservers.service.module.user.impl.service.userService.impl.method.user.createToken;

import com.omgservers.schema.module.user.CreateTokenRequest;
import com.omgservers.schema.module.user.CreateTokenResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTokenMethod {

    Uni<CreateTokenResponse> createToken(CreateTokenRequest request);
}
