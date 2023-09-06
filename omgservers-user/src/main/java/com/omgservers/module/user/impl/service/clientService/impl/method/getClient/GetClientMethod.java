package com.omgservers.module.user.impl.service.clientService.impl.method.getClient;

import com.omgservers.dto.user.GetClientShardedResponse;
import com.omgservers.dto.user.GetClientShardedRequest;
import io.smallrye.mutiny.Uni;

public interface GetClientMethod {
    Uni<GetClientShardedResponse> getClient(GetClientShardedRequest request);
}
