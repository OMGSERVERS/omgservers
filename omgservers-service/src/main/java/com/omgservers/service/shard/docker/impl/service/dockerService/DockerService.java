package com.omgservers.service.shard.docker.impl.service.dockerService;

import com.omgservers.schema.module.docker.PingDockerHostRequest;
import com.omgservers.schema.module.docker.PingDockerHostResponse;
import com.omgservers.schema.module.docker.StartDockerContainerRequest;
import com.omgservers.schema.module.docker.StartDockerContainerResponse;
import com.omgservers.schema.module.docker.StopDockerContainerRequest;
import com.omgservers.schema.module.docker.StopDockerContainerResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface DockerService {

    Uni<PingDockerHostResponse> execute(@Valid PingDockerHostRequest request);

    Uni<StartDockerContainerResponse> execute(@Valid StartDockerContainerRequest request);

    Uni<StopDockerContainerResponse> execute(@Valid StopDockerContainerRequest request);
}
