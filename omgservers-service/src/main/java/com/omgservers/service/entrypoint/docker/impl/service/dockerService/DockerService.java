package com.omgservers.service.entrypoint.docker.impl.service.dockerService;

import com.omgservers.schema.entrypoint.docker.BasicAuthDockerRequest;
import com.omgservers.schema.entrypoint.docker.BasicAuthDockerResponse;
import com.omgservers.schema.entrypoint.docker.OAuth2DockerRequest;
import com.omgservers.schema.entrypoint.docker.OAuth2DockerResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface DockerService {

    Uni<BasicAuthDockerResponse> basicAuth(@Valid BasicAuthDockerRequest request);

    Uni<OAuth2DockerResponse> oAuth2(@Valid OAuth2DockerRequest request);
}
