package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolRequest;

import com.omgservers.schema.module.pool.poolRequest.GetPoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.GetPoolRequestResponse;
import io.smallrye.mutiny.Uni;

public interface GetPoolRequestMethod {
    Uni<GetPoolRequestResponse> execute(GetPoolRequestRequest request);
}
