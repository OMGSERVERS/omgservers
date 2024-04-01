package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRequest.deletePoolRuntimeServerContainerRequest;

import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.DeletePoolRuntimeServerContainerRequestRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.DeletePoolRuntimeServerContainerRequestResponse;
import io.smallrye.mutiny.Uni;

public interface DeletePoolRuntimeServerContainerRequestMethod {
    Uni<DeletePoolRuntimeServerContainerRequestResponse> deletePoolRuntimeServerContainerRequest(
            DeletePoolRuntimeServerContainerRequestRequest request);
}
