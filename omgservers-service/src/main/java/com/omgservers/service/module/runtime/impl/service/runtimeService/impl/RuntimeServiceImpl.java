package com.omgservers.service.module.runtime.impl.service.runtimeService.impl;

import com.omgservers.model.dto.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeClientRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeClientResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeResponse;
import com.omgservers.model.dto.runtime.FindRuntimeClientRequest;
import com.omgservers.model.dto.runtime.FindRuntimeClientResponse;
import com.omgservers.model.dto.runtime.FindRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.FindRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeClientRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeClientResponse;
import com.omgservers.model.dto.runtime.SyncRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.SyncRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeClientsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeClientsResponse;
import com.omgservers.model.dto.runtime.ViewRuntimePermissionsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimePermissionsResponse;
import com.omgservers.service.module.runtime.impl.operation.getRuntimeModuleClient.GetRuntimeModuleClientOperation;
import com.omgservers.service.module.runtime.impl.operation.getRuntimeModuleClient.RuntimeModuleClient;
import com.omgservers.service.module.runtime.impl.service.runtimeService.RuntimeService;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.deleteRuntime.DeleteRuntimeMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.deleteRuntimeCommand.DeleteRuntimeCommandMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.deleteRuntimeCommands.DeleteRuntimeCommandsMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.deleteRuntimeClient.DeleteRuntimeClientMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.deleteRuntimePermission.DeleteRuntimePermissionMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.findRuntimeClient.FindRuntimeClientMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.findRuntimePermission.FindRuntimePermissionMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.getRuntime.GetRuntimeMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.syncRuntime.SyncRuntimeMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.syncRuntimeCommand.SyncRuntimeCommandMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.syncRuntimeClient.SyncRuntimeClientMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.syncRuntimePermission.SyncRuntimePermissionMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.viewRuntimeCommands.ViewRuntimeCommandsMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.viewRuntimeClients.ViewRuntimeClientsMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.viewRuntimePermissions.ViewRuntimePermissionsMethod;
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
public class RuntimeServiceImpl implements RuntimeService {

    final DeleteRuntimePermissionMethod deleteRuntimePermissionMethod;
    final ViewRuntimePermissionsMethod viewRuntimePermissionsMethod;
    final SyncRuntimePermissionMethod syncRuntimePermissionMethod;
    final FindRuntimePermissionMethod findRuntimePermissionMethod;
    final DeleteRuntimeCommandsMethod deleteRuntimeCommandsMethod;
    final DeleteRuntimeCommandMethod deleteRuntimeCommandMethod;
    final ViewRuntimeCommandsMethod viewRuntimeCommandsMethod;
    final SyncRuntimeCommandMethod syncRuntimeCommandMethod;
    final DeleteRuntimeClientMethod deleteRuntimeClientMethod;
    final ViewRuntimeClientsMethod viewRuntimeClientsMethod;
    final SyncRuntimeClientMethod syncRuntimeClientMethod;
    final FindRuntimeClientMethod findRuntimeClient;
    final DeleteRuntimeMethod deleteRuntimeMethod;
    final SyncRuntimeMethod syncRuntimeMethod;
    final GetRuntimeMethod getRuntimeMethod;

    final GetRuntimeModuleClientOperation getRuntimeModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;

    @Override
    public Uni<SyncRuntimeResponse> syncRuntime(@Valid final SyncRuntimeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::syncRuntime,
                syncRuntimeMethod::syncRuntime);
    }

    @Override
    public Uni<GetRuntimeResponse> getRuntime(@Valid final GetRuntimeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::getRuntime,
                getRuntimeMethod::getRuntime);
    }

    @Override
    public Uni<DeleteRuntimeResponse> deleteRuntime(@Valid final DeleteRuntimeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::deleteRuntime,
                deleteRuntimeMethod::deleteRuntime);
    }

    @Override
    public Uni<SyncRuntimePermissionResponse> syncRuntimePermission(@Valid final SyncRuntimePermissionRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::syncRuntimePermission,
                syncRuntimePermissionMethod::syncRuntimePermission);
    }

    @Override
    public Uni<ViewRuntimePermissionsResponse> viewRuntimePermissions(
            @Valid final ViewRuntimePermissionsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::viewRuntimePermissions,
                viewRuntimePermissionsMethod::viewRuntimePermissions);
    }

    @Override
    public Uni<FindRuntimePermissionResponse> findRuntimePermission(@Valid final FindRuntimePermissionRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::findRuntimePermission,
                findRuntimePermissionMethod::findRuntimePermission);
    }

    @Override
    public Uni<DeleteRuntimePermissionResponse> deleteRuntimePermission(
            @Valid final DeleteRuntimePermissionRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::deleteRuntimePermission,
                deleteRuntimePermissionMethod::deleteRuntimePermission);
    }

    @Override
    public Uni<ViewRuntimeCommandsResponse> viewRuntimeCommands(@Valid final ViewRuntimeCommandsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::viewRuntimeCommands,
                viewRuntimeCommandsMethod::viewRuntimeCommands);
    }

    @Override
    public Uni<SyncRuntimeCommandResponse> syncRuntimeCommand(@Valid final SyncRuntimeCommandRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::syncRuntimeCommand,
                syncRuntimeCommandMethod::syncRuntimeCommand);
    }

    @Override
    public Uni<DeleteRuntimeCommandResponse> deleteRuntimeCommand(@Valid final DeleteRuntimeCommandRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::deleteRuntimeCommand,
                deleteRuntimeCommandMethod::deleteRuntimeCommand);
    }

    @Override
    public Uni<DeleteRuntimeCommandsResponse> deleteRuntimeCommands(@Valid final DeleteRuntimeCommandsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::deleteRuntimeCommands,
                deleteRuntimeCommandsMethod::deleteRuntimeCommands);
    }

    @Override
    public Uni<SyncRuntimeClientResponse> syncRuntimeClient(@Valid final SyncRuntimeClientRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::syncRuntimeClient,
                syncRuntimeClientMethod::syncRuntimeClient);
    }

    @Override
    public Uni<ViewRuntimeClientsResponse> viewRuntimeClients(ViewRuntimeClientsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::viewRuntimeClients,
                viewRuntimeClientsMethod::viewRuntimeClients);
    }

    @Override
    public Uni<FindRuntimeClientResponse> findRuntimeClient(@Valid final FindRuntimeClientRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::findRuntimeClient,
                findRuntimeClient::findRuntimeClient);
    }

    @Override
    public Uni<DeleteRuntimeClientResponse> deleteRuntimeClient(@Valid final DeleteRuntimeClientRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::deleteRuntimeClient,
                deleteRuntimeClientMethod::deleteRuntimeClient);
    }
}
