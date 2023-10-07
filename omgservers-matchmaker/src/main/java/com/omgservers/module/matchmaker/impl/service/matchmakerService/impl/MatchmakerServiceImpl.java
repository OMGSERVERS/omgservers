package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl;

import com.omgservers.dto.matchmaker.DeleteMatchClientRequest;
import com.omgservers.dto.matchmaker.DeleteMatchClientResponse;
import com.omgservers.dto.matchmaker.DeleteMatchRequest;
import com.omgservers.dto.matchmaker.DeleteMatchResponse;
import com.omgservers.dto.matchmaker.DeleteMatchmakerCommandRequest;
import com.omgservers.dto.matchmaker.DeleteMatchmakerCommandResponse;
import com.omgservers.dto.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.dto.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.dto.matchmaker.DeleteRequestRequest;
import com.omgservers.dto.matchmaker.DeleteRequestResponse;
import com.omgservers.dto.matchmaker.FindMatchClientRequest;
import com.omgservers.dto.matchmaker.FindMatchClientResponse;
import com.omgservers.dto.matchmaker.GetMatchClientRequest;
import com.omgservers.dto.matchmaker.GetMatchClientResponse;
import com.omgservers.dto.matchmaker.GetMatchRequest;
import com.omgservers.dto.matchmaker.GetMatchResponse;
import com.omgservers.dto.matchmaker.GetMatchmakerRequest;
import com.omgservers.dto.matchmaker.GetMatchmakerResponse;
import com.omgservers.dto.matchmaker.GetMatchmakerStateRequest;
import com.omgservers.dto.matchmaker.GetMatchmakerStateResponse;
import com.omgservers.dto.matchmaker.SyncMatchClientRequest;
import com.omgservers.dto.matchmaker.SyncMatchClientResponse;
import com.omgservers.dto.matchmaker.SyncMatchRequest;
import com.omgservers.dto.matchmaker.SyncMatchResponse;
import com.omgservers.dto.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.dto.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.dto.matchmaker.SyncMatchmakerRequest;
import com.omgservers.dto.matchmaker.SyncMatchmakerResponse;
import com.omgservers.dto.matchmaker.UpdateMatchmakerStateRequest;
import com.omgservers.dto.matchmaker.UpdateMatchmakerStateResponse;
import com.omgservers.dto.matchmaker.SyncRequestRequest;
import com.omgservers.dto.matchmaker.SyncRequestResponse;
import com.omgservers.dto.matchmaker.UpdateMatchmakerCommandsStatusRequest;
import com.omgservers.dto.matchmaker.UpdateMatchmakerCommandsStatusResponse;
import com.omgservers.dto.matchmaker.ViewMatchesRequest;
import com.omgservers.dto.matchmaker.ViewMatchesResponse;
import com.omgservers.dto.matchmaker.ViewMatchmakerCommandsRequest;
import com.omgservers.dto.matchmaker.ViewMatchmakerCommandsResponse;
import com.omgservers.dto.matchmaker.ViewRequestsRequest;
import com.omgservers.dto.matchmaker.ViewRequestsResponse;
import com.omgservers.module.matchmaker.impl.operation.getMatchmakerModuleClient.GetMatchmakerModuleClientOperation;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.MatchmakerService;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatch.DeleteMatchMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatchClient.DeleteMatchClientMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatchmaker.DeleteMatchmakerMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatchmakerCommand.DeleteMatchmakerCommandMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.deleteRequest.DeleteRequestMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.findMatchClient.FindMatchClientMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.getMatch.GetMatchMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.getMatchClient.GetMatchClientMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.getMatchmaker.GetMatchmakerMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.getMatchmakerState.GetMatchmakerStateMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatch.SyncMatchMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchClient.SyncMatchClientMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchmaker.SyncMatchmakerMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchmakerCommand.SyncMatchmakerCommandMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchmakerState.UpdateMatchmakerStateMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.syncRequest.SyncRequestMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.updateMatchmakerCommandsStatus.UpdateMatchmakerCommandsMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.viewMatches.ViewMatchesMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.viewMatchmakerCommands.ViewMatchmakerCommandsMethod;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.viewRequests.ViewRequestsMethod;
import com.omgservers.module.matchmaker.impl.service.webService.impl.api.MatchmakerApi;
import com.omgservers.operation.calculateShard.CalculateShardOperation;
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
class MatchmakerServiceImpl implements MatchmakerService {

