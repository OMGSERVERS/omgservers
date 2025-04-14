package com.omgservers.service.shard.docker.impl.service.webService.impl.api;

import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.shard.docker.PingDockerHostRequest;
import com.omgservers.schema.shard.docker.PingDockerHostResponse;
import com.omgservers.schema.shard.docker.StartDockerContainerRequest;
import com.omgservers.schema.shard.docker.StartDockerContainerResponse;
import com.omgservers.schema.shard.docker.StopDockerContainerRequest;
import com.omgservers.schema.shard.docker.StopDockerContainerResponse;
import com.omgservers.service.shard.docker.impl.service.webService.WebService;
import com.omgservers.service.operation.server.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@RolesAllowed({UserRoleEnum.Names.SERVICE})
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DockerApiImpl implements DockerApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final WebService webService;

    /*
    Docker
     */

    @Override
    public Uni<PingDockerHostResponse> execute(final PingDockerHostRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<StartDockerContainerResponse> execute(final StartDockerContainerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<StopDockerContainerResponse> execute(final StopDockerContainerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }
}
