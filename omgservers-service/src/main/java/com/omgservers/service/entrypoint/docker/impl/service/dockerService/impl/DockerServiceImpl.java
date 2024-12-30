package com.omgservers.service.entrypoint.docker.impl.service.dockerService.impl;

import com.omgservers.schema.entrypoint.docker.BasicAuthDockerRequest;
import com.omgservers.schema.entrypoint.docker.BasicAuthDockerResponse;
import com.omgservers.schema.entrypoint.docker.OAuth2DockerRequest;
import com.omgservers.schema.entrypoint.docker.OAuth2DockerResponse;
import com.omgservers.service.entrypoint.docker.impl.service.dockerService.DockerService;
import com.omgservers.service.entrypoint.docker.impl.service.dockerService.impl.method.BasicAuthMethod;
import com.omgservers.service.entrypoint.docker.impl.service.dockerService.impl.method.OAuth2Method;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DockerServiceImpl implements DockerService {

    final BasicAuthMethod basicAuthMethod;
    final OAuth2Method oAuth2Method;

    @Override
    public Uni<BasicAuthDockerResponse> basicAuth(@Valid final BasicAuthDockerRequest request) {
        return basicAuthMethod.basicAuth(request);
    }

    @Override
    public Uni<OAuth2DockerResponse> oAuth2(@Valid final OAuth2DockerRequest request) {
        return oAuth2Method.oAuth2(request);
    }
}
