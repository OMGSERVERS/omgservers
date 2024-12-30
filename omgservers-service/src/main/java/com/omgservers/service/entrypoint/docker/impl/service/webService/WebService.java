package com.omgservers.service.entrypoint.docker.impl.service.webService;

import com.omgservers.schema.entrypoint.docker.BasicAuthDockerRequest;
import com.omgservers.schema.entrypoint.docker.BasicAuthDockerResponse;
import com.omgservers.schema.entrypoint.docker.OAuth2DockerRequest;
import com.omgservers.schema.entrypoint.docker.OAuth2DockerResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<BasicAuthDockerResponse> basicAuth(BasicAuthDockerRequest request);

    Uni<OAuth2DockerResponse> oAuth2(OAuth2DockerRequest request);
}
