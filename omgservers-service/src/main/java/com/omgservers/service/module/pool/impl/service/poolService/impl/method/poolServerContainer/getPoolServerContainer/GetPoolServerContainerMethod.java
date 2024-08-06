package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerContainer.getPoolServerContainer;

import com.omgservers.schema.module.pool.poolServerContainer.GetPoolServerContainerRequest;
import com.omgservers.schema.module.pool.poolServerContainer.GetPoolServerContainerResponse;
import io.smallrye.mutiny.Uni;

public interface GetPoolServerContainerMethod {
    Uni<GetPoolServerContainerResponse> getPoolServerContainer(
            GetPoolServerContainerRequest request);
}
