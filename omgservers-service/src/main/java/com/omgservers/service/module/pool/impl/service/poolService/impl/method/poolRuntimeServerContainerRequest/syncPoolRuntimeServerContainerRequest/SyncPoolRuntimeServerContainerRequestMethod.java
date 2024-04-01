package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRequest.syncPoolRuntimeServerContainerRequest;

import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.SyncPoolRuntimeServerContainerRequestRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.SyncPoolRuntimeServerContainerRequestResponse;
import io.smallrye.mutiny.Uni;

public interface SyncPoolRuntimeServerContainerRequestMethod {
    Uni<SyncPoolRuntimeServerContainerRequestResponse> syncPoolRuntimeServerContainerRequest(
            SyncPoolRuntimeServerContainerRequestRequest request);
}
