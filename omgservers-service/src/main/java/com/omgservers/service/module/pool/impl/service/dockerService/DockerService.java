package com.omgservers.service.module.pool.impl.service.dockerService;

import com.omgservers.schema.module.pool.docker.StartDockerContainerRequest;
import com.omgservers.schema.module.pool.docker.StartDockerContainerResponse;
import com.omgservers.schema.module.pool.docker.StopDockerContainerRequest;
import com.omgservers.schema.module.pool.docker.StopDockerContainerResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface DockerService {

    Uni<StartDockerContainerResponse> execute(@Valid StartDockerContainerRequest request);

    Uni<StopDockerContainerResponse> execute(@Valid StopDockerContainerRequest request);
}
