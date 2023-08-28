package com.omgservers.module.user.impl.service.tokenShardedService.impl.method.introspectToken;

import com.omgservers.dto.user.IntrospectTokenShardedRequest;
import com.omgservers.dto.user.IntrospectTokenShardedResponse;
import io.smallrye.mutiny.Uni;

public interface IntrospectTokenMethod {
    Uni<IntrospectTokenShardedResponse> introspectToken(IntrospectTokenShardedRequest request);
}
