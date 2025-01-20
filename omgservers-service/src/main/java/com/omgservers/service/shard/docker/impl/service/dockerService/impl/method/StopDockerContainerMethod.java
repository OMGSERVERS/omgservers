package com.omgservers.service.shard.docker.impl.service.dockerService.impl.method;

import com.omgservers.schema.module.docker.StopDockerContainerRequest;
import com.omgservers.schema.module.docker.StopDockerContainerResponse;
import io.smallrye.mutiny.Uni;

public interface StopDockerContainerMethod {
    Uni<StopDockerContainerResponse> execute(StopDockerContainerRequest request);
}
