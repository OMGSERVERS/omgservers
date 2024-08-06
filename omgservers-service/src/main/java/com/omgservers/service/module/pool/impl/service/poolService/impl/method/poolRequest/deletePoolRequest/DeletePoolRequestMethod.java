package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRequest.deletePoolRequest;

import com.omgservers.schema.module.pool.poolRequest.DeletePoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.DeletePoolRequestResponse;
import io.smallrye.mutiny.Uni;

public interface DeletePoolRequestMethod {
    Uni<DeletePoolRequestResponse> deletePoolRequest(
            DeletePoolRequestRequest request);
}
