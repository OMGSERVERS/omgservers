package com.omgservers.module.system.impl.service.containerService.impl.method.stopContainer;

import com.omgservers.model.dto.internal.StopContainerRequest;
import com.omgservers.model.dto.internal.StopContainerResponse;
import io.smallrye.mutiny.Uni;

public interface StopContainerMethod {
    Uni<StopContainerResponse> stopContainer(StopContainerRequest request);
}
