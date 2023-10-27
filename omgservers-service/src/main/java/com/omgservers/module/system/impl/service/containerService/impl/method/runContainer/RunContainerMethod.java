package com.omgservers.module.system.impl.service.containerService.impl.method.runContainer;

import com.omgservers.dto.internal.RunContainerRequest;
import com.omgservers.dto.internal.RunContainerResponse;
import io.smallrye.mutiny.Uni;

public interface RunContainerMethod {
    Uni<RunContainerResponse> runContainer(RunContainerRequest request);
}
