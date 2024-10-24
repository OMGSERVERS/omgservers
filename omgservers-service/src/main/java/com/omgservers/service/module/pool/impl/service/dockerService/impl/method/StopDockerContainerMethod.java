package com.omgservers.service.module.pool.impl.service.dockerService.impl.method;

import com.omgservers.schema.module.pool.docker.StopDockerContainerRequest;
import com.omgservers.schema.module.pool.docker.StopDockerContainerResponse;
import io.smallrye.mutiny.Uni;

public interface StopDockerContainerMethod {
    Uni<StopDockerContainerResponse> execute(StopDockerContainerRequest request);
}
