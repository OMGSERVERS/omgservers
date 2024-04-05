package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRequest.deletePoolRequest;

import com.omgservers.model.dto.pool.poolRequest.DeletePoolRequestRequest;
import com.omgservers.model.dto.pool.poolRequest.DeletePoolRequestResponse;
import io.smallrye.mutiny.Uni;

public interface DeletePoolRequestMethod {
    Uni<DeletePoolRequestResponse> deletePoolRuntimeServerContainerRequest(
            DeletePoolRequestRequest request);
}
