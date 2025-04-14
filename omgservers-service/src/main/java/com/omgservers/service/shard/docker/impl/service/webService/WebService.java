package com.omgservers.service.shard.docker.impl.service.webService;

import com.omgservers.schema.shard.docker.PingDockerHostRequest;
import com.omgservers.schema.shard.docker.PingDockerHostResponse;
import com.omgservers.schema.shard.docker.StartDockerContainerRequest;
import com.omgservers.schema.shard.docker.StartDockerContainerResponse;
import com.omgservers.schema.shard.docker.StopDockerContainerRequest;
import com.omgservers.schema.shard.docker.StopDockerContainerResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    /*
    Docker
     */

    Uni<PingDockerHostResponse> execute(PingDockerHostRequest request);

    Uni<StartDockerContainerResponse> execute(StartDockerContainerRequest request);

    Uni<StopDockerContainerResponse> execute(StopDockerContainerRequest request);
}
