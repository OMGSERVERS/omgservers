package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerAssignmentResponse;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerCommandRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerCommandResponse;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchAssignmentResponse;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchResponse;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchRuntimeRefRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchRuntimeRefResponse;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerRequestRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerRequestResponse;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.schema.module.matchmaker.FindMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.FindMatchmakerAssignmentResponse;
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchAssignmentResponse;
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchRuntimeRefRequest;
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchRuntimeRefResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerAssignmentResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchAssignmentResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchRuntimeRefRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchRuntimeRefResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerStateRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerStateResponse;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerAssignmentResponse;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchAssignmentResponse;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchResponse;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchRuntimeRefRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchRuntimeRefResponse;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerRequestRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerRequestResponse;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerResponse;
import com.omgservers.schema.module.matchmaker.UpdateMatchmakerMatchStatusRequest;
import com.omgservers.schema.module.matchmaker.UpdateMatchmakerMatchStatusResponse;
import com.omgservers.schema.module.matchmaker.UpdateMatchmakerStateRequest;
import com.omgservers.schema.module.matchmaker.UpdateMatchmakerStateResponse;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerAssignmentsRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerAssignmentsResponse;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerCommandsRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerCommandsResponse;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchAssignmentsRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchAssignmentsResponse;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchesRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchesResponse;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerRequestsRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerRequestsResponse;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.shard.matchmaker.impl.operation.getMatchmakerModuleClient.GetMatchmakerModuleClientOperation;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.MatchmakerService;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.machmaker.DeleteMatchmakerMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.machmaker.GetMatchmakerMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.machmaker.SyncMatchmakerMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment.DeleteMatchmakerAssignmentMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment.FindMatchmakerAssignmentMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment.GetMatchmakerAssignmentMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment.SyncMatchmakerAssignmentMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment.ViewMatchmakerAssignmentsMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerCommand.DeleteMatchmakerCommandMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerCommand.SyncMatchmakerCommandMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerCommand.ViewMatchmakerCommandsMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatch.DeleteMatchmakerMatchMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatch.GetMatchmakerMatchMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatch.SyncMatchmakerMatchMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatch.UpdateMatchmakerMatchStatusMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatch.ViewMatchmakerMatchesMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment.DeleteMatchmakerMatchAssignmentMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment.FindMatchmakerMatchAssignmentMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment.GetMatchmakerMatchAssignmentMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment.SyncMatchmakerMatchAssignmentMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchAssignment.ViewMatchmakerMatchAssignmentsMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchRuntimerRef.DeleteMatchmakerMatchRuntimeRefMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchRuntimerRef.FindMatchmakerMatchRuntimeRefMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchRuntimerRef.GetMatchmakerMatchRuntimeRefMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchRuntimerRef.SyncMatchmakerMatchRuntimeRefMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerRequest.DeleteMatchmakerRequestMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerRequest.SyncMatchmakerRequestMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerRequest.ViewMatchmakerRequestsMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerState.GetMatchmakerStateMethod;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerState.UpdateMatchmakerStateMethod;
import com.omgservers.service.shard.matchmaker.impl.service.webService.impl.api.MatchmakerApi;
import com.omgservers.service.operation.server.CalculateShardOperation;
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
class MatchmakerServiceImpl implements MatchmakerService {

