package com.omgservers.service.module.pool.impl.service.dockerService.impl.method;

import com.omgservers.schema.module.pool.docker.StartDockerContainerRequest;
import com.omgservers.schema.module.pool.docker.StartDockerContainerResponse;
import io.smallrye.mutiny.Uni;

public interface StartDockerContainerMethod {
    Uni<StartDockerContainerResponse> execute(StartDockerContainerRequest request);
}
