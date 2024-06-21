package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl;

import com.omgservers.model.dto.matchmaker.DeleteMatchmakerAssignmentRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerAssignmentResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerCommandResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchClientRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchClientResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchRuntimeRefResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerRequestRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerRequestResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.FindMatchmakerAssignmentRequest;
import com.omgservers.model.dto.matchmaker.FindMatchmakerAssignmentResponse;
import com.omgservers.model.dto.matchmaker.FindMatchmakerMatchClientRequest;
import com.omgservers.model.dto.matchmaker.FindMatchmakerMatchClientResponse;
import com.omgservers.model.dto.matchmaker.FindMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.FindMatchmakerMatchRuntimeRefResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerAssignmentRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerAssignmentResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchClientRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchClientResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchRuntimeRefResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerStateResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerAssignmentRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerAssignmentResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchClientRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchClientResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchRuntimeRefResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequestRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequestResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerMatchStatusRequest;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerMatchStatusResponse;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerAssignmentsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerAssignmentsResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerCommandsResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchClientsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchClientsResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchesRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchesResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerRequestsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerRequestsResponse;
import com.omgservers.service.module.matchmaker.impl.operation.getMatchmakerModuleClient.GetMatchmakerModuleClientOperation;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.MatchmakerService;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.machmaker.deleteMatchmaker.DeleteMatchmakerMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment.deleteMatchmakerAssignment.DeleteMatchmakerAssignmentMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerCommand.deleteMatchmakerCommand.DeleteMatchmakerCommandMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatch.deleteMatchmakerMatch.DeleteMatchmakerMatchMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchClient.deleteMatchmakerMatchClient.DeleteMatchmakerMatchClientMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchRuntimerRef.deleteMatchmakerMatchRuntimeRef.DeleteMatchmakerMatchRuntimeRefMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerRequest.deleteMatchmakerRequest.DeleteMatchmakerRequestMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment.findMatchmakerAssignment.FindMatchmakerAssignmentMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchClient.findMatchmakerMatchClient.FindMatchmakerMatchClientMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchRuntimerRef.findMatchmakerMatchRuntimeRef.FindMatchmakerMatchRuntimeRefMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.machmaker.getMatchmaker.GetMatchmakerMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment.getMatchmakerAssignment.GetMatchmakerAssignmentMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatch.getMatchmakerMatch.GetMatchmakerMatchMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchClient.getMatchmakerMatchClient.GetMatchmakerMatchClientMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchRuntimerRef.getMatchmakerMatchRuntimeRef.GetMatchmakerMatchRuntimeRefMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.machmaker.getMatchmakerState.GetMatchmakerStateMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.machmaker.syncMatchmaker.SyncMatchmakerMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment.syncMatchmakerAssignment.SyncMatchmakerAssignmentMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerCommand.syncMatchmakerCommand.SyncMatchmakerCommandMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatch.syncMatchmakerMatch.SyncMatchmakerMatchMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchClient.syncMatchmakerMatchClient.SyncMatchmakerMatchClientMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchRuntimerRef.syncMatchmakerMatchRuntimeRef.SyncMatchmakerMatchRuntimeRefMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerRequest.syncMatchmakerRequest.SyncMatchmakerRequestMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatch.updateMatchmakerMatchStatus.UpdateMatchmakerMatchStatusMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.machmaker.updateMatchmakerState.UpdateMatchmakerStateMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerAssignment.viewMatchmakerAssignments.ViewMatchmakerAssignmentsMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerCommand.viewMatchmakerCommands.ViewMatchmakerCommandsMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchClient.viewMatchmakerMatchClients.ViewMatchmakerMatchClientsMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatch.viewMatchmakerMatches.ViewMatchmakerMatchesMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerRequest.viewMatchmakerRequests.ViewMatchmakerRequestsMethod;
import com.omgservers.service.module.matchmaker.impl.service.webService.impl.api.MatchmakerApi;
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
    final HandleInternalRequestOperation handleInternalRequestOperation;
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

    @Override
    public Uni<SyncMatchmakerResponse> syncMatchmaker(@Valid final SyncMatchmakerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::syncMatchmaker,
                syncMatchmakerMethod::syncMatchmaker);
    }

    @Override
    public Uni<SyncMatchmakerAssignmentResponse> syncMatchmakerAssignment(
            @Valid final SyncMatchmakerAssignmentRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::syncMatchmakerAssignment,
                syncMatchmakerAssignmentMethod::syncMatchmakerAssignment);
    }

    @Override
    public Uni<GetMatchmakerResponse> getMatchmaker(@Valid final GetMatchmakerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::getMatchmaker,
                getMatchmakerMethod::getMatchmaker);
    }

    @Override
    public Uni<DeleteMatchmakerResponse> deleteMatchmaker(@Valid final DeleteMatchmakerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::deleteMatchmaker,
                deleteMatchmakerMethod::deleteMatchmaker);
    }

    @Override
    public Uni<GetMatchmakerAssignmentResponse> getMatchmakerAssignment(
            @Valid final GetMatchmakerAssignmentRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::getMatchmakerAssignment,
                getMatchmakerAssignmentMethod::getMatchmakerAssignment);
    }

    @Override
    public Uni<FindMatchmakerAssignmentResponse> findMatchmakerAssignment(
            @Valid final FindMatchmakerAssignmentRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::findMatchmakerAssignment,
                findMatchmakerAssignmentMethod::findMatchmakerAssignment);
    }

    @Override
    public Uni<ViewMatchmakerAssignmentsResponse> viewMatchmakerAssignments(
            @Valid final ViewMatchmakerAssignmentsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::viewMatchmakerAssignments,
                viewMatchmakerAssignmentsMethod::viewMatchmakerAssignments);
    }

    @Override
    public Uni<DeleteMatchmakerAssignmentResponse> deleteMatchmakerAssignment(
            @Valid final DeleteMatchmakerAssignmentRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::deleteMatchmakerAssignment,
                deleteMatchmakerAssignmentMethod::deleteMatchmakerAssignment);
    }

    @Override
    public Uni<GetMatchmakerStateResponse> getMatchmakerState(@Valid final GetMatchmakerStateRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::getMatchmakerState,
                getMatchmakerState::getMatchmakerState);
    }

    @Override
    public Uni<UpdateMatchmakerStateResponse> updateMatchmakerState(@Valid final UpdateMatchmakerStateRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::updateMatchmakerState,
                updateMatchmakerStateMethod::updateMatchmakerState);
    }

    @Override
    public Uni<SyncMatchmakerCommandResponse> syncMatchmakerCommand(@Valid final SyncMatchmakerCommandRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::syncMatchmakerCommand,
                syncMatchmakerCommandMethod::syncMatchmakerCommand);
    }

    @Override
    public Uni<DeleteMatchmakerCommandResponse> deleteMatchmakerCommand(
            @Valid final DeleteMatchmakerCommandRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::deleteMatchmakerCommand,
                deleteMatchmakerCommandMethod::deleteMatchmakerCommand);
    }

    @Override
    public Uni<ViewMatchmakerCommandsResponse> viewMatchmakerCommands(
            @Valid final ViewMatchmakerCommandsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::viewMatchmakerCommands,
                viewMatchmakerCommandsMethod::viewMatchmakerCommands);
    }

    @Override
    public Uni<SyncMatchmakerRequestResponse> syncMatchmakerRequest(@Valid final SyncMatchmakerRequestRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::syncMatchmakerRequest,
                syncMatchmakerRequestMethod::syncMatchmakerRequest);
    }

    @Override
    public Uni<DeleteMatchmakerRequestResponse> deleteMatchmakerRequest(
            @Valid final DeleteMatchmakerRequestRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::deleteMatchmakerRequest,
                deleteMatchmakerRequestMethod::deleteMatchmakerRequest);
    }

    @Override
    public Uni<ViewMatchmakerRequestsResponse> viewMatchmakerRequests(
            @Valid final ViewMatchmakerRequestsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::viewMatchmakerRequests,
                viewMatchmakerRequestsMethod::viewMatchmakerRequests);
    }

    @Override
    public Uni<GetMatchmakerMatchResponse> getMatchmakerMatch(@Valid final GetMatchmakerMatchRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::getMatchmakerMatch,
                getMatchmakerMatchMethod::getMatchmakerMatch);
    }

    @Override
    public Uni<UpdateMatchmakerMatchStatusResponse> updateMatchmakerMatchStatus(
            @Valid final UpdateMatchmakerMatchStatusRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::updateMatchmakerMatchStatus,
                updateMatchmakerMatchStatusMethod::updateMatchmakerMatchStatus);
    }

    @Override
    public Uni<SyncMatchmakerMatchResponse> syncMatchmakerMatch(@Valid final SyncMatchmakerMatchRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::syncMatchmakerMatch,
                syncMatchmakerMatchMethod::syncMatchmakerMatch);
    }

    @Override
    public Uni<DeleteMatchmakerMatchResponse> deleteMatchmakerMatch(@Valid final DeleteMatchmakerMatchRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::deleteMatchmakerMatch,
                deleteMatchmakerMatchMethod::deleteMatchmakerMatch);
    }

    @Override
    public Uni<ViewMatchmakerMatchesResponse> viewMatchmakerMatches(@Valid final ViewMatchmakerMatchesRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::viewMatchmakerMatches,
                viewMatchmakerMatchesMethod::viewMatchmakerMatches);
    }

    @Override
    public Uni<GetMatchmakerMatchClientResponse> getMatchmakerMatchClient(
            @Valid final GetMatchmakerMatchClientRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::getMatchmakerMatchClient,
                getMatchmakerMatchClientMethod::getMatchmakerMatchClient);
    }

    @Override
    public Uni<FindMatchmakerMatchClientResponse> findMatchmakerMatchClient(
            @Valid final FindMatchmakerMatchClientRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::findMatchmakerMatchClient,
                findMatchClient::findMatchmakerMatchClient);
    }

    @Override
    public Uni<ViewMatchmakerMatchClientsResponse> viewMatchmakerMatchClients(
            @Valid final ViewMatchmakerMatchClientsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::viewMatchmakerMatchClients,
                viewMatchClients::viewMatchmakerMatchClients);
    }

    @Override
    public Uni<SyncMatchmakerMatchClientResponse> syncMatchmakerMatchClient(
            @Valid final SyncMatchmakerMatchClientRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::syncMatchmakerMatchClient,
                syncMatchmakerMatchClientMethod::syncMatchmakerMatchClient);
    }

    @Override
    public Uni<DeleteMatchmakerMatchClientResponse> deleteMatchmakerMatchClient(
            @Valid final DeleteMatchmakerMatchClientRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::deleteMatchmakerMatchClient,
                deleteMatchmakerMatchClientMethod::deleteMatchmakerMatchClient);
    }

    @Override
    public Uni<GetMatchmakerMatchRuntimeRefResponse> getMatchmakerMatchRuntimeRef(
            @Valid final GetMatchmakerMatchRuntimeRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::getMatchmakerMatchRuntimeRef,
                getMatchmakerMatchRuntimeRefMethod::getMatchmakerMatchRuntimeRef);
    }

    @Override
    public Uni<FindMatchmakerMatchRuntimeRefResponse> findMatchmakerMatchRuntimeRef(
            @Valid final FindMatchmakerMatchRuntimeRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::findMatchmakerMatchRuntimeRef,
                findMatchmakerMatchRuntimeRefMethod::findMatchmakerMatchRuntimeRef);
    }

    @Override
    public Uni<SyncMatchmakerMatchRuntimeRefResponse> syncMatchmakerMatchRuntimeRef(
            @Valid final SyncMatchmakerMatchRuntimeRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::syncMatchmakerMatchRuntimeRef,
                syncMatchmakerMatchRuntimeRefMethod::syncMatchmakerMatchRuntimeRef);
    }

    @Override
    public Uni<DeleteMatchmakerMatchRuntimeRefResponse> deleteMatchmakerMatchRuntimeRef(
            @Valid final DeleteMatchmakerMatchRuntimeRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::deleteMatchmakerMatchRuntimeRef,
                deleteMatchmakerMatchRuntimeRefMethod::deleteMatchmakerMatchRuntimeRef);
    }
}
