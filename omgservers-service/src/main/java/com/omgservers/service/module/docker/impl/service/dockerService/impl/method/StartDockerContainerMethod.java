package com.omgservers.service.module.docker.impl.service.dockerService.impl.method;

import com.omgservers.schema.module.docker.StartDockerContainerRequest;
import com.omgservers.schema.module.docker.StartDockerContainerResponse;
import io.smallrye.mutiny.Uni;

public interface StartDockerContainerMethod {
    Uni<StartDockerContainerResponse> execute(StartDockerContainerRequest request);
}
