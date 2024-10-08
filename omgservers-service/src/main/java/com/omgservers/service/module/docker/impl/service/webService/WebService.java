package com.omgservers.service.module.docker.impl.service.webService;

import com.omgservers.schema.module.docker.StartDockerContainerRequest;
import com.omgservers.schema.module.docker.StartDockerContainerResponse;
import com.omgservers.schema.module.docker.StopDockerContainerRequest;
import com.omgservers.schema.module.docker.StopDockerContainerResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<StartDockerContainerResponse> startDockerContainer(StartDockerContainerRequest request);

    Uni<StopDockerContainerResponse> stopDockerContainer(StopDockerContainerRequest request);
}
