package com.omgservers.service.module.docker.impl.service.webService.impl;

import com.omgservers.schema.module.docker.StartDockerContainerRequest;
import com.omgservers.schema.module.docker.StartDockerContainerResponse;
import com.omgservers.schema.module.docker.StopDockerContainerRequest;
import com.omgservers.schema.module.docker.StopDockerContainerResponse;
import com.omgservers.service.module.docker.impl.service.dockerService.DockerService;
import com.omgservers.service.module.docker.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class WebServiceImpl implements WebService {

    final DockerService dockerService;

    @Override
    public Uni<StartDockerContainerResponse> startDockerContainer(final StartDockerContainerRequest request) {
        return dockerService.startDockerContainer(request);
    }

    @Override
    public Uni<StopDockerContainerResponse> stopDockerContainer(final StopDockerContainerRequest request) {
        return dockerService.stopDockerContainer(request);
    }
}
