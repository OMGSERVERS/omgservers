package com.omgservers.module.system.impl.service.containerService.impl.method.stopContainer;

import com.omgservers.model.dto.system.StopContainerRequest;
import com.omgservers.model.dto.system.StopContainerResponse;
import io.smallrye.mutiny.Uni;

public interface StopContainerMethod {
    Uni<StopContainerResponse> stopContainer(StopContainerRequest request);
}
