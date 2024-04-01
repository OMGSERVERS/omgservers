package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRequest.findPoolRuntimeServerContainerRequest;

import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.FindPoolRuntimeServerContainerRequestRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.FindPoolRuntimeServerContainerRequestResponse;
import io.smallrye.mutiny.Uni;

public interface FindPoolRuntimeServerContainerRequestMethod {
    Uni<FindPoolRuntimeServerContainerRequestResponse> findPoolRuntimeServerContainerRequest(
            FindPoolRuntimeServerContainerRequestRequest request);
}
