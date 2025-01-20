package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl;

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
import com.omgservers.schema.module.runtime.poolContainerRef.DeleteRuntimePoolContainerRefRequest;
import com.omgservers.schema.module.runtime.poolContainerRef.DeleteRuntimePoolContainerRefResponse;
import com.omgservers.schema.module.runtime.poolContainerRef.FindRuntimePoolContainerRefRequest;
import com.omgservers.schema.module.runtime.poolContainerRef.FindRuntimePoolContainerRefResponse;
import com.omgservers.schema.module.runtime.poolContainerRef.GetRuntimePoolContainerRefRequest;
import com.omgservers.schema.module.runtime.poolContainerRef.GetRuntimePoolContainerRefResponse;
import com.omgservers.schema.module.runtime.poolContainerRef.SyncRuntimePoolContainerRefRequest;
import com.omgservers.schema.module.runtime.poolContainerRef.SyncRuntimePoolContainerRefResponse;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.shard.runtime.impl.operation.getRuntimeModuleClient.GetRuntimeModuleClientOperation;
import com.omgservers.service.shard.runtime.impl.operation.getRuntimeModuleClient.RuntimeModuleClient;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.RuntimeService;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtime.DeleteRuntimeMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtime.GetRuntimeMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtime.InterchangeMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtime.SyncRuntimeMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.CountRuntimeAssignmentsMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.DeleteRuntimeAssignmentMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.FindRuntimeAssignmentMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.GetRuntimeAssignmentMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.SyncRuntimeAssignmentMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.UpdateRuntimeAssignmentLastActivityMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.ViewRuntimeAssignmentsMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeCommand.DeleteRuntimeCommandMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeCommand.DeleteRuntimeCommandsMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeCommand.SyncClientCommandMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeCommand.SyncRuntimeCommandMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeCommand.ViewRuntimeCommandsMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimePermission.DeleteRuntimePermissionMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimePermission.FindRuntimePermissionMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimePermission.SyncRuntimePermissionMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimePermission.ViewRuntimePermissionsMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimePoolContainerRef.DeleteRuntimePoolContainerRefMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimePoolContainerRef.FindRuntimePoolContainerRefMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimePoolContainerRef.GetRuntimePoolContainerRefMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimePoolContainerRef.SyncRuntimePoolContainerRefMethod;
import com.omgservers.service.operation.server.HandleShardedRequestOperation;
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

    final DeleteRuntimePoolContainerRefMethod deleteRuntimePoolContainerRefMethod;
    final FindRuntimePoolContainerRefMethod findRuntimePoolContainerRefMethod;
    final SyncRuntimePoolContainerRefMethod syncRuntimePoolContainerRefMethod;
    final GetRuntimePoolContainerRefMethod getRuntimePoolContainerRefMethod;
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
    public Uni<SyncRuntimeResponse> execute(@Valid final SyncRuntimeRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                syncRuntimeMethod::execute);
    }

    @Override
    public Uni<SyncRuntimeResponse> executeWithIdempotency(SyncRuntimeRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getRuntime(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncRuntimeResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<GetRuntimeResponse> execute(@Valid final GetRuntimeRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                getRuntimeMethod::execute);
    }

    @Override
    public Uni<DeleteRuntimeResponse> execute(@Valid final DeleteRuntimeRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                deleteRuntimeMethod::execute);
    }

    @Override
    public Uni<SyncRuntimePermissionResponse> execute(@Valid final SyncRuntimePermissionRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                syncRuntimePermissionMethod::execute);
    }

    @Override
    public Uni<ViewRuntimePermissionsResponse> execute(
            @Valid final ViewRuntimePermissionsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                viewRuntimePermissionsMethod::execute);
    }

    @Override
    public Uni<FindRuntimePermissionResponse> execute(@Valid final FindRuntimePermissionRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                findRuntimePermissionMethod::execute);
    }

    @Override
    public Uni<DeleteRuntimePermissionResponse> execute(
            @Valid final DeleteRuntimePermissionRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                deleteRuntimePermissionMethod::execute);
    }

    @Override
    public Uni<ViewRuntimeCommandsResponse> execute(@Valid final ViewRuntimeCommandsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                viewRuntimeCommandsMethod::execute);
    }

    @Override
    public Uni<SyncRuntimeCommandResponse> execute(@Valid final SyncRuntimeCommandRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                syncRuntimeCommandMethod::execute);
    }

    @Override
    public Uni<SyncClientCommandResponse> execute(@Valid final SyncClientCommandRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                syncClientCommandMethod::execute);
    }

    @Override
    public Uni<SyncClientCommandResponse> executeWithIdempotency(SyncClientCommandRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getRuntimeCommand(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncClientCommandResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteRuntimeCommandResponse> execute(@Valid final DeleteRuntimeCommandRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                deleteRuntimeCommandMethod::execute);
    }

    @Override
    public Uni<DeleteRuntimeCommandsResponse> execute(@Valid final DeleteRuntimeCommandsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                deleteRuntimeCommandsMethod::execute);
    }

    @Override
    public Uni<GetRuntimeAssignmentResponse> execute(@Valid final GetRuntimeAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                getRuntimeAssignmentMethod::execute);
    }

    @Override
    public Uni<FindRuntimeAssignmentResponse> execute(@Valid final FindRuntimeAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                findRuntimeAssignmentMethod::execute);
    }

    @Override
    public Uni<ViewRuntimeAssignmentsResponse> execute(
            @Valid final ViewRuntimeAssignmentsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                viewRuntimeAssignmentsMethod::execute);
    }

    @Override
    public Uni<CountRuntimeAssignmentsResponse> execute(
            @Valid final CountRuntimeAssignmentsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                countRuntimeAssignmentsMethod::execute);
    }

    @Override
    public Uni<SyncRuntimeAssignmentResponse> execute(@Valid final SyncRuntimeAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                syncRuntimeAssignmentMethod::execute);
    }

    @Override
    public Uni<SyncRuntimeAssignmentResponse> executeWithIdempotency(SyncRuntimeAssignmentRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getRuntimeAssignment(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncRuntimeAssignmentResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<UpdateRuntimeAssignmentLastActivityResponse> execute(
            @Valid final UpdateRuntimeAssignmentLastActivityRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                updateRuntimeAssignmentLastActivity::execute);
    }

    @Override
    public Uni<DeleteRuntimeAssignmentResponse> execute(
            @Valid final DeleteRuntimeAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                deleteRuntimeAssignmentMethod::execute);
    }

    @Override
    public Uni<GetRuntimePoolContainerRefResponse> execute(
            @Valid final GetRuntimePoolContainerRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                getRuntimePoolContainerRefMethod::execute);
    }

    @Override
    public Uni<FindRuntimePoolContainerRefResponse> execute(
            @Valid final FindRuntimePoolContainerRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                findRuntimePoolContainerRefMethod::execute);
    }

    @Override
    public Uni<SyncRuntimePoolContainerRefResponse> execute(
            @Valid final SyncRuntimePoolContainerRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                syncRuntimePoolContainerRefMethod::execute);
    }

    @Override
    public Uni<DeleteRuntimePoolContainerRefResponse> execute(
            @Valid final DeleteRuntimePoolContainerRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                deleteRuntimePoolContainerRefMethod::execute);
    }

    @Override
    public Uni<InterchangeResponse> execute(@Valid final InterchangeRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                interchangeMethod::execute);
    }
}
