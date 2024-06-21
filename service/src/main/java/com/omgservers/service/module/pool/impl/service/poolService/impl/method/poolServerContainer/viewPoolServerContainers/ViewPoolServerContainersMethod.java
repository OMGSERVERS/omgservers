package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerContainer.viewPoolServerContainers;

import com.omgservers.model.dto.pool.poolServerContainer.ViewPoolServerContainersRequest;
import com.omgservers.model.dto.pool.poolServerContainer.ViewPoolServerContainersResponse;
import io.smallrye.mutiny.Uni;

public interface ViewPoolServerContainersMethod {
    Uni<ViewPoolServerContainersResponse> viewPoolServerContainers(
            ViewPoolServerContainersRequest request);
}
