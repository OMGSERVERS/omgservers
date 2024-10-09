package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerAssignmentResponse;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerCommandRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerCommandResponse;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchClientRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchClientResponse;
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
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchClientRequest;
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchClientResponse;
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchRuntimeRefRequest;
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchRuntimeRefResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerAssignmentResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchClientRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchClientResponse;
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
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchClientRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchClientResponse;
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
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchClientsRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchClientsResponse;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchesRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchesResponse;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerRequestsRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerRequestsResponse;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.matchmaker.impl.operation.getMatchmakerModuleClient.GetMatchmakerModuleClientOperation;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.MatchmakerService;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.machmaker.deleteMatchmaker.DeleteMatchmakerMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.machmaker.getMatchmaker.GetMatchmakerMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.machmaker.getMatchmakerState.GetMatchmakerStateMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.machmaker.syncMatchmaker.SyncMatchmakerMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.machmaker.updateMatchmakerState.UpdateMatchmakerStateMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment.deleteMatchmakerAssignment.DeleteMatchmakerAssignmentMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment.findMatchmakerAssignment.FindMatchmakerAssignmentMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment.getMatchmakerAssignment.GetMatchmakerAssignmentMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment.syncMatchmakerAssignment.SyncMatchmakerAssignmentMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment.viewMatchmakerAssignments.ViewMatchmakerAssignmentsMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerCommand.deleteMatchmakerCommand.DeleteMatchmakerCommandMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerCommand.syncMatchmakerCommand.SyncMatchmakerCommandMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerCommand.viewMatchmakerCommands.ViewMatchmakerCommandsMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatch.deleteMatchmakerMatch.DeleteMatchmakerMatchMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatch.getMatchmakerMatch.GetMatchmakerMatchMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatch.syncMatchmakerMatch.SyncMatchmakerMatchMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatch.updateMatchmakerMatchStatus.UpdateMatchmakerMatchStatusMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatch.viewMatchmakerMatches.ViewMatchmakerMatchesMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchClient.deleteMatchmakerMatchClient.DeleteMatchmakerMatchClientMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchClient.findMatchmakerMatchClient.FindMatchmakerMatchClientMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchClient.getMatchmakerMatchClient.GetMatchmakerMatchClientMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchClient.syncMatchmakerMatchClient.SyncMatchmakerMatchClientMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchClient.viewMatchmakerMatchClients.ViewMatchmakerMatchClientsMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchRuntimerRef.deleteMatchmakerMatchRuntimeRef.DeleteMatchmakerMatchRuntimeRefMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchRuntimerRef.findMatchmakerMatchRuntimeRef.FindMatchmakerMatchRuntimeRefMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchRuntimerRef.getMatchmakerMatchRuntimeRef.GetMatchmakerMatchRuntimeRefMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchRuntimerRef.syncMatchmakerMatchRuntimeRef.SyncMatchmakerMatchRuntimeRefMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerRequest.deleteMatchmakerRequest.DeleteMatchmakerRequestMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerRequest.syncMatchmakerRequest.SyncMatchmakerRequestMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerRequest.viewMatchmakerRequests.ViewMatchmakerRequestsMethod;
import com.omgservers.service.module.matchmaker.impl.service.webService.impl.api.MatchmakerApi;
import com.omgservers.service.operation.calculateShard.CalculateShardOperation;
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
class MatchmakerServiceImpl implements MatchmakerService {

    final DeleteMatchmakerMatchRuntimeRefMethod deleteMatchmakerMatchRuntimeRefMethod;
    final FindMatchmakerMatchRuntimeRefMethod findMatchmakerMatchRuntimeRefMethod;
    final SyncMatchmakerMatchRuntimeRefMethod syncMatchmakerMatchRuntimeRefMethod;
    final GetMatchmakerMatchRuntimeRefMethod getMatchmakerMatchRuntimeRefMethod;
    final GetMatchmakerModuleClientOperation getMatchServiceApiClientOperation;
    final UpdateMatchmakerMatchStatusMethod updateMatchmakerMatchStatusMethod;
    final DeleteMatchmakerMatchClientMethod deleteMatchmakerMatchClientMethod;
    final DeleteMatchmakerAssignmentMethod deleteMatchmakerAssignmentMethod;
    final SyncMatchmakerMatchClientMethod syncMatchmakerMatchClientMethod;
    final ViewMatchmakerAssignmentsMethod viewMatchmakerAssignmentsMethod;
    final GetMatchmakerMatchClientMethod getMatchmakerMatchClientMethod;
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
    final ViewMatchmakerMatchClientsMethod viewMatchClients;
    final GetMatchmakerMatchMethod getMatchmakerMatchMethod;
    final FindMatchmakerMatchClientMethod findMatchClient;
    final CalculateShardOperation calculateShardOperation;
    final DeleteMatchmakerMethod deleteMatchmakerMethod;
    final GetMatchmakerStateMethod getMatchmakerState;
    final SyncMatchmakerMethod syncMatchmakerMethod;
    final GetMatchmakerMethod getMatchmakerMethod;

