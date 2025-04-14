package com.omgservers.service.shard.docker.impl.service.webService.impl;

import com.omgservers.schema.shard.docker.PingDockerHostRequest;
import com.omgservers.schema.shard.docker.PingDockerHostResponse;
import com.omgservers.schema.shard.docker.StartDockerContainerRequest;
import com.omgservers.schema.shard.docker.StartDockerContainerResponse;
import com.omgservers.schema.shard.docker.StopDockerContainerRequest;
import com.omgservers.schema.shard.docker.StopDockerContainerResponse;
import com.omgservers.service.shard.docker.impl.service.dockerService.DockerService;
import com.omgservers.service.shard.docker.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final DockerService dockerService;

    /*
    Docker
     */

    @Override
    public Uni<PingDockerHostResponse> execute(final PingDockerHostRequest request) {
        return dockerService.execute(request);
    }

    @Override
    public Uni<StartDockerContainerResponse> execute(final StartDockerContainerRequest request) {
        return dockerService.execute(request);
    }

    @Override
    public Uni<StopDockerContainerResponse> execute(final StopDockerContainerRequest request) {
        return dockerService.execute(request);
    }
}