    final DeleteMatchmakerMatchRuntimeRefMethod deleteMatchmakerMatchRuntimeRefMethod;
    final DeleteMatchmakerMatchAssignmentMethod deleteMatchmakerMatchAssignmentMethod;
    final ViewMatchmakerMatchAssignmentsMethod viewMatchmakerMatchAssignmentsMethod;
    final FindMatchmakerMatchAssignmentMethod findMatchmakerMatchAssignmentMethod;
    final FindMatchmakerMatchRuntimeRefMethod findMatchmakerMatchRuntimeRefMethod;
    final SyncMatchmakerMatchRuntimeRefMethod syncMatchmakerMatchRuntimeRefMethod;
    final SyncMatchmakerMatchAssignmentMethod syncMatchmakerMatchAssignmentMethod;
    final GetMatchmakerMatchAssignmentMethod getMatchmakerMatchAssignmentMethod;
    final GetMatchmakerMatchRuntimeRefMethod getMatchmakerMatchRuntimeRefMethod;
    final GetMatchmakerModuleClientOperation getMatchServiceApiClientOperation;
    final UpdateMatchmakerMatchStatusMethod updateMatchmakerMatchStatusMethod;
    final DeleteMatchmakerAssignmentMethod deleteMatchmakerAssignmentMethod;
    final ViewMatchmakerAssignmentsMethod viewMatchmakerAssignmentsMethod;
    final FindMatchmakerAssignmentMethod findMatchmakerAssignmentMethod;
    final SyncMatchmakerAssignmentMethod syncMatchmakerAssignmentMethod;
    final DeleteMatchmakerRequestMethod deleteMatchmakerRequestMethod;
    final DeleteMatchmakerCommandMethod deleteMatchmakerCommandMethod;
    final GetMatchmakerAssignmentMethod getMatchmakerAssignmentMethod;
    final ViewMatchmakerCommandsMethod viewMatchmakerCommandsMethod;
    final ViewMatchmakerRequestsMethod viewMatchmakerRequestsMethod;
    final SyncMatchmakerCommandMethod syncMatchmakerCommandMethod;
    final UpdateMatchmakerStateMethod updateMatchmakerStateMethod;
    final ViewMatchmakerMatchesMethod viewMatchmakerMatchesMethod;
    final SyncMatchmakerRequestMethod syncMatchmakerRequestMethod;
    final DeleteMatchmakerMatchMethod deleteMatchmakerMatchMethod;
    final SyncMatchmakerMatchMethod syncMatchmakerMatchMethod;
    final GetMatchmakerMatchMethod getMatchmakerMatchMethod;
    final CalculateShardOperation calculateShardOperation;
    final DeleteMatchmakerMethod deleteMatchmakerMethod;
    final GetMatchmakerStateMethod getMatchmakerState;
    final SyncMatchmakerMethod syncMatchmakerMethod;
    final GetMatchmakerMethod getMatchmakerMethod;

    final HandleShardedRequestOperation handleShardedRequestOperation;

    /*
    Matchmaker
     */

