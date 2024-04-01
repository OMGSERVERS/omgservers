package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRequest.viewPoolRuntimeServerContainerRequest;

import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.ViewPoolRuntimeServerContainerRequestsRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.ViewPoolRuntimeServerContainerRequestsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewPoolRuntimeServerContainerRequestsMethod {
    Uni<ViewPoolRuntimeServerContainerRequestsResponse> viewPoolRuntimeServerContainerRequests(
            ViewPoolRuntimeServerContainerRequestsRequest request);
}
