package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.shard.matchmaker.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.schema.shard.matchmaker.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.schema.shard.matchmaker.matchmaker.GetMatchmakerRequest;
import com.omgservers.schema.shard.matchmaker.matchmaker.GetMatchmakerResponse;
import com.omgservers.schema.shard.matchmaker.matchmaker.SyncMatchmakerRequest;
import com.omgservers.schema.shard.matchmaker.matchmaker.SyncMatchmakerResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerCommand.DeleteMatchmakerCommandRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerCommand.DeleteMatchmakerCommandResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerCommand.SyncMatchmakerCommandRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerCommand.SyncMatchmakerCommandResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerCommand.ViewMatchmakerCommandsRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerCommand.ViewMatchmakerCommandsResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.DeleteMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.DeleteMatchmakerMatchAssignmentResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.FindMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.FindMatchmakerMatchAssignmentResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.GetMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.GetMatchmakerMatchAssignmentResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.SyncMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.SyncMatchmakerMatchAssignmentResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.ViewMatchmakerMatchAssignmentsRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.ViewMatchmakerMatchAssignmentsResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.DeleteMatchmakerMatchResourceRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.DeleteMatchmakerMatchResourceResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.GetMatchmakerMatchResourceRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.GetMatchmakerMatchResourceResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.SyncMatchmakerMatchResourceRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.SyncMatchmakerMatchResourceResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.UpdateMatchmakerMatchResourceStatusRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.UpdateMatchmakerMatchResourceStatusResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.ViewMatchmakerMatchResourcesRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.ViewMatchmakerMatchResourcesResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerRequest.DeleteMatchmakerRequestRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerRequest.DeleteMatchmakerRequestResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerRequest.SyncMatchmakerRequestRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerRequest.SyncMatchmakerRequestResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerRequest.ViewMatchmakerRequestsRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerRequest.ViewMatchmakerRequestsResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerState.GetMatchmakerStateRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerState.GetMatchmakerStateResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerState.UpdateMatchmakerStateRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerState.UpdateMatchmakerStateResponse;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.operation.server.CalculateShardOperation;
import com.omgservers.service.operation.server.HandleShardedRequestOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.getMatchmakerModuleClient.GetMatchmakerModuleClientOperation;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.MatchmakerService;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.machmaker.DeleteMatchmakerMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.machmaker.GetMatchmakerMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.machmaker.SyncMatchmakerMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerCommand.DeleteMatchmakerCommandMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerCommand.SyncMatchmakerCommandMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerCommand.ViewMatchmakerCommandsMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment.DeleteMatchmakerMatchAssignmentMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment.FindMatchmakerMatchAssignmentMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment.GetMatchmakerMatchAssignmentMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment.SyncMatchmakerMatchAssignmentMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment.ViewMatchmakerMatchAssignmentsMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchResource.DeleteMatchmakerMatchResourceMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchResource.GetMatchmakerMatchResourceMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchResource.SyncMatchmakerMatchResourceMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchResource.UpdateMatchmakerMatchResourceStatusMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchResource.ViewMatchmakerMatchResourcesMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerRequest.DeleteMatchmakerRequestMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerRequest.SyncMatchmakerRequestMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerRequest.ViewMatchmakerRequestsMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerState.GetMatchmakerStateMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerState.UpdateMatchmakerStateMethod;
import com.omgservers.service.shard.matchmaker.impl.service.webService.impl.api.MatchmakerApi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MatchmakerServiceImpl implements MatchmakerService {

    final UpdateMatchmakerMatchResourceStatusMethod updateMatchmakerMatchResourceStatusMethod;
    final DeleteMatchmakerMatchAssignmentMethod deleteMatchmakerMatchAssignmentMethod;
    final ViewMatchmakerMatchAssignmentsMethod viewMatchmakerMatchAssignmentsMethod;
    final FindMatchmakerMatchAssignmentMethod findMatchmakerMatchAssignmentMethod;
    final SyncMatchmakerMatchAssignmentMethod syncMatchmakerMatchAssignmentMethod;
    final DeleteMatchmakerMatchResourceMethod deleteMatchmakerMatchResourceMethod;
    final GetMatchmakerMatchAssignmentMethod getMatchmakerMatchAssignmentMethod;
    final ViewMatchmakerMatchResourcesMethod viewMatchmakerMatchResourcesMethod;
    final GetMatchmakerModuleClientOperation getMatchServiceApiClientOperation;
    final SyncMatchmakerMatchResourceMethod syncMatchmakerMatchResourceMethod;
    final GetMatchmakerMatchResourceMethod getMatchmakerMatchResourceMethod;
    final DeleteMatchmakerRequestMethod deleteMatchmakerRequestMethod;
    final DeleteMatchmakerCommandMethod deleteMatchmakerCommandMethod;
    final ViewMatchmakerCommandsMethod viewMatchmakerCommandsMethod;
    final ViewMatchmakerRequestsMethod viewMatchmakerRequestsMethod;
    final SyncMatchmakerCommandMethod syncMatchmakerCommandMethod;
    final UpdateMatchmakerStateMethod updateMatchmakerStateMethod;
    final SyncMatchmakerRequestMethod syncMatchmakerRequestMethod;
    final GetMatchmakerStateMethod getMatchmakerStateMethod;
    final CalculateShardOperation calculateShardOperation;
    final DeleteMatchmakerMethod deleteMatchmakerMethod;
    final SyncMatchmakerMethod syncMatchmakerMethod;
    final GetMatchmakerMethod getMatchmakerMethod;

    final HandleShardedRequestOperation handleShardedRequestOperation;

    /*
    Matchmaker
     */

    @Override
    public Uni<GetMatchmakerResponse> execute(@Valid final GetMatchmakerRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                getMatchmakerMethod::execute);
    }

    @Override
    public Uni<SyncMatchmakerResponse> execute(@Valid final SyncMatchmakerRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                syncMatchmakerMethod::execute);
    }

    @Override
    public Uni<SyncMatchmakerResponse> executeWithIdempotency(@Valid final SyncMatchmakerRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}",
                                    request.getMatchmaker(), t.getMessage());
                            return Uni.createFrom().item(new SyncMatchmakerResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteMatchmakerResponse> execute(@Valid final DeleteMatchmakerRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                deleteMatchmakerMethod::deleteMatchmaker);
    }

    /*
    MatchmakerCommand
     */

    @Override
    public Uni<ViewMatchmakerCommandsResponse> execute(@Valid final ViewMatchmakerCommandsRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                viewMatchmakerCommandsMethod::execute);
    }

    @Override
    public Uni<SyncMatchmakerCommandResponse> execute(@Valid final SyncMatchmakerCommandRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                syncMatchmakerCommandMethod::execute);
    }

    @Override
    public Uni<SyncMatchmakerCommandResponse> executeWithIdempotency(
            @Valid final SyncMatchmakerCommandRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}",
                                    request.getMatchmakerCommand(), t.getMessage());
                            return Uni.createFrom().item(new SyncMatchmakerCommandResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteMatchmakerCommandResponse> execute(@Valid final DeleteMatchmakerCommandRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                deleteMatchmakerCommandMethod::execute);
    }

    /*
    MatchmakerRequest
     */

    @Override
    public Uni<GetMatchmakerMatchResourceResponse> execute(@Valid final GetMatchmakerMatchResourceRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                getMatchmakerMatchResourceMethod::execute);
    }

    @Override
    public Uni<ViewMatchmakerRequestsResponse> execute(@Valid final ViewMatchmakerRequestsRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                viewMatchmakerRequestsMethod::execute);
    }

    @Override
    public Uni<SyncMatchmakerRequestResponse> execute(@Valid final SyncMatchmakerRequestRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                syncMatchmakerRequestMethod::execute);
    }

    @Override
    public Uni<SyncMatchmakerRequestResponse> executeWithIdempotency(
            @Valid final SyncMatchmakerRequestRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}",
                                    request.getMatchmakerRequest(), t.getMessage());
                            return Uni.createFrom().item(new SyncMatchmakerRequestResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteMatchmakerRequestResponse> execute(@Valid final DeleteMatchmakerRequestRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                deleteMatchmakerRequestMethod::execute);
    }

    /*
    MatchmakerMatchResource
     */

    @Override
    public Uni<ViewMatchmakerMatchResourcesResponse> execute(@Valid final ViewMatchmakerMatchResourcesRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                viewMatchmakerMatchResourcesMethod::execute);
    }

    @Override
    public Uni<SyncMatchmakerMatchResourceResponse> execute(@Valid final SyncMatchmakerMatchResourceRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                syncMatchmakerMatchResourceMethod::execute);
    }

    @Override
    public Uni<UpdateMatchmakerMatchResourceStatusResponse> execute(
            @Valid final UpdateMatchmakerMatchResourceStatusRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                updateMatchmakerMatchResourceStatusMethod::execute);
    }

    @Override
    public Uni<DeleteMatchmakerMatchResourceResponse> execute(
            @Valid final DeleteMatchmakerMatchResourceRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                deleteMatchmakerMatchResourceMethod::execute);
    }

    /*
    MatchmakerMatchAssignment
     */

    @Override
    public Uni<GetMatchmakerMatchAssignmentResponse> execute(@Valid final GetMatchmakerMatchAssignmentRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                getMatchmakerMatchAssignmentMethod::execute);
    }

    @Override
    public Uni<FindMatchmakerMatchAssignmentResponse> execute(
            @Valid final FindMatchmakerMatchAssignmentRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                findMatchmakerMatchAssignmentMethod::execute);
    }

    @Override
    public Uni<ViewMatchmakerMatchAssignmentsResponse> execute(
            @Valid final ViewMatchmakerMatchAssignmentsRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                viewMatchmakerMatchAssignmentsMethod::execute);
    }

    @Override
    public Uni<SyncMatchmakerMatchAssignmentResponse> execute(
            @Valid final SyncMatchmakerMatchAssignmentRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                syncMatchmakerMatchAssignmentMethod::execute);
    }

    @Override
    public Uni<DeleteMatchmakerMatchAssignmentResponse> execute(
            @Valid final DeleteMatchmakerMatchAssignmentRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                deleteMatchmakerMatchAssignmentMethod::execute);
    }

    /*
    MatchmakerState
     */

    @Override
    public Uni<GetMatchmakerStateResponse> execute(@Valid final GetMatchmakerStateRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                getMatchmakerStateMethod::execute);
    }

    @Override
    public Uni<UpdateMatchmakerStateResponse> execute(@Valid final UpdateMatchmakerStateRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                updateMatchmakerStateMethod::execute);
    }
}
