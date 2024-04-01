package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRequest.getPoolRuntimeServerContainerRequest;

import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.GetPoolRuntimeServerContainerRequestRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.GetPoolRuntimeServerContainerRequestResponse;
import io.smallrye.mutiny.Uni;

public interface GetPoolRuntimeServerContainerRequestMethod {
    Uni<GetPoolRuntimeServerContainerRequestResponse> getPoolRuntimeServerContainerRequest(
            GetPoolRuntimeServerContainerRequestRequest request);
}
