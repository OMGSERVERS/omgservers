package com.omgservers.service.module.matchmaker.impl.service.webService.impl.api;

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
import com.omgservers.schema.model.internalRole.InternalRoleEnum;
import com.omgservers.service.module.matchmaker.impl.service.webService.WebService;
import com.omgservers.service.server.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@RolesAllowed({InternalRoleEnum.Names.SERVICE})
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerApiImpl implements MatchmakerApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final WebService webService;

    @Override
    public Uni<SyncMatchmakerResponse> syncMatchmaker(final SyncMatchmakerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncMatchmaker);
    }

    @Override
    public Uni<GetMatchmakerResponse> getMatchmaker(final GetMatchmakerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getMatchmaker);
    }

    @Override
    public Uni<DeleteMatchmakerResponse> deleteMatchmaker(final DeleteMatchmakerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteMatchmaker);
    }

    @Override
    public Uni<GetMatchmakerAssignmentResponse> getMatchmakerAssignment(final GetMatchmakerAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getMatchmakerAssignment);
    }

    @Override
    public Uni<FindMatchmakerAssignmentResponse> findMatchmakerAssignment(
            final FindMatchmakerAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findMatchmakerAssignment);
    }

    @Override
    public Uni<ViewMatchmakerAssignmentsResponse> viewMatchmakerAssignments(
            final ViewMatchmakerAssignmentsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewMatchmakerAssignments);
    }

    @Override
    public Uni<SyncMatchmakerAssignmentResponse> syncMatchmakerAssignment(
            final SyncMatchmakerAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncMatchmakerAssignment);
    }

    @Override
    public Uni<DeleteMatchmakerAssignmentResponse> deleteMatchmakerAssignment(
            final DeleteMatchmakerAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteMatchmakerAssignment);
    }

    @Override
    public Uni<GetMatchmakerStateResponse> getMatchmakerState(GetMatchmakerStateRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getMatchmakerState);
    }

    @Override
    public Uni<UpdateMatchmakerStateResponse> updateMatchmakerState(final UpdateMatchmakerStateRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::updateMatchmakerState);
    }

    @Override
    public Uni<SyncMatchmakerCommandResponse> syncMatchmakerCommand(final SyncMatchmakerCommandRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncMatchmakerCommand);
    }

    @Override
    public Uni<DeleteMatchmakerCommandResponse> deleteMatchmakerCommand(final DeleteMatchmakerCommandRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteMatchmakerCommand);
    }

    @Override
    public Uni<ViewMatchmakerCommandsResponse> viewMatchmakerCommands(final ViewMatchmakerCommandsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewMatchmakerCommands);
    }

    @Override
    public Uni<SyncMatchmakerRequestResponse> syncMatchmakerRequest(final SyncMatchmakerRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncMatchmakerRequest);
    }

    @Override
    public Uni<DeleteMatchmakerRequestResponse> deleteMatchmakerRequest(final DeleteMatchmakerRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteMatchmakerRequest);
    }

    @Override
    public Uni<ViewMatchmakerRequestsResponse> viewMatchmakerRequests(final ViewMatchmakerRequestsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewMatchmakerRequests);
    }

    @Override
    public Uni<GetMatchmakerMatchResponse> getMatchmakerMatch(final GetMatchmakerMatchRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getMatchmakerMatch);
    }

    @Override
    public Uni<UpdateMatchmakerMatchStatusResponse> updateMatchmakerMatchStatus(
            final UpdateMatchmakerMatchStatusRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::updateMatchmakerMatchStatus);
    }

    @Override
    public Uni<SyncMatchmakerMatchResponse> syncMatchmakerMatch(final SyncMatchmakerMatchRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncMatchmakerMatch);
    }

    @Override
    public Uni<DeleteMatchmakerMatchResponse> deleteMatchmakerMatch(final DeleteMatchmakerMatchRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteMatchmakerMatch);
    }

    @Override
    public Uni<ViewMatchmakerMatchesResponse> viewMatchmakerMatches(final ViewMatchmakerMatchesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewMatchmakerMatches);
    }

    @Override
    public Uni<GetMatchmakerMatchClientResponse> getMatchmakerMatchClient(
            final GetMatchmakerMatchClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getMatchmakerMatchClient);
    }

    @Override
    public Uni<FindMatchmakerMatchClientResponse> findMatchmakerMatchClient(
            final FindMatchmakerMatchClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findMatchmakerMatchClient);
    }

    @Override
    public Uni<ViewMatchmakerMatchClientsResponse> viewMatchmakerMatchClients(
            ViewMatchmakerMatchClientsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewMatchmakerMatchClients);
    }

    @Override
    public Uni<SyncMatchmakerMatchClientResponse> syncMatchmakerMatchClient(
            final SyncMatchmakerMatchClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncMatchmakerMatchClient);
    }

    @Override
    public Uni<DeleteMatchmakerMatchClientResponse> deleteMatchmakerMatchClient(
            final DeleteMatchmakerMatchClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteMatchmakerMatchClient);
    }

    @Override
    public Uni<GetMatchmakerMatchRuntimeRefResponse> getMatchmakerMatchRuntimeRef(
            final GetMatchmakerMatchRuntimeRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getMatchmakerMatchRuntimeRef);
    }

    @Override
    public Uni<FindMatchmakerMatchRuntimeRefResponse> findMatchmakerMatchRuntimeRef(
            final FindMatchmakerMatchRuntimeRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findMatchmakerMatchRuntimeRef);
    }

    @Override
    public Uni<SyncMatchmakerMatchRuntimeRefResponse> syncMatchmakerMatchRuntimeRef(
            final SyncMatchmakerMatchRuntimeRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncMatchmakerMatchRuntimeRef);
    }

    @Override
    public Uni<DeleteMatchmakerMatchRuntimeRefResponse> deleteMatchmakerMatchRuntimeRef(
            final DeleteMatchmakerMatchRuntimeRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteMatchmakerMatchRuntimeRef);
    }
}
