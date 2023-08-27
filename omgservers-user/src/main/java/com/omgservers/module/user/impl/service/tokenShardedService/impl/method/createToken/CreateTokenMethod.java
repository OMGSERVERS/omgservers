package com.omgservers.module.user.impl.service.tokenShardedService.impl.method.createToken;

import com.omgservers.dto.user.CreateTokenShardedResponse;
import com.omgservers.dto.user.CreateTokenShardedRequest;
import io.smallrye.mutiny.Uni;

public interface CreateTokenMethod {

    Uni<CreateTokenShardedResponse> createToken(CreateTokenShardedRequest request);
}
