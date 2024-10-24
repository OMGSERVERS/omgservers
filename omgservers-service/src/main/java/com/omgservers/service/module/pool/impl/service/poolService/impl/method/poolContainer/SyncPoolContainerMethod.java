package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolContainer;

import com.omgservers.schema.module.pool.poolContainer.SyncPoolContainerRequest;
import com.omgservers.schema.module.pool.poolContainer.SyncPoolContainerResponse;
import io.smallrye.mutiny.Uni;

public interface SyncPoolContainerMethod {
    Uni<SyncPoolContainerResponse> execute(SyncPoolContainerRequest request);
}
