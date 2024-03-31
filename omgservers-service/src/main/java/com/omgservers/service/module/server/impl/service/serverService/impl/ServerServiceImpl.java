package com.omgservers.service.module.server.impl.service.serverService.impl;

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
import com.omgservers.service.module.server.impl.operation.getServerModuleClient.GetServerModuleClientOperation;
import com.omgservers.service.module.server.impl.operation.getServerModuleClient.ServerModuleClient;
import com.omgservers.service.module.server.impl.service.serverService.ServerService;
import com.omgservers.service.module.server.impl.service.serverService.impl.method.server.deleteServer.DeleteServerMethod;
import com.omgservers.service.module.server.impl.service.serverService.impl.method.serverContainer.deleteServerContainer.DeleteServerContainerMethod;
import com.omgservers.service.module.server.impl.service.serverService.impl.method.server.getServer.GetServerMethod;
import com.omgservers.service.module.server.impl.service.serverService.impl.method.serverContainer.getServerContainer.GetServerContainerMethod;
import com.omgservers.service.module.server.impl.service.serverService.impl.method.server.syncServer.SyncServerMethod;
import com.omgservers.service.module.server.impl.service.serverService.impl.method.serverContainer.syncServerContainer.SyncServerContainerMethod;
import com.omgservers.service.module.server.impl.service.serverService.impl.method.serverContainer.viewServerContainers.ViewServerContainersMethod;
import com.omgservers.service.operation.calculateShard.CalculateShardOperation;
import com.omgservers.service.operation.handleInternalRequest.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ServerServiceImpl implements ServerService {

    final DeleteServerContainerMethod deleteServerContainerMethod;
    final ViewServerContainersMethod viewServerContainersMethod;
    final SyncServerContainerMethod syncServerContainerMethod;
    final GetServerContainerMethod getServerContainerMethod;
    final DeleteServerMethod deleteServerMethod;
    final SyncServerMethod syncServerMethod;
    final GetServerMethod getServerMethod;

    final GetServerModuleClientOperation getServerModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetServerResponse> getServer(@Valid final GetServerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getServerModuleClientOperation::getClient,
                ServerModuleClient::getServer,
                getServerMethod::getServer);
    }

    @Override
    public Uni<SyncServerResponse> syncServer(@Valid final SyncServerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getServerModuleClientOperation::getClient,
                ServerModuleClient::syncServer,
                syncServerMethod::syncServer);
    }

    @Override
    public Uni<DeleteServerResponse> deleteServer(@Valid final DeleteServerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getServerModuleClientOperation::getClient,
                ServerModuleClient::deleteServer,
                deleteServerMethod::deleteServer);
    }

    @Override
    public Uni<GetServerContainerResponse> getServerContainer(@Valid final GetServerContainerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getServerModuleClientOperation::getClient,
                ServerModuleClient::getServerContainer,
                getServerContainerMethod::getServerContainer);
    }

    @Override
    public Uni<ViewServerContainersResponse> viewServerContainers(@Valid final ViewServerContainersRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getServerModuleClientOperation::getClient,
                ServerModuleClient::viewServerContainers,
                viewServerContainersMethod::viewServerContainers);
    }

    @Override
    public Uni<SyncServerContainerResponse> syncServerContainer(@Valid final SyncServerContainerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getServerModuleClientOperation::getClient,
                ServerModuleClient::syncServerContainer,
                syncServerContainerMethod::syncServerContainer);
    }

    @Override
    public Uni<DeleteServerContainerResponse> deleteServerContainer(@Valid final DeleteServerContainerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getServerModuleClientOperation::getClient,
                ServerModuleClient::deleteServerContainer,
                deleteServerContainerMethod::deleteServerContainer);
    }
}
