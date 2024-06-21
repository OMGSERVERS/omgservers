package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerContainer.deletePoolServerContainer;

import com.omgservers.model.dto.pool.poolServerContainer.DeletePoolServerContainerRequest;
import com.omgservers.model.dto.pool.poolServerContainer.DeletePoolServerContainerResponse;
import io.smallrye.mutiny.Uni;

public interface DeletePoolServerContainerMethod {
    Uni<DeletePoolServerContainerResponse> deletePoolServerContainer(
            DeletePoolServerContainerRequest request);
}
