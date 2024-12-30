package com.omgservers.service.entrypoint.docker.impl.service.webService.impl.dockerApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.entrypoint.docker.BasicAuthDockerRequest;
import com.omgservers.schema.entrypoint.docker.BasicAuthDockerResponse;
import com.omgservers.schema.entrypoint.docker.OAuth2DockerRequest;
import com.omgservers.schema.entrypoint.docker.OAuth2DockerResponse;
import com.omgservers.service.entrypoint.docker.impl.service.webService.WebService;
import com.omgservers.service.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.BeanParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DockerApiImpl implements DockerApi {

    final WebService webService;

    final HandleApiRequestOperation handleApiRequestOperation;
    final ObjectMapper objectMapper;

    @Override
    @PermitAll
    public Uni<BasicAuthDockerResponse> basicAuth(@NotNull @BeanParam final BasicAuthDockerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::basicAuth);
    }

    @Override
    @PermitAll
    public Uni<OAuth2DockerResponse> oAuth2(@NotNull @BeanParam OAuth2DockerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::oAuth2);
    }
}

