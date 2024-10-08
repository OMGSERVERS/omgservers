package com.omgservers.service.module.docker.impl.service.webService.impl.api;

import com.omgservers.schema.module.docker.StartDockerContainerRequest;
import com.omgservers.schema.module.docker.StartDockerContainerResponse;
import com.omgservers.schema.module.docker.StopDockerContainerRequest;
import com.omgservers.schema.module.docker.StopDockerContainerResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Docker Module API")
@Path("/omgservers/v1/module/docker/request")
public interface DockerApi {

    @PUT
    @Path("/start-docker-container")
    Uni<StartDockerContainerResponse> startDockerContainer(StartDockerContainerRequest request);

    @PUT
    @Path("/stop-docker-container")
    Uni<StopDockerContainerResponse> stopDockerContainer(StopDockerContainerRequest request);
}
