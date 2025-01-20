package com.omgservers.service.shard.docker.impl.service.webService;

import com.omgservers.schema.module.docker.PingDockerHostRequest;
import com.omgservers.schema.module.docker.PingDockerHostResponse;
import com.omgservers.schema.module.docker.StartDockerContainerRequest;
import com.omgservers.schema.module.docker.StartDockerContainerResponse;
import com.omgservers.schema.module.docker.StopDockerContainerRequest;
import com.omgservers.schema.module.docker.StopDockerContainerResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    /*
    Docker
     */

    Uni<PingDockerHostResponse> execute(PingDockerHostRequest request);

    Uni<StartDockerContainerResponse> execute(StartDockerContainerRequest request);

    Uni<StopDockerContainerResponse> execute(StopDockerContainerRequest request);
}
