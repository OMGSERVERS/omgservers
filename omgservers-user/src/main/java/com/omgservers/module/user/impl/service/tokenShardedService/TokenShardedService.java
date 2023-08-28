package com.omgservers.module.user.impl.service.tokenShardedService;

import com.omgservers.dto.user.CreateTokenShardedRequest;
import com.omgservers.dto.user.CreateTokenShardedResponse;
import com.omgservers.dto.user.IntrospectTokenShardedRequest;
import com.omgservers.dto.user.IntrospectTokenShardedResponse;
import io.smallrye.mutiny.Uni;

public interface TokenShardedService {

    Uni<CreateTokenShardedResponse> createToken(CreateTokenShardedRequest request);

    Uni<IntrospectTokenShardedResponse> introspectToken(IntrospectTokenShardedRequest request);
}
