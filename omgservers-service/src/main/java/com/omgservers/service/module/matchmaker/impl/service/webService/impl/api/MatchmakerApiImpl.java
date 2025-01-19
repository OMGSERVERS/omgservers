package com.omgservers.service.module.matchmaker.impl.service.webService.impl.api;

import com.omgservers.schema.model.user.UserRoleEnum;
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
import com.omgservers.service.module.matchmaker.impl.service.webService.WebService;
import com.omgservers.service.operation.server.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@RolesAllowed({UserRoleEnum.Names.SERVICE})
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerApiImpl implements MatchmakerApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final WebService webService;

    /*
    Matchmaker
     */

    @Override
    public Uni<SyncMatchmakerResponse> execute(final SyncMatchmakerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<GetMatchmakerResponse> execute(final GetMatchmakerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteMatchmakerResponse> execute(final DeleteMatchmakerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    MatchmakerAssignment
     */

    @Override
    public Uni<GetMatchmakerAssignmentResponse> execute(final GetMatchmakerAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<FindMatchmakerAssignmentResponse> execute(final FindMatchmakerAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<ViewMatchmakerAssignmentsResponse> execute(final ViewMatchmakerAssignmentsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncMatchmakerAssignmentResponse> execute(final SyncMatchmakerAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteMatchmakerAssignmentResponse> execute(final DeleteMatchmakerAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    MatchmakerCommand
     */

    @Override
    public Uni<ViewMatchmakerCommandsResponse> execute(final ViewMatchmakerCommandsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncMatchmakerCommandResponse> execute(final SyncMatchmakerCommandRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteMatchmakerCommandResponse> execute(final DeleteMatchmakerCommandRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    MatchmakerRequest
     */

    @Override
    public Uni<ViewMatchmakerRequestsResponse> execute(final ViewMatchmakerRequestsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncMatchmakerRequestResponse> execute(final SyncMatchmakerRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteMatchmakerRequestResponse> execute(final DeleteMatchmakerRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    MatchmakerMatch
     */

    @Override
    public Uni<GetMatchmakerMatchResponse> execute(final GetMatchmakerMatchRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<ViewMatchmakerMatchesResponse> execute(final ViewMatchmakerMatchesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncMatchmakerMatchResponse> execute(final SyncMatchmakerMatchRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<UpdateMatchmakerMatchStatusResponse> execute(final UpdateMatchmakerMatchStatusRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteMatchmakerMatchResponse> execute(final DeleteMatchmakerMatchRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    MatchmakerMatchAssignment
     */

    @Override
    public Uni<GetMatchmakerMatchAssignmentResponse> execute(final GetMatchmakerMatchAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<FindMatchmakerMatchAssignmentResponse> execute(final FindMatchmakerMatchAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<ViewMatchmakerMatchAssignmentsResponse> execute(final ViewMatchmakerMatchAssignmentsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncMatchmakerMatchAssignmentResponse> execute(final SyncMatchmakerMatchAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteMatchmakerMatchAssignmentResponse> execute(final DeleteMatchmakerMatchAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    MatchmakerMatchRuntimeRef
     */

    @Override
    public Uni<GetMatchmakerMatchRuntimeRefResponse> execute(final GetMatchmakerMatchRuntimeRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<FindMatchmakerMatchRuntimeRefResponse> execute(final FindMatchmakerMatchRuntimeRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncMatchmakerMatchRuntimeRefResponse> execute(final SyncMatchmakerMatchRuntimeRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteMatchmakerMatchRuntimeRefResponse> execute(final DeleteMatchmakerMatchRuntimeRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    MatchmakerState
     */

    @Override
    public Uni<GetMatchmakerStateResponse> execute(GetMatchmakerStateRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<UpdateMatchmakerStateResponse> execute(final UpdateMatchmakerStateRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }
}
