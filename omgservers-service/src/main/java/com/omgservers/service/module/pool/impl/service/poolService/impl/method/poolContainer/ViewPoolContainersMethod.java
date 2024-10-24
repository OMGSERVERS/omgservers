package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolContainer;

import com.omgservers.schema.module.pool.poolContainer.ViewPoolContainersRequest;
import com.omgservers.schema.module.pool.poolContainer.ViewPoolContainersResponse;
import io.smallrye.mutiny.Uni;

public interface ViewPoolContainersMethod {
    Uni<ViewPoolContainersResponse> execute(ViewPoolContainersRequest request);
}
