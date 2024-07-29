package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerContainer.syncPoolServerContainer;

import com.omgservers.schema.module.pool.poolServerContainer.SyncPoolServerContainerRequest;
import com.omgservers.schema.module.pool.poolServerContainer.SyncPoolServerContainerResponse;
import io.smallrye.mutiny.Uni;

public interface SyncPoolServerContainerMethod {
    Uni<SyncPoolServerContainerResponse> syncPoolServerContainer(
            SyncPoolServerContainerRequest request);
}
