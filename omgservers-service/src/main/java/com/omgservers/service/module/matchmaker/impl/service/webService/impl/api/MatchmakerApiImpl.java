package com.omgservers.service.module.matchmaker.impl.service.webService.impl.api;

import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchClientRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchClientResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchCommandResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchRuntimeRefResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerCommandResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerRequestRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerRequestResponse;
import com.omgservers.model.dto.matchmaker.FindMatchmakerMatchClientRequest;
import com.omgservers.model.dto.matchmaker.FindMatchmakerMatchClientResponse;
import com.omgservers.model.dto.matchmaker.FindMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.FindMatchmakerMatchRuntimeRefResponse;
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
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchClientRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchClientResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchCommandResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchRuntimeRefResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequestRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequestResponse;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchClientsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchClientsResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchCommandsResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchesRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchesResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerCommandsResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerRequestsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerRequestsResponse;
import com.omgservers.model.internalRole.InternalRoleEnum;
import com.omgservers.service.module.matchmaker.impl.service.webService.WebService;
import com.omgservers.service.operation.handleApiRequest.HandleApiRequestOperation;
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
    public Uni<SyncMatchmakerRequestResponse> syncMatchmakerRequest(final SyncMatchmakerRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncMatchmakerRequest);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteMatchmakerRequestResponse> deleteMatchmakerRequest(final DeleteMatchmakerRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteMatchmakerRequest);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<ViewMatchmakerRequestsResponse> viewMatchmakerRequests(final ViewMatchmakerRequestsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewMatchmakerRequests);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetMatchmakerMatchResponse> getMatchmakerMatch(final GetMatchmakerMatchRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getMatchmakerMatch);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncMatchmakerMatchResponse> syncMatchmakerMatch(final SyncMatchmakerMatchRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncMatchmakerMatch);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteMatchmakerMatchResponse> deleteMatchmakerMatch(final DeleteMatchmakerMatchRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteMatchmakerMatch);
    }

    @Override
    public Uni<ViewMatchmakerMatchesResponse> viewMatchmakerMatches(final ViewMatchmakerMatchesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewMatchmakerMatches);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncMatchCommandResponse> syncMatchmakerMatchCommand(final SyncMatchCommandRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncMatchmakerMatchCommand);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteMatchCommandResponse> deleteMatchCommand(final DeleteMatchCommandRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteMatchmakerMatchCommand);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<ViewMatchmakerMatchCommandsResponse> viewMatchCommands(final ViewMatchmakerMatchCommandsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewMatchmakerMatchCommands);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetMatchmakerMatchClientResponse> getMatchmakerMatchClient(final GetMatchmakerMatchClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getMatchmakerMatchClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<FindMatchmakerMatchClientResponse> findMatchmakerMatchClient(final FindMatchmakerMatchClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findMatchmakerMatchClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<ViewMatchmakerMatchClientsResponse> viewMatchmakerMatchClients(ViewMatchmakerMatchClientsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewMatchmakerMatchClients);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncMatchmakerMatchClientResponse> syncMatchmakerMatchClient(final SyncMatchmakerMatchClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncMatchmakerMatchClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteMatchmakerMatchClientResponse> deleteMatchmakerMatchClient(final DeleteMatchmakerMatchClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteMatchmakerMatchClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetMatchmakerMatchRuntimeRefResponse> getMatchmakerMatchRuntimeRef(final GetMatchmakerMatchRuntimeRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getMatchmakerMatchRuntimeRef);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<FindMatchmakerMatchRuntimeRefResponse> findMatchmakerMatchRuntimeRef(final FindMatchmakerMatchRuntimeRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findMatchmakerMatchRuntimeRef);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncMatchmakerMatchRuntimeRefResponse> syncMatchmakerMatchRuntimeRef(final SyncMatchmakerMatchRuntimeRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncMatchmakerMatchRuntimeRef);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteMatchmakerMatchRuntimeRefResponse> deleteMatchmakerMatchRuntimeRef(final DeleteMatchmakerMatchRuntimeRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteMatchmakerMatchRuntimeRef);
    }
}
