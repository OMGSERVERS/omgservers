package com.omgservers.module.runtime.impl.service.runtimeService.impl;

import com.omgservers.model.dto.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeGrantRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeGrantResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeResponse;
import com.omgservers.model.dto.runtime.FindRuntimeGrantRequest;
import com.omgservers.model.dto.runtime.FindRuntimeGrantResponse;
import com.omgservers.model.dto.runtime.FindRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.FindRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.dto.runtime.HandleRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.HandleRuntimeCommandsResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeGrantRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeGrantResponse;
import com.omgservers.model.dto.runtime.SyncRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.SyncRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.module.runtime.impl.operation.getRuntimeModuleClient.GetRuntimeModuleClientOperation;
import com.omgservers.module.runtime.impl.operation.getRuntimeModuleClient.RuntimeModuleClient;
import com.omgservers.module.runtime.impl.service.runtimeService.RuntimeService;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.deleteRuntime.DeleteRuntimeMethod;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.deleteRuntimeCommand.DeleteRuntimeCommandMethod;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.deleteRuntimeCommands.DeleteRuntimeCommandsMethod;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.deleteRuntimeGrant.DeleteRuntimeGrantMethod;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.deleteRuntimePermission.DeleteRuntimePermissionMethod;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.findRuntimeGrant.FindRuntimeGrantMethod;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.findRuntimePermission.FindRuntimePermissionMethod;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.getRuntime.GetRuntimeMethod;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.handleRuntimeCommands.HandleRuntimeCommandsMethod;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.syncRuntime.SyncRuntimeMethod;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.syncRuntimeCommand.SyncRuntimeCommandMethod;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.syncRuntimeGrant.SyncRuntimeGrantMethod;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.syncRuntimePermission.SyncRuntimePermissionMethod;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.viewRuntimeCommands.ViewRuntimeCommandsMethod;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
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
    final SyncRuntimePermissionMethod syncRuntimePermissionMethod;
    final FindRuntimePermissionMethod findRuntimePermissionMethod;
    final DeleteRuntimeCommandsMethod deleteRuntimeCommandsMethod;
    final DeleteRuntimeCommandMethod deleteRuntimeCommandMethod;
    final ViewRuntimeCommandsMethod viewRuntimeCommandsMethod;
    final SyncRuntimeCommandMethod syncRuntimeCommandMethod;
    final HandleRuntimeCommandsMethod handleRuntimeCommands;
    final DeleteRuntimeGrantMethod deleteRuntimeGrantMethod;
    final SyncRuntimeGrantMethod syncRuntimeGrantMethod;
    final FindRuntimeGrantMethod findRuntimeGrant;
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
    public Uni<HandleRuntimeCommandsResponse> handleRuntimeCommands(@Valid final HandleRuntimeCommandsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::handleRuntimeCommands,
                handleRuntimeCommands::handleRuntimeCommands);
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
    public Uni<SyncRuntimeGrantResponse> syncRuntimeGrant(@Valid final SyncRuntimeGrantRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::syncRuntimeGrant,
                syncRuntimeGrantMethod::syncRuntimeGrant);
    }

    @Override
    public Uni<FindRuntimeGrantResponse> findRuntimeGrant(@Valid final FindRuntimeGrantRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::findRuntimeGrant,
                findRuntimeGrant::findRuntimeGrant);
    }

    @Override
    public Uni<DeleteRuntimeGrantResponse> deleteRuntimeGrant(@Valid final DeleteRuntimeGrantRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::deleteRuntimeGrant,
                deleteRuntimeGrantMethod::deleteRuntimeGrant);
    }
}