    final UpdateMatchmakerCommandsMethod updateMatchmakerCommandsMethod;
    final DeleteMatchmakerCommandMethod deleteMatchmakerCommandMethod;
    final ViewMatchmakerCommandsMethod viewMatchmakerCommandsMethod;
    final SyncMatchmakerCommandMethod syncMatchmakerCommandMethod;
    final UpdateMatchmakerStateMethod updateMatchmakerStateMethod;
    final DeleteMatchClientMethod deleteMatchClientMethod;
    final DeleteMatchmakerMethod deleteMatchmakerMethod;
    final GetMatchmakerStateMethod getMatchmakerState;
    final SyncMatchClientMethod syncMatchClientMethod;
    final SyncMatchmakerMethod syncMatchmakerMethod;
    final GetMatchClientMethod getMatchClientMethod;
    final GetMatchmakerMethod getMatchmakerMethod;
    final DeleteRequestMethod deleteRequestMethod;
    final ViewRequestsMethod viewRequestsMethod;
    final FindMatchClientMethod findMatchClient;
    final ViewMatchesMethod viewMatchesMethod;
    final SyncRequestMethod syncRequestMethod;
    final DeleteMatchMethod deleteMatchMethod;
    final SyncMatchMethod syncMatchMethod;
    final GetMatchMethod getMatchMethod;

    final GetMatchmakerModuleClientOperation getMatchServiceApiClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<SyncMatchmakerResponse> syncMatchmaker(@Valid final SyncMatchmakerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::syncMatchmaker,
                syncMatchmakerMethod::syncMatchmaker);
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
    public Uni<UpdateMatchmakerCommandsStatusResponse> updateMatchmakerCommandsStatus(
            @Valid final UpdateMatchmakerCommandsStatusRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::updateMatchmakerCommandsStatus,
                updateMatchmakerCommandsMethod::updateMatchmakerCommandsStatus);
    }

    @Override
    public Uni<SyncRequestResponse> syncRequest(@Valid final SyncRequestRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::syncRequest,
                syncRequestMethod::syncRequest);
    }

    @Override
    public Uni<DeleteRequestResponse> deleteRequest(@Valid final DeleteRequestRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::deleteRequest,
                deleteRequestMethod::deleteRequest);
    }

    @Override
    public Uni<ViewRequestsResponse> viewRequests(@Valid final ViewRequestsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::viewRequests,
                viewRequestsMethod::viewRequests);
    }

    @Override
    public Uni<GetMatchResponse> getMatch(@Valid final GetMatchRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::getMatch,
                getMatchMethod::getMatch);
    }

    @Override
    public Uni<SyncMatchResponse> syncMatch(@Valid final SyncMatchRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::syncMatch,
                syncMatchMethod::syncMatch);
    }

    @Override
    public Uni<DeleteMatchResponse> deleteMatch(@Valid final DeleteMatchRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::deleteMatch,
                deleteMatchMethod::deleteMatch);
    }

    @Override
    public Uni<ViewMatchesResponse> viewMatches(@Valid final ViewMatchesRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::viewMatches,
                viewMatchesMethod::viewMatches);
    }

    @Override
    public Uni<GetMatchClientResponse> getMatchClient(@Valid final GetMatchClientRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::getMatchClient,
                getMatchClientMethod::getMatchClient);
    }

    @Override
    public Uni<SyncMatchClientResponse> syncMatchClient(@Valid final SyncMatchClientRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::syncMatchClient,
                syncMatchClientMethod::syncMatchClient);
    }

    @Override
    public Uni<DeleteMatchClientResponse> deleteMatchClient(@Valid final DeleteMatchClientRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::deleteMatchClient,
                deleteMatchClientMethod::deleteMatchClient);
    }

    @Override
    public Uni<FindMatchClientResponse> findMatchClient(@Valid final FindMatchClientRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::findMatchClient,
                findMatchClient::findMatchClient);
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
}
