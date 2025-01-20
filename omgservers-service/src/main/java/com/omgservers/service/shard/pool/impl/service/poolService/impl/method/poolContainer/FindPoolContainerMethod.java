package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolContainer;

import com.omgservers.schema.module.pool.poolContainer.FindPoolContainerRequest;
import com.omgservers.schema.module.pool.poolContainer.FindPoolContainerResponse;
import io.smallrye.mutiny.Uni;

public interface FindPoolContainerMethod {
    Uni<FindPoolContainerResponse> execute(FindPoolContainerRequest request);
}
