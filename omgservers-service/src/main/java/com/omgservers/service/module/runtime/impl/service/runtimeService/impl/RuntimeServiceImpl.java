package com.omgservers.service.module.runtime.impl.service.runtimeService.impl;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.runtime.CountRuntimeAssignmentsRequest;
import com.omgservers.schema.module.runtime.CountRuntimeAssignmentsResponse;
import com.omgservers.schema.module.runtime.DeleteRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimeAssignmentResponse;
import com.omgservers.schema.module.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimeCommandResponse;
import com.omgservers.schema.module.runtime.DeleteRuntimeCommandsRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimeCommandsResponse;
import com.omgservers.schema.module.runtime.DeleteRuntimePermissionRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimePermissionResponse;
import com.omgservers.schema.module.runtime.DeleteRuntimeRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimeResponse;
import com.omgservers.schema.module.runtime.FindRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.FindRuntimeAssignmentResponse;
import com.omgservers.schema.module.runtime.FindRuntimePermissionRequest;
import com.omgservers.schema.module.runtime.FindRuntimePermissionResponse;
import com.omgservers.schema.module.runtime.GetRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.GetRuntimeAssignmentResponse;
import com.omgservers.schema.module.runtime.GetRuntimeRequest;
import com.omgservers.schema.module.runtime.GetRuntimeResponse;
import com.omgservers.schema.module.runtime.InterchangeRequest;
import com.omgservers.schema.module.runtime.InterchangeResponse;
import com.omgservers.schema.module.runtime.SyncClientCommandRequest;
import com.omgservers.schema.module.runtime.SyncClientCommandResponse;
import com.omgservers.schema.module.runtime.SyncRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.SyncRuntimeAssignmentResponse;
import com.omgservers.schema.module.runtime.SyncRuntimeCommandRequest;
import com.omgservers.schema.module.runtime.SyncRuntimeCommandResponse;
import com.omgservers.schema.module.runtime.SyncRuntimePermissionRequest;
import com.omgservers.schema.module.runtime.SyncRuntimePermissionResponse;
import com.omgservers.schema.module.runtime.SyncRuntimeRequest;
import com.omgservers.schema.module.runtime.SyncRuntimeResponse;
import com.omgservers.schema.module.runtime.UpdateRuntimeAssignmentLastActivityRequest;
import com.omgservers.schema.module.runtime.UpdateRuntimeAssignmentLastActivityResponse;
import com.omgservers.schema.module.runtime.ViewRuntimeAssignmentsRequest;
import com.omgservers.schema.module.runtime.ViewRuntimeAssignmentsResponse;
import com.omgservers.schema.module.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.schema.module.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.schema.module.runtime.ViewRuntimePermissionsRequest;
import com.omgservers.schema.module.runtime.ViewRuntimePermissionsResponse;
import com.omgservers.schema.module.runtime.poolServerContainerRef.DeleteRuntimePoolServerContainerRefRequest;
import com.omgservers.schema.module.runtime.poolServerContainerRef.DeleteRuntimePoolServerContainerRefResponse;
import com.omgservers.schema.module.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefRequest;
import com.omgservers.schema.module.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefResponse;
import com.omgservers.schema.module.runtime.poolServerContainerRef.GetRuntimePoolServerContainerRefRequest;
import com.omgservers.schema.module.runtime.poolServerContainerRef.GetRuntimePoolServerContainerRefResponse;
import com.omgservers.schema.module.runtime.poolServerContainerRef.SyncRuntimePoolServerContainerRefRequest;
import com.omgservers.schema.module.runtime.poolServerContainerRef.SyncRuntimePoolServerContainerRefResponse;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.runtime.impl.operation.getRuntimeModuleClient.GetRuntimeModuleClientOperation;
import com.omgservers.service.module.runtime.impl.operation.getRuntimeModuleClient.RuntimeModuleClient;
import com.omgservers.service.module.runtime.impl.service.runtimeService.RuntimeService;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtime.deleteRuntime.DeleteRuntimeMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtime.getRuntime.GetRuntimeMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtime.interchange.InterchangeMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtime.syncRuntime.SyncRuntimeMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.countRuntimeAssignments.CountRuntimeAssignmentsMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.deleteRuntimeAssignment.DeleteRuntimeAssignmentMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.findRuntimeAssignment.FindRuntimeAssignmentMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.getRuntimeAssignment.GetRuntimeAssignmentMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.syncRuntimeAssignment.SyncRuntimeAssignmentMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.updateRuntimeAssignmentLastActivity.UpdateRuntimeAssignmentLastActivityMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.viewRuntimeAssignments.ViewRuntimeAssignmentsMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeCommand.deleteRuntimeCommand.DeleteRuntimeCommandMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeCommand.deleteRuntimeCommands.DeleteRuntimeCommandsMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeCommand.syncClientCommand.SyncClientCommandMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeCommand.syncRuntimeCommand.SyncRuntimeCommandMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeCommand.viewRuntimeCommands.ViewRuntimeCommandsMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePermission.deleteRuntimePermission.DeleteRuntimePermissionMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePermission.findRuntimePermission.FindRuntimePermissionMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePermission.syncRuntimePermission.SyncRuntimePermissionMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePermission.viewRuntimePermissions.ViewRuntimePermissionsMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePoolServerContainerRef.deleteRuntimePoolServerContainerRef.DeleteRuntimePoolServerContainerRefMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePoolServerContainerRef.findRuntimePoolServerContainerRef.FindRuntimePoolServerContainerRefMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePoolServerContainerRef.getRuntimePoolServerContainerRef.GetRuntimePoolServerContainerRefMethod;
import com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePoolServerContainerRef.syncRuntimePoolServerContainerRef.SyncRuntimePoolServerContainerRefMethod;
import com.omgservers.service.operation.handleInternalRequest.HandleShardedRequestOperation;
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

    final DeleteRuntimePoolServerContainerRefMethod deleteRuntimePoolServerContainerRefMethod;
    final FindRuntimePoolServerContainerRefMethod findRuntimePoolServerContainerRefMethod;
    final SyncRuntimePoolServerContainerRefMethod syncRuntimePoolServerContainerRefMethod;
    final GetRuntimePoolServerContainerRefMethod getRuntimePoolServerContainerRefMethod;
    final UpdateRuntimeAssignmentLastActivityMethod updateRuntimeAssignmentLastActivity;
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
    final HandleShardedRequestOperation handleShardedRequestOperation;

    @Override
    public Uni<SyncRuntimeResponse> syncRuntime(@Valid final SyncRuntimeRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::syncLobbyRuntime,
                syncRuntimeMethod::syncRuntime);
    }

    @Override
    public Uni<SyncRuntimeResponse> syncRuntimeWithIdempotency(SyncRuntimeRequest request) {
        return syncRuntime(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", request.getRuntime(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncRuntimeResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<GetRuntimeResponse> getRuntime(@Valid final GetRuntimeRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::getRuntime,
                getRuntimeMethod::getRuntime);
    }

    @Override
    public Uni<DeleteRuntimeResponse> deleteRuntime(@Valid final DeleteRuntimeRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::deleteRuntime,
                deleteRuntimeMethod::deleteRuntime);
    }

    @Override
    public Uni<SyncRuntimePermissionResponse> syncRuntimePermission(@Valid final SyncRuntimePermissionRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::syncRuntimePermission,
                syncRuntimePermissionMethod::syncRuntimePermission);
    }

    @Override
    public Uni<ViewRuntimePermissionsResponse> viewRuntimePermissions(
            @Valid final ViewRuntimePermissionsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::viewRuntimePermissions,
                viewRuntimePermissionsMethod::viewRuntimePermissions);
    }

    @Override
    public Uni<FindRuntimePermissionResponse> findRuntimePermission(@Valid final FindRuntimePermissionRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::findRuntimePermission,
                findRuntimePermissionMethod::findRuntimePermission);
    }

    @Override
    public Uni<DeleteRuntimePermissionResponse> deleteRuntimePermission(
            @Valid final DeleteRuntimePermissionRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::deleteRuntimePermission,
                deleteRuntimePermissionMethod::deleteRuntimePermission);
    }

    @Override
    public Uni<ViewRuntimeCommandsResponse> viewRuntimeCommands(@Valid final ViewRuntimeCommandsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::viewRuntimeCommands,
                viewRuntimeCommandsMethod::viewRuntimeCommands);
    }

    @Override
    public Uni<SyncRuntimeCommandResponse> syncRuntimeCommand(@Valid final SyncRuntimeCommandRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::syncRuntimeCommand,
                syncRuntimeCommandMethod::syncRuntimeCommand);
    }

    @Override
    public Uni<SyncClientCommandResponse> syncClientCommand(@Valid final SyncClientCommandRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::syncClientCommand,
                syncClientCommandMethod::syncClientCommand);
    }

    @Override
    public Uni<SyncClientCommandResponse> syncClientCommandWithIdempotency(SyncClientCommandRequest request) {
        return syncClientCommand(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", request.getRuntimeCommand(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncClientCommandResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteRuntimeCommandResponse> deleteRuntimeCommand(@Valid final DeleteRuntimeCommandRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::deleteRuntimeCommand,
                deleteRuntimeCommandMethod::deleteRuntimeCommand);
    }

    @Override
    public Uni<DeleteRuntimeCommandsResponse> deleteRuntimeCommands(@Valid final DeleteRuntimeCommandsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::deleteRuntimeCommands,
                deleteRuntimeCommandsMethod::deleteRuntimeCommands);
    }

    @Override
    public Uni<GetRuntimeAssignmentResponse> getRuntimeAssignment(@Valid final GetRuntimeAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::getRuntimeAssignment,
                getRuntimeAssignmentMethod::getRuntimeAssignment);
    }

    @Override
    public Uni<FindRuntimeAssignmentResponse> findRuntimeAssignment(@Valid final FindRuntimeAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::findRuntimeAssignment,
                findRuntimeAssignmentMethod::findRuntimeAssignment);
    }

    @Override
    public Uni<ViewRuntimeAssignmentsResponse> viewRuntimeAssignments(
            @Valid final ViewRuntimeAssignmentsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::viewRuntimeAssignments,
                viewRuntimeAssignmentsMethod::viewRuntimeAssignments);
    }

    @Override
    public Uni<CountRuntimeAssignmentsResponse> countRuntimeAssignments(
            @Valid final CountRuntimeAssignmentsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::countRuntimeAssignments,
                countRuntimeAssignmentsMethod::countRuntimeAssignments);
    }

    @Override
    public Uni<SyncRuntimeAssignmentResponse> syncRuntimeAssignment(@Valid final SyncRuntimeAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::syncRuntimeAssignment,
                syncRuntimeAssignmentMethod::syncRuntimeAssignment);
    }

    @Override
    public Uni<UpdateRuntimeAssignmentLastActivityResponse> updateRuntimeAssignmentLastActivity(
            @Valid final UpdateRuntimeAssignmentLastActivityRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::updateRuntimeAssignmentLastActivity,
                updateRuntimeAssignmentLastActivity::updateRuntimeAssignmentLastActivity);
    }

    @Override
    public Uni<DeleteRuntimeAssignmentResponse> deleteRuntimeAssignment(
            @Valid final DeleteRuntimeAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::deleteRuntimeAssignment,
                deleteRuntimeAssignmentMethod::deleteRuntimeAssignment);
    }

    @Override
    public Uni<GetRuntimePoolServerContainerRefResponse> getRuntimePoolServerContainerRef(
            @Valid final GetRuntimePoolServerContainerRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::getRuntimePoolServerContainerRef,
                getRuntimePoolServerContainerRefMethod::getRuntimePoolServerContainerRef);
    }

    @Override
    public Uni<FindRuntimePoolServerContainerRefResponse> findRuntimePoolServerContainerRef(
            @Valid final FindRuntimePoolServerContainerRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::findRuntimePoolServerContainerRef,
                findRuntimePoolServerContainerRefMethod::findRuntimePoolServerContainerRef);
    }

    @Override
    public Uni<SyncRuntimePoolServerContainerRefResponse> syncRuntimePoolServerContainerRef(
            @Valid final SyncRuntimePoolServerContainerRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::syncRuntimePoolServerContainerRef,
                syncRuntimePoolServerContainerRefMethod::syncRuntimePoolServerContainerRef);
    }

    @Override
    public Uni<DeleteRuntimePoolServerContainerRefResponse> deleteRuntimePoolServerContainerRef(
            @Valid final DeleteRuntimePoolServerContainerRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::deleteRuntimePoolServerContainerRef,
                deleteRuntimePoolServerContainerRefMethod::deleteRuntimePoolServerContainerRef);
    }

    @Override
    public Uni<InterchangeResponse> interchange(@Valid final InterchangeRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::interchange,
                interchangeMethod::interchange);
    }
}
