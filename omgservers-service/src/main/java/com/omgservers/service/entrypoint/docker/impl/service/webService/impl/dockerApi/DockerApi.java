package com.omgservers.service.entrypoint.docker.impl.service.webService.impl.dockerApi;

import com.omgservers.schema.entrypoint.docker.BasicAuthDockerRequest;
import com.omgservers.schema.entrypoint.docker.BasicAuthDockerResponse;
import com.omgservers.schema.entrypoint.docker.OAuth2DockerRequest;
import com.omgservers.schema.entrypoint.docker.OAuth2DockerResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Docker Entrypoint API")
@Path("/service/v1/entrypoint/docker/request")
public interface DockerApi {

    @GET
    @Path("/token")
    Uni<BasicAuthDockerResponse> basicAuth(@NotNull @BeanParam BasicAuthDockerRequest request);

    @POST
    @Path("/token")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Uni<OAuth2DockerResponse> oAuth2(@NotNull @BeanParam OAuth2DockerRequest request);
}
