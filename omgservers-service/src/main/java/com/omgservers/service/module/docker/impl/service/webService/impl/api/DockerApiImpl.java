package com.omgservers.service.module.docker.impl.service.webService.impl.api;

import com.omgservers.schema.model.internalRole.InternalRoleEnum;
import com.omgservers.schema.module.docker.StartDockerContainerRequest;
import com.omgservers.schema.module.docker.StartDockerContainerResponse;
import com.omgservers.schema.module.docker.StopDockerContainerRequest;
import com.omgservers.schema.module.docker.StopDockerContainerResponse;
import com.omgservers.service.module.docker.impl.service.webService.WebService;
import com.omgservers.service.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
@RolesAllowed({InternalRoleEnum.Names.SERVICE})
class DockerApiImpl implements DockerApi {

    final WebService webService;

    final HandleApiRequestOperation handleApiRequestOperation;

    @Override
    public Uni<StartDockerContainerResponse> startDockerContainer(final StartDockerContainerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::startDockerContainer);
    }

    @Override
    public Uni<StopDockerContainerResponse> stopDockerContainer(final StopDockerContainerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::stopDockerContainer);
    }
}
