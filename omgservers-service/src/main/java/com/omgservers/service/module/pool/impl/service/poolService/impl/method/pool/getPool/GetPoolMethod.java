package com.omgservers.service.module.pool.impl.service.poolService.impl.method.pool.getPool;

import com.omgservers.model.dto.pool.GetPoolRequest;
import com.omgservers.model.dto.pool.GetPoolResponse;
import io.smallrye.mutiny.Uni;

public interface GetPoolMethod {
    Uni<GetPoolResponse> getPool(GetPoolRequest request);
}
