package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRequest.getPoolRequest;

import com.omgservers.schema.module.pool.poolRequest.GetPoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.GetPoolRequestResponse;
import io.smallrye.mutiny.Uni;

public interface GetPoolRequestMethod {
    Uni<GetPoolRequestResponse> getPoolRequest(GetPoolRequestRequest request);
}
