package com.omgservers.service.shard.docker.impl.service.webService.impl.api;

import com.omgservers.schema.shard.docker.PingDockerHostRequest;
import com.omgservers.schema.shard.docker.PingDockerHostResponse;
import com.omgservers.schema.shard.docker.StartDockerContainerRequest;
import com.omgservers.schema.shard.docker.StartDockerContainerResponse;
import com.omgservers.schema.shard.docker.StopDockerContainerRequest;
import com.omgservers.schema.shard.docker.StopDockerContainerResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Docker Shard API")
@Path("/service/v1/shard/docker/request")
public interface DockerApi {

    /*
    Docker
     */

    @POST
    @Path("/ping-docker-host")
    Uni<PingDockerHostResponse> execute(PingDockerHostRequest request);

    @POST
    @Path("/start-docker-container")
    Uni<StartDockerContainerResponse> execute(StartDockerContainerRequest request);

    @POST
    @Path("/stop-docker-container")
    Uni<StopDockerContainerResponse> execute(StopDockerContainerRequest request);
}
