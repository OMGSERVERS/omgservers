package com.omgservers.service.module.system.impl.service.containerService.impl.method.runContainer;

import com.omgservers.model.dto.system.RunContainerRequest;
import com.omgservers.model.dto.system.RunContainerResponse;
import io.smallrye.mutiny.Uni;

public interface RunContainerMethod {
    Uni<RunContainerResponse> runContainer(RunContainerRequest request);
}
