package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerRef.getPoolServerRef;

import com.omgservers.model.dto.pool.poolServerRef.GetPoolServerRefRequest;
import com.omgservers.model.dto.pool.poolServerRef.GetPoolServerRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetPoolServerRefMethod {
    Uni<GetPoolServerRefResponse> getPoolServerRef(GetPoolServerRefRequest request);
}
