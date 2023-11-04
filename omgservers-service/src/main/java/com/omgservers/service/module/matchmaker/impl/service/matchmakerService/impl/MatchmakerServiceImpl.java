package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl;

import com.omgservers.model.dto.matchmaker.DeleteMatchClientRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchClientResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchCommandResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerCommandResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.DeleteRequestRequest;
import com.omgservers.model.dto.matchmaker.DeleteRequestResponse;
import com.omgservers.model.dto.matchmaker.FindMatchClientRequest;
import com.omgservers.model.dto.matchmaker.FindMatchClientResponse;
import com.omgservers.model.dto.matchmaker.GetMatchClientRequest;
import com.omgservers.model.dto.matchmaker.GetMatchClientResponse;
import com.omgservers.model.dto.matchmaker.GetMatchRequest;
import com.omgservers.model.dto.matchmaker.GetMatchResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerStateResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchClientRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchClientResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchCommandResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.SyncRequestRequest;
import com.omgservers.model.dto.matchmaker.SyncRequestResponse;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchClientsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchClientsResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchCommandsResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchesRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchesResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerCommandsResponse;
import com.omgservers.model.dto.matchmaker.ViewRequestsRequest;
import com.omgservers.model.dto.matchmaker.ViewRequestsResponse;
import com.omgservers.service.module.matchmaker.impl.operation.getMatchmakerModuleClient.GetMatchmakerModuleClientOperation;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.MatchmakerService;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatch.DeleteMatchMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatchClient.DeleteMatchClientMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatchCommand.DeleteMatchCommandMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatchmaker.DeleteMatchmakerMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatchmakerCommand.DeleteMatchmakerCommandMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.deleteRequest.DeleteRequestMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.findMatchClient.FindMatchClientMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.getMatch.GetMatchMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.getMatchClient.GetMatchClientMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.getMatchmaker.GetMatchmakerMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.getMatchmakerState.GetMatchmakerStateMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatch.SyncMatchMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchClient.SyncMatchClientMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchCommand.SyncMatchCommandMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchmaker.SyncMatchmakerMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchmakerCommand.SyncMatchmakerCommandMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.syncRequest.SyncRequestMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.updateMatchmakerState.UpdateMatchmakerStateMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.viewMatchClients.ViewMatchClientsMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.viewMatchCommands.ViewMatchCommandsMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.viewMatches.ViewMatchesMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.viewMatchmakerCommands.ViewMatchmakerCommandsMethod;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.viewRequests.ViewRequestsMethod;
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

    final DeleteMatchmakerCommandMethod deleteMatchmakerCommandMethod;
    final ViewMatchmakerCommandsMethod viewMatchmakerCommandsMethod;
    final SyncMatchmakerCommandMethod syncMatchmakerCommandMethod;
    final UpdateMatchmakerStateMethod updateMatchmakerStateMethod;
    final DeleteMatchCommandMethod deleteMatchCommandMethod;
    final DeleteMatchClientMethod deleteMatchClientMethod;
    final ViewMatchCommandsMethod viewMatchCommandsMethod;
    final SyncMatchCommandMethod syncMatchCommandMethod;
    final DeleteMatchmakerMethod deleteMatchmakerMethod;
    final GetMatchmakerStateMethod getMatchmakerState;
    final SyncMatchClientMethod syncMatchClientMethod;
    final SyncMatchmakerMethod syncMatchmakerMethod;
    final GetMatchClientMethod getMatchClientMethod;
    final GetMatchmakerMethod getMatchmakerMethod;
    final DeleteRequestMethod deleteRequestMethod;
    final ViewMatchClientsMethod viewMatchClients;
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
    public Uni<SyncMatchCommandResponse> syncMatchCommand(@Valid final SyncMatchCommandRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::syncMatchCommand,
                syncMatchCommandMethod::syncMatchCommand);
    }

    @Override
    public Uni<DeleteMatchCommandResponse> deleteMatchCommand(
            @Valid final DeleteMatchCommandRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::deleteMatchCommand,
                deleteMatchCommandMethod::deleteMatchCommand);
    }

    @Override
    public Uni<ViewMatchCommandsResponse> viewMatchCommands(
            @Valid final ViewMatchCommandsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::viewMatchCommands,
                viewMatchCommandsMethod::viewMatchCommands);
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
    public Uni<ViewMatchClientsResponse> viewMatchClients(@Valid final ViewMatchClientsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                MatchmakerApi::viewMatchClients,
                viewMatchClients::viewMatchClients);
    }
}
