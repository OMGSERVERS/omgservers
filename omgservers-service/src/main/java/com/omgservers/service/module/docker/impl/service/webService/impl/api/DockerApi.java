package com.omgservers.service.module.docker.impl.service.webService.impl.api;

import com.omgservers.schema.module.docker.PingDockerHostRequest;
import com.omgservers.schema.module.docker.PingDockerHostResponse;
import com.omgservers.schema.module.docker.StartDockerContainerRequest;
import com.omgservers.schema.module.docker.StartDockerContainerResponse;
import com.omgservers.schema.module.docker.StopDockerContainerRequest;
import com.omgservers.schema.module.docker.StopDockerContainerResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Docker Module API")
@Path("/service/v1/module/docker/request")
public interface DockerApi {

    /*
    Docker
     */

    @PUT
    @Path("/ping-docker-host")
    Uni<PingDockerHostResponse> execute(PingDockerHostRequest request);

    @PUT
    @Path("/start-docker-container")
    Uni<StartDockerContainerResponse> execute(StartDockerContainerRequest request);

    @PUT
    @Path("/stop-docker-container")
    Uni<StopDockerContainerResponse> execute(StopDockerContainerRequest request);
}
