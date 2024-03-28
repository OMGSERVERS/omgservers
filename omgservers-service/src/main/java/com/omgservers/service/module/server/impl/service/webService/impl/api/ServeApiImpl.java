package com.omgservers.service.module.server.impl.service.webService.impl.api;

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
import com.omgservers.service.module.server.impl.service.webService.WebService;
import com.omgservers.service.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ServeApiImpl implements ServeApi {

    final WebService webService;

    final HandleApiRequestOperation handleApiRequestOperation;

    @Override
    public Uni<GetServerResponse> getServer(final GetServerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getServer);
    }

    @Override
    public Uni<SyncServerResponse> syncServer(final SyncServerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncServer);
    }

    @Override
    public Uni<DeleteServerResponse> deleteServer(final DeleteServerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteServer);
    }

    @Override
    public Uni<GetServerContainerResponse> getServerContainer(final GetServerContainerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getServerContainer);
    }

    @Override
    public Uni<ViewServerContainersResponse> viewServerContainers(final ViewServerContainersRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewServerContainers);
    }

    @Override
    public Uni<SyncServerContainerResponse> syncServerContainer(final SyncServerContainerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncServerContainer);
    }

    @Override
    public Uni<DeleteServerContainerResponse> deleteServerContainer(final DeleteServerContainerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteServerContainer);
    }
}
