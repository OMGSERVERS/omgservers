package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerContainer.viewPoolServerContainers;

import com.omgservers.schema.module.pool.poolServerContainer.ViewPoolServerContainersRequest;
import com.omgservers.schema.module.pool.poolServerContainer.ViewPoolServerContainersResponse;
import io.smallrye.mutiny.Uni;

public interface ViewPoolServerContainersMethod {
    Uni<ViewPoolServerContainersResponse> viewPoolServerContainers(
            ViewPoolServerContainersRequest request);
}
