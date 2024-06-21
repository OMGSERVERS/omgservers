package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRequest.findPoolRequest;

import com.omgservers.model.dto.pool.poolRequest.FindPoolRequestRequest;
import com.omgservers.model.dto.pool.poolRequest.FindPoolRequestResponse;
import io.smallrye.mutiny.Uni;

public interface FindPoolRequestMethod {
    Uni<FindPoolRequestResponse> findPoolRequest(FindPoolRequestRequest request);
}
