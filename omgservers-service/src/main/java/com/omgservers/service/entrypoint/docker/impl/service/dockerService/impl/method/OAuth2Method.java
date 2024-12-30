package com.omgservers.service.entrypoint.docker.impl.service.dockerService.impl.method;

import com.omgservers.schema.entrypoint.docker.OAuth2DockerRequest;
import com.omgservers.schema.entrypoint.docker.OAuth2DockerResponse;
import io.smallrye.mutiny.Uni;

public interface OAuth2Method {
    Uni<OAuth2DockerResponse> oAuth2(OAuth2DockerRequest request);
}
