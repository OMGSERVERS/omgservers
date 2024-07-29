package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerContainer.findPoolServerContainer;

import com.omgservers.schema.module.pool.poolServerContainer.FindPoolServerContainerRequest;
import com.omgservers.schema.module.pool.poolServerContainer.FindPoolServerContainerResponse;
import io.smallrye.mutiny.Uni;

public interface FindPoolServerContainerMethod {
    Uni<FindPoolServerContainerResponse> findPoolServerContainer(
            FindPoolServerContainerRequest request);
}
