package com.omgservers.service.module.runtime.impl.service.runtimeService.impl;

import com.omgservers.model.dto.runtime.CountRuntimeAssignmentsRequest;
import com.omgservers.model.dto.runtime.CountRuntimeAssignmentsResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeAssignmentRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeAssignmentResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeResponse;
import com.omgservers.model.dto.runtime.FindRuntimeAssignmentRequest;
import com.omgservers.model.dto.runtime.FindRuntimeAssignmentResponse;
import com.omgservers.model.dto.runtime.FindRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.FindRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.GetRuntimeAssignmentRequest;
import com.omgservers.model.dto.runtime.GetRuntimeAssignmentResponse;
import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.dto.runtime.InterchangeRequest;
import com.omgservers.model.dto.runtime.InterchangeResponse;
import com.omgservers.model.dto.runtime.SyncClientCommandRequest;
import com.omgservers.model.dto.runtime.SyncClientCommandResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeAssignmentRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeAssignmentResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.SyncRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.SyncRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeAssignmentsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeAssignmentsResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.model.dto.runtime.ViewRuntimePermissionsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimePermissionsResponse;
import com.omgservers.model.dto.runtime.serverRuntimeRef.DeleteRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.runtime.serverRuntimeRef.DeleteRuntimeServerContainerRefResponse;
import com.omgservers.model.dto.runtime.serverRuntimeRef.FindRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.runtime.serverRuntimeRef.FindRuntimeServerContainerRefResponse;
import com.omgservers.model.dto.runtime.serverRuntimeRef.GetRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.runtime.serverRuntimeRef.GetRuntimeServerContainerRefResponse;
import com.omgservers.model.dto.runtime.serverRuntimeRef.SyncRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.runtime.serverRuntimeRef.SyncRuntimeServerContainerRefResponse;
import com.omgservers.service.module.runtime.impl.operation.getRuntimeModuleClient.GetRuntimeModuleClientOperation;
import com.omgservers.service.module.runtime.impl.operation.getRuntimeModuleClient.RuntimeModuleClient;
import com.omgservers.service.module.runtime.impl.service.runtimeService.RuntimeService;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.countRuntimeAssignments.CountRuntimeAssignmentsMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtime.deleteRuntime.DeleteRuntimeMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.deleteRuntimeAssignment.DeleteRuntimeAssignmentMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeCommand.deleteRuntimeCommand.DeleteRuntimeCommandMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeCommand.deleteRuntimeCommands.DeleteRuntimeCommandsMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePermission.deleteRuntimePermission.DeleteRuntimePermissionMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeServerContainerRef.deleteRuntimeServerContainerRef.DeleteRuntimeServerContainerRefMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.findRuntimeAssignment.FindRuntimeAssignmentMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePermission.findRuntimePermission.FindRuntimePermissionMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeServerContainerRef.findRuntimeServerContainerRef.FindRuntimeServerContainerRefMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtime.getRuntime.GetRuntimeMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.getRuntimeAssignment.GetRuntimeAssignmentMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeServerContainerRef.getRuntimeServerContainerRef.GetRuntimeServerContainerRefMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtime.interchange.InterchangeMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeCommand.syncClientCommand.SyncClientCommandMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtime.syncRuntime.SyncRuntimeMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.syncRuntimeAssignment.SyncRuntimeAssignmentMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeCommand.syncRuntimeCommand.SyncRuntimeCommandMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePermission.syncRuntimePermission.SyncRuntimePermissionMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeServerContainerRef.syncRuntimeServerContainerRef.SyncRuntimeServerContainerRefMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.viewRuntimeAssignments.ViewRuntimeAssignmentsMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeCommand.viewRuntimeCommands.ViewRuntimeCommandsMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePermission.viewRuntimePermissions.ViewRuntimePermissionsMethod;
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

    final DeleteRuntimeServerContainerRefMethod deleteRuntimeServerContainerRefMethod;
    final FindRuntimeServerContainerRefMethod findRuntimeServerContainerRefMethod;
    final SyncRuntimeServerContainerRefMethod syncRuntimeServerContainerRefMethod;
    final GetRuntimeServerContainerRefMethod getRuntimeServerContainerRefMethod;
    final DeleteRuntimePermissionMethod deleteRuntimePermissionMethod;
    final DeleteRuntimeAssignmentMethod deleteRuntimeAssignmentMethod;
    final CountRuntimeAssignmentsMethod countRuntimeAssignmentsMethod;
    final ViewRuntimeAssignmentsMethod viewRuntimeAssignmentsMethod;
    final ViewRuntimePermissionsMethod viewRuntimePermissionsMethod;
    final SyncRuntimePermissionMethod syncRuntimePermissionMethod;
    final FindRuntimePermissionMethod findRuntimePermissionMethod;
    final DeleteRuntimeCommandsMethod deleteRuntimeCommandsMethod;
    final SyncRuntimeAssignmentMethod syncRuntimeAssignmentMethod;
    final FindRuntimeAssignmentMethod findRuntimeAssignmentMethod;
    final DeleteRuntimeCommandMethod deleteRuntimeCommandMethod;
    final GetRuntimeAssignmentMethod getRuntimeAssignmentMethod;
    final ViewRuntimeCommandsMethod viewRuntimeCommandsMethod;
    final SyncRuntimeCommandMethod syncRuntimeCommandMethod;
    final SyncClientCommandMethod syncClientCommandMethod;
    final DeleteRuntimeMethod deleteRuntimeMethod;
    final SyncRuntimeMethod syncRuntimeMethod;
    final InterchangeMethod interchangeMethod;
    final GetRuntimeMethod getRuntimeMethod;

    final GetRuntimeModuleClientOperation getRuntimeModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;

    @Override
    public Uni<SyncRuntimeResponse> syncRuntime(@Valid final SyncRuntimeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::syncLobbyRuntime,
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
    public Uni<SyncClientCommandResponse> syncClientCommand(@Valid final SyncClientCommandRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::syncClientCommand,
                syncClientCommandMethod::syncClientCommand);
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
    public Uni<GetRuntimeAssignmentResponse> getRuntimeAssignment(@Valid final GetRuntimeAssignmentRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::getRuntimeAssignment,
                getRuntimeAssignmentMethod::getRuntimeAssignment);
    }

    @Override
    public Uni<FindRuntimeAssignmentResponse> findRuntimeAssignment(@Valid final FindRuntimeAssignmentRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::findRuntimeAssignment,
                findRuntimeAssignmentMethod::findRuntimeAssignment);
    }

    @Override
    public Uni<ViewRuntimeAssignmentsResponse> viewRuntimeAssignments(
            @Valid final ViewRuntimeAssignmentsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::viewRuntimeAssignments,
                viewRuntimeAssignmentsMethod::viewRuntimeAssignments);
    }

    @Override
    public Uni<CountRuntimeAssignmentsResponse> countRuntimeAssignments(
            @Valid final CountRuntimeAssignmentsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::countRuntimeAssignments,
                countRuntimeAssignmentsMethod::countRuntimeAssignments);
    }

    @Override
    public Uni<SyncRuntimeAssignmentResponse> syncRuntimeAssignment(@Valid final SyncRuntimeAssignmentRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::syncRuntimeAssignment,
                syncRuntimeAssignmentMethod::syncRuntimeAssignment);
    }

    @Override
    public Uni<DeleteRuntimeAssignmentResponse> deleteRuntimeAssignment(
            @Valid final DeleteRuntimeAssignmentRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::deleteRuntimeAssignment,
                deleteRuntimeAssignmentMethod::deleteRuntimeAssignment);
    }

    @Override
    public Uni<GetRuntimeServerContainerRefResponse> getRuntimeServerContainerRef(
            @Valid final GetRuntimeServerContainerRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::getRuntimeServerContainerRef,
                getRuntimeServerContainerRefMethod::getRuntimeServerContainerRef);
    }

    @Override
    public Uni<FindRuntimeServerContainerRefResponse> findRuntimeServerContainerRef(
            @Valid final FindRuntimeServerContainerRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::findRuntimeServerContainerRef,
                findRuntimeServerContainerRefMethod::findRuntimeServerContainerRef);
    }

    @Override
    public Uni<SyncRuntimeServerContainerRefResponse> syncRuntimeServerContainerRef(
            @Valid final SyncRuntimeServerContainerRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::syncRuntimeServerContainerRef,
                syncRuntimeServerContainerRefMethod::syncRuntimeServerContainerRef);
    }

    @Override
    public Uni<DeleteRuntimeServerContainerRefResponse> deleteRuntimeServerContainerRef(
            @Valid final DeleteRuntimeServerContainerRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::deleteRuntimeServerContainerRef,
                deleteRuntimeServerContainerRefMethod::deleteRuntimeServerContainerRef);
    }

    @Override
    public Uni<InterchangeResponse> interchange(@Valid final InterchangeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::interchange,
                interchangeMethod::interchange);
    }
}