    @Override
    public Uni<GetMatchmakerResponse> execute(@Valid final GetMatchmakerRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                getMatchmakerMethod::execute);
    }

    @Override
    public Uni<SyncMatchmakerResponse> execute(@Valid final SyncMatchmakerRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
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
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                deleteMatchmakerMethod::deleteMatchmaker);
    }

    /*
    MatchmakerAssignment
     */

    @Override
    public Uni<GetMatchmakerAssignmentResponse> execute(@Valid final GetMatchmakerAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                getMatchmakerAssignmentMethod::execute);
    }

    @Override
    public Uni<FindMatchmakerAssignmentResponse> execute(@Valid final FindMatchmakerAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                findMatchmakerAssignmentMethod::execute);
    }

    @Override
    public Uni<ViewMatchmakerAssignmentsResponse> execute(@Valid final ViewMatchmakerAssignmentsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                viewMatchmakerAssignmentsMethod::execute);
    }

    @Override
    public Uni<SyncMatchmakerAssignmentResponse> execute(@Valid final SyncMatchmakerAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                syncMatchmakerAssignmentMethod::execute);
    }

    @Override
    public Uni<SyncMatchmakerAssignmentResponse> executeWithIdempotency(
            @Valid final SyncMatchmakerAssignmentRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}",
                                    request.getMatchmakerAssignment(), t.getMessage());
                            return Uni.createFrom().item(new SyncMatchmakerAssignmentResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteMatchmakerAssignmentResponse> execute(@Valid final DeleteMatchmakerAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                deleteMatchmakerAssignmentMethod::deleteMatchmakerAssignment);
    }

    /*
    MatchmakerCommand
     */

    @Override
    public Uni<ViewMatchmakerCommandsResponse> execute(@Valid final ViewMatchmakerCommandsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                viewMatchmakerCommandsMethod::execute);
    }

    @Override
    public Uni<SyncMatchmakerCommandResponse> execute(@Valid final SyncMatchmakerCommandRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
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
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                deleteMatchmakerCommandMethod::execute);
    }

    /*
    MatchmakerRequest
     */

    @Override
    public Uni<GetMatchmakerMatchResponse> execute(@Valid final GetMatchmakerMatchRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                getMatchmakerMatchMethod::execute);
    }

    @Override
    public Uni<ViewMatchmakerRequestsResponse> execute(@Valid final ViewMatchmakerRequestsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                viewMatchmakerRequestsMethod::execute);
    }

    @Override
    public Uni<SyncMatchmakerRequestResponse> execute(@Valid final SyncMatchmakerRequestRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
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
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                deleteMatchmakerRequestMethod::execute);
    }

    /*
    MatchmakerMatch
     */

    @Override
    public Uni<ViewMatchmakerMatchesResponse> execute(@Valid final ViewMatchmakerMatchesRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                viewMatchmakerMatchesMethod::execute);
    }

    @Override
    public Uni<SyncMatchmakerMatchResponse> execute(@Valid final SyncMatchmakerMatchRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                syncMatchmakerMatchMethod::execute);
    }

    @Override
    public Uni<UpdateMatchmakerMatchStatusResponse> execute(@Valid final UpdateMatchmakerMatchStatusRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                updateMatchmakerMatchStatusMethod::execute);
    }

    @Override
    public Uni<DeleteMatchmakerMatchResponse> execute(@Valid final DeleteMatchmakerMatchRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                deleteMatchmakerMatchMethod::execute);
    }

    /*
    MatchmakerMatchAssignment
     */

    @Override
    public Uni<GetMatchmakerMatchAssignmentResponse> execute(@Valid final GetMatchmakerMatchAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                getMatchmakerMatchAssignmentMethod::execute);
    }

    @Override
    public Uni<FindMatchmakerMatchAssignmentResponse> execute(
            @Valid final FindMatchmakerMatchAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                findMatchmakerMatchAssignmentMethod::execute);
    }

    @Override
    public Uni<ViewMatchmakerMatchAssignmentsResponse> execute(
            @Valid final ViewMatchmakerMatchAssignmentsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                viewMatchmakerMatchAssignmentsMethod::execute);
    }

    @Override
    public Uni<SyncMatchmakerMatchAssignmentResponse> execute(
            @Valid final SyncMatchmakerMatchAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                syncMatchmakerMatchAssignmentMethod::execute);
    }

    @Override
    public Uni<DeleteMatchmakerMatchAssignmentResponse> execute(
            @Valid final DeleteMatchmakerMatchAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                deleteMatchmakerMatchAssignmentMethod::execute);
    }

    /*
    MatchmakerMatchRuntimeRef
     */

    @Override
    public Uni<GetMatchmakerMatchRuntimeRefResponse> execute(
            @Valid final GetMatchmakerMatchRuntimeRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                getMatchmakerMatchRuntimeRefMethod::execute);
    }

    @Override
    public Uni<FindMatchmakerMatchRuntimeRefResponse> execute(
            @Valid final FindMatchmakerMatchRuntimeRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                findMatchmakerMatchRuntimeRefMethod::execute);
    }

    @Override
    public Uni<SyncMatchmakerMatchRuntimeRefResponse> execute(
            @Valid final SyncMatchmakerMatchRuntimeRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                syncMatchmakerMatchRuntimeRefMethod::execute);
    }

    @Override
    public Uni<DeleteMatchmakerMatchRuntimeRefResponse> execute(
            @Valid final DeleteMatchmakerMatchRuntimeRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                deleteMatchmakerMatchRuntimeRefMethod::execute);
    }

    /*
    MatchmakerState
     */

    @Override
    public Uni<GetMatchmakerStateResponse> execute(@Valid final GetMatchmakerStateRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                getMatchmakerState::execute);
    }

    @Override
    public Uni<UpdateMatchmakerStateResponse> execute(@Valid final UpdateMatchmakerStateRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::execute,
                updateMatchmakerStateMethod::execute);
    }
}
