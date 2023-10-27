package com.omgservers.module.matchmaker.impl.service.webService.impl.api;

import com.omgservers.dto.matchmaker.DeleteMatchClientRequest;
import com.omgservers.dto.matchmaker.DeleteMatchClientResponse;
import com.omgservers.dto.matchmaker.DeleteMatchCommandRequest;
import com.omgservers.dto.matchmaker.DeleteMatchCommandResponse;
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
import com.omgservers.dto.matchmaker.SyncMatchCommandRequest;
import com.omgservers.dto.matchmaker.SyncMatchCommandResponse;
import com.omgservers.dto.matchmaker.SyncMatchRequest;
import com.omgservers.dto.matchmaker.SyncMatchResponse;
import com.omgservers.dto.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.dto.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.dto.matchmaker.SyncMatchmakerRequest;
import com.omgservers.dto.matchmaker.SyncMatchmakerResponse;
import com.omgservers.dto.matchmaker.SyncRequestRequest;
import com.omgservers.dto.matchmaker.SyncRequestResponse;
import com.omgservers.dto.matchmaker.UpdateMatchmakerStateRequest;
import com.omgservers.dto.matchmaker.UpdateMatchmakerStateResponse;
import com.omgservers.dto.matchmaker.ViewMatchClientsRequest;
import com.omgservers.dto.matchmaker.ViewMatchClientsResponse;
import com.omgservers.dto.matchmaker.ViewMatchCommandsRequest;
import com.omgservers.dto.matchmaker.ViewMatchCommandsResponse;
import com.omgservers.dto.matchmaker.ViewMatchesRequest;
import com.omgservers.dto.matchmaker.ViewMatchesResponse;
import com.omgservers.dto.matchmaker.ViewMatchmakerCommandsRequest;
import com.omgservers.dto.matchmaker.ViewMatchmakerCommandsResponse;
import com.omgservers.dto.matchmaker.ViewRequestsRequest;
import com.omgservers.dto.matchmaker.ViewRequestsResponse;
import com.omgservers.model.internalRole.InternalRoleEnum;
import com.omgservers.module.matchmaker.impl.service.webService.WebService;
import com.omgservers.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerApiImpl implements MatchmakerApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final WebService webService;

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncMatchmakerResponse> syncMatchmaker(final SyncMatchmakerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncMatchmaker);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetMatchmakerResponse> getMatchmaker(final GetMatchmakerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getMatchmaker);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteMatchmakerResponse> deleteMatchmaker(final DeleteMatchmakerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteMatchmaker);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetMatchmakerStateResponse> getMatchmakerState(GetMatchmakerStateRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getMatchmakerState);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<UpdateMatchmakerStateResponse> updateMatchmakerState(final UpdateMatchmakerStateRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::updateMatchmakerState);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncMatchmakerCommandResponse> syncMatchmakerCommand(final SyncMatchmakerCommandRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncMatchmakerCommand);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteMatchmakerCommandResponse> deleteMatchmakerCommand(final DeleteMatchmakerCommandRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteMatchmakerCommand);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<ViewMatchmakerCommandsResponse> viewMatchmakerCommands(final ViewMatchmakerCommandsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewMatchmakerCommands);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncRequestResponse> syncRequest(final SyncRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncRequest);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteRequestResponse> deleteRequest(final DeleteRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteRequest);
    }

    @Override
    public Uni<ViewRequestsResponse> viewRequests(final ViewRequestsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewRequests);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetMatchResponse> getMatch(final GetMatchRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getMatch);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncMatchResponse> syncMatch(final SyncMatchRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncMatch);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteMatchResponse> deleteMatch(final DeleteMatchRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteMatch);
    }

    @Override
    public Uni<ViewMatchesResponse> viewMatches(final ViewMatchesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewMatches);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncMatchCommandResponse> syncMatchCommand(final SyncMatchCommandRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncMatchCommand);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteMatchCommandResponse> deleteMatchCommand(final DeleteMatchCommandRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteMatchCommand);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<ViewMatchCommandsResponse> viewMatchCommands(final ViewMatchCommandsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewMatchCommands);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetMatchClientResponse> getMatchClient(final GetMatchClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getMatchClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncMatchClientResponse> syncMatchClient(final SyncMatchClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncMatchClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteMatchClientResponse> deleteMatchClient(final DeleteMatchClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteMatchClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<FindMatchClientResponse> findMatchClient(final FindMatchClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findMatchClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<ViewMatchClientsResponse> viewMatchClients(ViewMatchClientsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewMatchClients);
    }
}