    final HandleShardedRequestOperation handleShardedRequestOperation;

    @Override
    public Uni<SyncMatchmakerResponse> syncMatchmaker(@Valid final SyncMatchmakerRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::syncMatchmaker,
                syncMatchmakerMethod::syncMatchmaker);
    }

    @Override
    public Uni<SyncMatchmakerResponse> syncMatchmakerWithIdempotency(SyncMatchmakerRequest request) {
        return syncMatchmaker(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}",
                                    request.getMatchmaker(), t.getMessage());
                            return Uni.createFrom().item(new SyncMatchmakerResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<SyncMatchmakerAssignmentResponse> syncMatchmakerAssignment(
            @Valid final SyncMatchmakerAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::syncMatchmakerAssignment,
                syncMatchmakerAssignmentMethod::syncMatchmakerAssignment);
    }

    @Override
    public Uni<GetMatchmakerResponse> getMatchmaker(@Valid final GetMatchmakerRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::getMatchmaker,
                getMatchmakerMethod::getMatchmaker);
    }

    @Override
    public Uni<DeleteMatchmakerResponse> deleteMatchmaker(@Valid final DeleteMatchmakerRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::deleteMatchmaker,
                deleteMatchmakerMethod::deleteMatchmaker);
    }

    @Override
    public Uni<GetMatchmakerAssignmentResponse> getMatchmakerAssignment(
            @Valid final GetMatchmakerAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::getMatchmakerAssignment,
                getMatchmakerAssignmentMethod::getMatchmakerAssignment);
    }

    @Override
    public Uni<FindMatchmakerAssignmentResponse> findMatchmakerAssignment(
            @Valid final FindMatchmakerAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::findMatchmakerAssignment,
                findMatchmakerAssignmentMethod::findMatchmakerAssignment);
    }

    @Override
    public Uni<ViewMatchmakerAssignmentsResponse> viewMatchmakerAssignments(
            @Valid final ViewMatchmakerAssignmentsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::viewMatchmakerAssignments,
                viewMatchmakerAssignmentsMethod::viewMatchmakerAssignments);
    }

    @Override
    public Uni<DeleteMatchmakerAssignmentResponse> deleteMatchmakerAssignment(
            @Valid final DeleteMatchmakerAssignmentRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::deleteMatchmakerAssignment,
                deleteMatchmakerAssignmentMethod::deleteMatchmakerAssignment);
    }

    @Override
    public Uni<GetMatchmakerStateResponse> getMatchmakerState(@Valid final GetMatchmakerStateRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::getMatchmakerState,
                getMatchmakerState::getMatchmakerState);
    }

    @Override
    public Uni<UpdateMatchmakerStateResponse> updateMatchmakerState(@Valid final UpdateMatchmakerStateRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::updateMatchmakerState,
                updateMatchmakerStateMethod::updateMatchmakerState);
    }

    @Override
    public Uni<SyncMatchmakerCommandResponse> syncMatchmakerCommand(@Valid final SyncMatchmakerCommandRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::syncMatchmakerCommand,
                syncMatchmakerCommandMethod::syncMatchmakerCommand);
    }

    @Override
    public Uni<SyncMatchmakerCommandResponse> syncMatchmakerCommandWithIdempotency(
            @Valid final SyncMatchmakerCommandRequest request) {
        return syncMatchmakerCommand(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}",
                                    request.getMatchmakerCommand(), t.getMessage());
                            return Uni.createFrom().item(new SyncMatchmakerCommandResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteMatchmakerCommandResponse> deleteMatchmakerCommand(
            @Valid final DeleteMatchmakerCommandRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::deleteMatchmakerCommand,
                deleteMatchmakerCommandMethod::deleteMatchmakerCommand);
    }

    @Override
    public Uni<ViewMatchmakerCommandsResponse> viewMatchmakerCommands(
            @Valid final ViewMatchmakerCommandsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::viewMatchmakerCommands,
                viewMatchmakerCommandsMethod::viewMatchmakerCommands);
    }

    @Override
    public Uni<SyncMatchmakerRequestResponse> syncMatchmakerRequest(@Valid final SyncMatchmakerRequestRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::syncMatchmakerRequest,
                syncMatchmakerRequestMethod::syncMatchmakerRequest);
    }

    @Override
    public Uni<SyncMatchmakerRequestResponse> syncMatchmakerRequestWithIdempotency(
            SyncMatchmakerRequestRequest request) {
        return syncMatchmakerRequest(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}",
                                    request.getMatchmakerRequest(), t.getMessage());
                            return Uni.createFrom().item(new SyncMatchmakerRequestResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteMatchmakerRequestResponse> deleteMatchmakerRequest(
            @Valid final DeleteMatchmakerRequestRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::deleteMatchmakerRequest,
                deleteMatchmakerRequestMethod::deleteMatchmakerRequest);
    }

    @Override
    public Uni<ViewMatchmakerRequestsResponse> viewMatchmakerRequests(
            @Valid final ViewMatchmakerRequestsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::viewMatchmakerRequests,
                viewMatchmakerRequestsMethod::viewMatchmakerRequests);
    }

    @Override
    public Uni<GetMatchmakerMatchResponse> getMatchmakerMatch(@Valid final GetMatchmakerMatchRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::getMatchmakerMatch,
                getMatchmakerMatchMethod::getMatchmakerMatch);
    }

    @Override
    public Uni<UpdateMatchmakerMatchStatusResponse> updateMatchmakerMatchStatus(
            @Valid final UpdateMatchmakerMatchStatusRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::updateMatchmakerMatchStatus,
                updateMatchmakerMatchStatusMethod::updateMatchmakerMatchStatus);
    }

    @Override
    public Uni<SyncMatchmakerMatchResponse> syncMatchmakerMatch(@Valid final SyncMatchmakerMatchRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::syncMatchmakerMatch,
                syncMatchmakerMatchMethod::syncMatchmakerMatch);
    }

    @Override
    public Uni<DeleteMatchmakerMatchResponse> deleteMatchmakerMatch(@Valid final DeleteMatchmakerMatchRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::deleteMatchmakerMatch,
                deleteMatchmakerMatchMethod::deleteMatchmakerMatch);
    }

    @Override
    public Uni<ViewMatchmakerMatchesResponse> viewMatchmakerMatches(@Valid final ViewMatchmakerMatchesRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::viewMatchmakerMatches,
                viewMatchmakerMatchesMethod::viewMatchmakerMatches);
    }

    @Override
    public Uni<GetMatchmakerMatchClientResponse> getMatchmakerMatchClient(
            @Valid final GetMatchmakerMatchClientRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::getMatchmakerMatchClient,
                getMatchmakerMatchClientMethod::getMatchmakerMatchClient);
    }

    @Override
    public Uni<FindMatchmakerMatchClientResponse> findMatchmakerMatchClient(
            @Valid final FindMatchmakerMatchClientRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::findMatchmakerMatchClient,
                findMatchClient::findMatchmakerMatchClient);
    }

    @Override
    public Uni<ViewMatchmakerMatchClientsResponse> viewMatchmakerMatchClients(
            @Valid final ViewMatchmakerMatchClientsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::viewMatchmakerMatchClients,
                viewMatchClients::viewMatchmakerMatchClients);
    }

    @Override
    public Uni<SyncMatchmakerMatchClientResponse> syncMatchmakerMatchClient(
            @Valid final SyncMatchmakerMatchClientRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::syncMatchmakerMatchClient,
                syncMatchmakerMatchClientMethod::syncMatchmakerMatchClient);
    }

    @Override
    public Uni<DeleteMatchmakerMatchClientResponse> deleteMatchmakerMatchClient(
            @Valid final DeleteMatchmakerMatchClientRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::deleteMatchmakerMatchClient,
                deleteMatchmakerMatchClientMethod::deleteMatchmakerMatchClient);
    }

    @Override
    public Uni<GetMatchmakerMatchRuntimeRefResponse> getMatchmakerMatchRuntimeRef(
            @Valid final GetMatchmakerMatchRuntimeRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::getMatchmakerMatchRuntimeRef,
                getMatchmakerMatchRuntimeRefMethod::getMatchmakerMatchRuntimeRef);
    }

    @Override
    public Uni<FindMatchmakerMatchRuntimeRefResponse> findMatchmakerMatchRuntimeRef(
            @Valid final FindMatchmakerMatchRuntimeRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::findMatchmakerMatchRuntimeRef,
                findMatchmakerMatchRuntimeRefMethod::findMatchmakerMatchRuntimeRef);
    }

    @Override
    public Uni<SyncMatchmakerMatchRuntimeRefResponse> syncMatchmakerMatchRuntimeRef(
            @Valid final SyncMatchmakerMatchRuntimeRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::syncMatchmakerMatchRuntimeRef,
                syncMatchmakerMatchRuntimeRefMethod::syncMatchmakerMatchRuntimeRef);
    }

    @Override
    public Uni<DeleteMatchmakerMatchRuntimeRefResponse> deleteMatchmakerMatchRuntimeRef(
            @Valid final DeleteMatchmakerMatchRuntimeRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::deleteMatchmakerMatchRuntimeRef,
                deleteMatchmakerMatchRuntimeRefMethod::deleteMatchmakerMatchRuntimeRef);
    }
}
