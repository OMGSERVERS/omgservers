package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRequest.findPoolRequest;

import com.omgservers.schema.module.pool.poolRequest.FindPoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.FindPoolRequestResponse;
import io.smallrye.mutiny.Uni;

public interface FindPoolRequestMethod {
    Uni<FindPoolRequestResponse> findPoolRequest(FindPoolRequestRequest request);
}
