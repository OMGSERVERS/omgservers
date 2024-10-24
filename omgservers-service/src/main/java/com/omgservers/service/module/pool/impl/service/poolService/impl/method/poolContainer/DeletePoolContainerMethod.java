package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolContainer;

import com.omgservers.schema.module.pool.poolContainer.DeletePoolContainerRequest;
import com.omgservers.schema.module.pool.poolContainer.DeletePoolContainerResponse;
import io.smallrye.mutiny.Uni;

public interface DeletePoolContainerMethod {
    Uni<DeletePoolContainerResponse> execute(DeletePoolContainerRequest request);
}
