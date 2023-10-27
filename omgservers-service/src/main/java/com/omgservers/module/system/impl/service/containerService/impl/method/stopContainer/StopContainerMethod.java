package com.omgservers.module.system.impl.service.containerService.impl.method.stopContainer;

import com.omgservers.dto.internal.StopContainerRequest;
import com.omgservers.dto.internal.StopContainerResponse;
import io.smallrye.mutiny.Uni;

public interface StopContainerMethod {
    Uni<StopContainerResponse> stopContainer(StopContainerRequest request);
}
