package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.shard.runtime.runtime.DeleteRuntimeRequest;
import com.omgservers.schema.shard.runtime.runtime.DeleteRuntimeResponse;
import com.omgservers.schema.shard.runtime.runtime.GetRuntimeRequest;
import com.omgservers.schema.shard.runtime.runtime.GetRuntimeResponse;
import com.omgservers.schema.shard.runtime.runtime.SyncRuntimeRequest;
import com.omgservers.schema.shard.runtime.runtime.SyncRuntimeResponse;
import com.omgservers.schema.shard.runtime.runtimeAssignment.DeleteRuntimeAssignmentRequest;
import com.omgservers.schema.shard.runtime.runtimeAssignment.DeleteRuntimeAssignmentResponse;
import com.omgservers.schema.shard.runtime.runtimeAssignment.FindRuntimeAssignmentRequest;
import com.omgservers.schema.shard.runtime.runtimeAssignment.FindRuntimeAssignmentResponse;
import com.omgservers.schema.shard.runtime.runtimeAssignment.GetRuntimeAssignmentRequest;
import com.omgservers.schema.shard.runtime.runtimeAssignment.GetRuntimeAssignmentResponse;
import com.omgservers.schema.shard.runtime.runtimeAssignment.SyncRuntimeAssignmentRequest;
import com.omgservers.schema.shard.runtime.runtimeAssignment.SyncRuntimeAssignmentResponse;
import com.omgservers.schema.shard.runtime.runtimeAssignment.ViewRuntimeAssignmentsRequest;
import com.omgservers.schema.shard.runtime.runtimeAssignment.ViewRuntimeAssignmentsResponse;
import com.omgservers.schema.shard.runtime.runtimeCommand.DeleteRuntimeCommandRequest;
import com.omgservers.schema.shard.runtime.runtimeCommand.DeleteRuntimeCommandResponse;
import com.omgservers.schema.shard.runtime.runtimeCommand.SyncRuntimeCommandRequest;
import com.omgservers.schema.shard.runtime.runtimeCommand.SyncRuntimeCommandResponse;
import com.omgservers.schema.shard.runtime.runtimeCommand.ViewRuntimeCommandsRequest;
import com.omgservers.schema.shard.runtime.runtimeCommand.ViewRuntimeCommandsResponse;
import com.omgservers.schema.shard.runtime.runtimeMessage.DeleteRuntimeMessageRequest;
import com.omgservers.schema.shard.runtime.runtimeMessage.DeleteRuntimeMessageResponse;
import com.omgservers.schema.shard.runtime.runtimeMessage.DeleteRuntimeMessagesRequest;
import com.omgservers.schema.shard.runtime.runtimeMessage.DeleteRuntimeMessagesResponse;
import com.omgservers.schema.shard.runtime.runtimeMessage.InterchangeMessagesRequest;
import com.omgservers.schema.shard.runtime.runtimeMessage.InterchangeMessagesResponse;
import com.omgservers.schema.shard.runtime.runtimeMessage.SyncRuntimeMessageRequest;
import com.omgservers.schema.shard.runtime.runtimeMessage.SyncRuntimeMessageResponse;
import com.omgservers.schema.shard.runtime.runtimeMessage.ViewRuntimeMessagesRequest;
import com.omgservers.schema.shard.runtime.runtimeMessage.ViewRuntimeMessagesResponse;
import com.omgservers.schema.shard.runtime.runtimeState.GetRuntimeStateRequest;
import com.omgservers.schema.shard.runtime.runtimeState.GetRuntimeStateResponse;
import com.omgservers.schema.shard.runtime.runtimeState.UpdateRuntimeStateRequest;
import com.omgservers.schema.shard.runtime.runtimeState.UpdateRuntimeStateResponse;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.operation.server.HandleShardedRequestOperation;
import com.omgservers.service.shard.runtime.impl.operation.getRuntimeModuleClient.GetRuntimeModuleClientOperation;
import com.omgservers.service.shard.runtime.impl.operation.getRuntimeModuleClient.RuntimeModuleClient;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.RuntimeService;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtime.DeleteRuntimeMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtime.GetRuntimeMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtime.SyncRuntimeMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.DeleteRuntimeAssignmentMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.FindRuntimeAssignmentMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.GetRuntimeAssignmentMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.SyncRuntimeAssignmentMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.ViewRuntimeAssignmentsMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeCommand.DeleteRuntimeCommandMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeCommand.SyncRuntimeCommandMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeCommand.ViewRuntimeCommandsMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeMessage.DeleteRuntimeMessageMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeMessage.DeleteRuntimeMessagesMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeMessage.InterchangeMessagesMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeMessage.SyncRuntimeMessageMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeMessage.ViewRuntimeMessageMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeState.GetRuntimeStateMethod;
import com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeState.UpdateRuntimeStateMethod;
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

    final DeleteRuntimeAssignmentMethod deleteRuntimeAssignmentMethod;
    final ViewRuntimeAssignmentsMethod viewRuntimeAssignmentsMethod;
    final DeleteRuntimeMessagesMethod deleteRuntimeMessagesMethod;
    final SyncRuntimeAssignmentMethod syncRuntimeAssignmentMethod;
    final FindRuntimeAssignmentMethod findRuntimeAssignmentMethod;
    final DeleteRuntimeCommandMethod deleteRuntimeCommandMethod;
    final GetRuntimeAssignmentMethod getRuntimeAssignmentMethod;
    final DeleteRuntimeMessageMethod deleteRuntimeMessageMethod;
    final InterchangeMessagesMethod interchangeMessagesMethod;
    final ViewRuntimeCommandsMethod viewRuntimeCommandsMethod;
    final ViewRuntimeMessageMethod viewRuntimeMessageMethod;
    final SyncRuntimeMessageMethod syncRuntimeMessageMethod;
    final SyncRuntimeCommandMethod syncRuntimeCommandMethod;
    final UpdateRuntimeStateMethod updateRuntimeStateMethod;
    final GetRuntimeStateMethod getRuntimeStateMethod;
    final DeleteRuntimeMethod deleteRuntimeMethod;
    final SyncRuntimeMethod syncRuntimeMethod;
    final GetRuntimeMethod getRuntimeMethod;

    final GetRuntimeModuleClientOperation getRuntimeModuleClientOperation;
    final HandleShardedRequestOperation handleShardedRequestOperation;

    /*
    Runtime
     */

    @Override
    public Uni<GetRuntimeResponse> execute(@Valid final GetRuntimeRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                getRuntimeMethod::execute);
    }

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
    public Uni<DeleteRuntimeResponse> execute(@Valid final DeleteRuntimeRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                deleteRuntimeMethod::execute);
    }

    /*
    RuntimeCommand
     */

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
    public Uni<SyncRuntimeCommandResponse> executeWithIdempotency(SyncRuntimeCommandRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getRuntimeCommand(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncRuntimeCommandResponse(Boolean.FALSE));
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

    /*
    RuntimeMessage
     */

    @Override
    public Uni<ViewRuntimeMessagesResponse> execute(@Valid final ViewRuntimeMessagesRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                viewRuntimeMessageMethod::execute);
    }

    @Override
    public Uni<SyncRuntimeMessageResponse> execute(@Valid final SyncRuntimeMessageRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                syncRuntimeMessageMethod::execute);
    }

    @Override
    public Uni<SyncRuntimeMessageResponse> executeWithIdempotency(@Valid final SyncRuntimeMessageRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getRuntimeMessage(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncRuntimeMessageResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteRuntimeMessageResponse> execute(@Valid final DeleteRuntimeMessageRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                deleteRuntimeMessageMethod::execute);
    }

    @Override
    public Uni<DeleteRuntimeMessagesResponse> execute(@Valid final DeleteRuntimeMessagesRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                deleteRuntimeMessagesMethod::execute);
    }

    @Override
    public Uni<InterchangeMessagesResponse> execute(@Valid final InterchangeMessagesRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                interchangeMessagesMethod::execute);
    }

    /*
    RuntimeAssignment
     */

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
    public Uni<ViewRuntimeAssignmentsResponse> execute(@Valid final ViewRuntimeAssignmentsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                viewRuntimeAssignmentsMethod::execute);
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
    public Uni<DeleteRuntimeAssignmentResponse> execute(@Valid final DeleteRuntimeAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                deleteRuntimeAssignmentMethod::execute);
    }

    /*
    RuntimeState
     */

    @Override
    public Uni<GetRuntimeStateResponse> execute(@Valid final GetRuntimeStateRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                getRuntimeStateMethod::execute);
    }

    @Override
    public Uni<UpdateRuntimeStateResponse> execute(@Valid final UpdateRuntimeStateRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::execute,
                updateRuntimeStateMethod::execute);
    }
}
