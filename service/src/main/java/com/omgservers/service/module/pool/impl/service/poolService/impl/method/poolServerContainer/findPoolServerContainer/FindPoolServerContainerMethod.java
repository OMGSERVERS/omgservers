package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerContainer.findPoolServerContainer;

import com.omgservers.model.dto.pool.poolServerContainer.FindPoolServerContainerRequest;
import com.omgservers.model.dto.pool.poolServerContainer.FindPoolServerContainerResponse;
import io.smallrye.mutiny.Uni;

public interface FindPoolServerContainerMethod {
    Uni<FindPoolServerContainerResponse> findPoolServerContainer(
            FindPoolServerContainerRequest request);
}
