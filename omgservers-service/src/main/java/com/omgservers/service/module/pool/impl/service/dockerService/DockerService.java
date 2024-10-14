package com.omgservers.service.module.pool.impl.service.dockerService;

import com.omgservers.schema.module.docker.StartDockerContainerRequest;
import com.omgservers.schema.module.docker.StartDockerContainerResponse;
import com.omgservers.schema.module.docker.StopDockerContainerRequest;
import com.omgservers.schema.module.docker.StopDockerContainerResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface DockerService {

    Uni<StartDockerContainerResponse> startDockerContainer(@Valid StartDockerContainerRequest request);

    Uni<StopDockerContainerResponse> stopDockerContainer(@Valid StopDockerContainerRequest request);
}
