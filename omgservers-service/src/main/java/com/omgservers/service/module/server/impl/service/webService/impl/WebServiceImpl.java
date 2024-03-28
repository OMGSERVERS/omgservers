package com.omgservers.service.module.server.impl.service.webService.impl;

import com.omgservers.model.dto.server.DeleteServerContainerRequest;
import com.omgservers.model.dto.server.DeleteServerContainerResponse;
import com.omgservers.model.dto.server.DeleteServerRequest;
import com.omgservers.model.dto.server.DeleteServerResponse;
import com.omgservers.model.dto.server.GetServerContainerRequest;
import com.omgservers.model.dto.server.GetServerContainerResponse;
import com.omgservers.model.dto.server.GetServerRequest;
import com.omgservers.model.dto.server.GetServerResponse;
import com.omgservers.model.dto.server.SyncServerContainerRequest;
import com.omgservers.model.dto.server.SyncServerContainerResponse;
import com.omgservers.model.dto.server.SyncServerRequest;
import com.omgservers.model.dto.server.SyncServerResponse;
import com.omgservers.model.dto.server.ViewServerContainersRequest;
import com.omgservers.model.dto.server.ViewServerContainersResponse;
import com.omgservers.service.module.server.impl.service.serverService.ServerService;
import com.omgservers.service.module.server.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class WebServiceImpl implements WebService {

    final ServerService serverService;

    @Override
    public Uni<GetServerResponse> getServer(final GetServerRequest request) {
        return serverService.getServer(request);
    }

    @Override
    public Uni<SyncServerResponse> syncServer(final SyncServerRequest request) {
        return serverService.syncServer(request);
    }

    @Override
    public Uni<DeleteServerResponse> deleteServer(final DeleteServerRequest request) {
        return serverService.deleteServer(request);
    }

    @Override
    public Uni<GetServerContainerResponse> getServerContainer(final GetServerContainerRequest request) {
        return serverService.getServerContainer(request);
    }

    @Override
    public Uni<ViewServerContainersResponse> viewServerContainers(final ViewServerContainersRequest request) {
        return serverService.viewServerContainers(request);
    }

    @Override
    public Uni<SyncServerContainerResponse> syncServerContainer(final SyncServerContainerRequest request) {
        return serverService.syncServerContainer(request);
    }

    @Override
    public Uni<DeleteServerContainerResponse> deleteServerContainer(final DeleteServerContainerRequest request) {
        return serverService.deleteServerContainer(request);
    }
}
