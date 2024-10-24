package com.omgservers.service.module.pool.impl.service.poolService.impl.method.pool;

import com.omgservers.schema.module.pool.pool.GetPoolRequest;
import com.omgservers.schema.module.pool.pool.GetPoolResponse;
import io.smallrye.mutiny.Uni;

public interface GetPoolMethod {
    Uni<GetPoolResponse> execute(GetPoolRequest request);
}
