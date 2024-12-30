package com.omgservers.service.entrypoint.docker.impl.service.webService.impl;

import com.omgservers.schema.entrypoint.docker.BasicAuthDockerRequest;
import com.omgservers.schema.entrypoint.docker.BasicAuthDockerResponse;
import com.omgservers.schema.entrypoint.docker.OAuth2DockerRequest;
import com.omgservers.schema.entrypoint.docker.OAuth2DockerResponse;
import com.omgservers.service.entrypoint.docker.impl.service.dockerService.DockerService;
import com.omgservers.service.entrypoint.docker.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class WebServiceImpl implements WebService {

    final DockerService dockerService;

    @Override
    public Uni<BasicAuthDockerResponse> basicAuth(final BasicAuthDockerRequest request) {
        return dockerService.basicAuth(request);
    }

    @Override
    public Uni<OAuth2DockerResponse> oAuth2(final OAuth2DockerRequest request) {
        return dockerService.oAuth2(request);
    }
}
