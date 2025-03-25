package com.omgservers.service.shard.matchmaker.impl.service.webService.impl.api;

import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.module.matchmaker.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.schema.module.matchmaker.matchmaker.GetMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.matchmaker.GetMatchmakerResponse;
import com.omgservers.schema.module.matchmaker.matchmaker.SyncMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.matchmaker.SyncMatchmakerResponse;
import com.omgservers.schema.module.matchmaker.matchmakerCommand.DeleteMatchmakerCommandRequest;
import com.omgservers.schema.module.matchmaker.matchmakerCommand.DeleteMatchmakerCommandResponse;
import com.omgservers.schema.module.matchmaker.matchmakerCommand.SyncMatchmakerCommandRequest;
import com.omgservers.schema.module.matchmaker.matchmakerCommand.SyncMatchmakerCommandResponse;
import com.omgservers.schema.module.matchmaker.matchmakerCommand.ViewMatchmakerCommandsRequest;
import com.omgservers.schema.module.matchmaker.matchmakerCommand.ViewMatchmakerCommandsResponse;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.DeleteMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.DeleteMatchmakerMatchAssignmentResponse;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.FindMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.FindMatchmakerMatchAssignmentResponse;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.GetMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.GetMatchmakerMatchAssignmentResponse;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.SyncMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.SyncMatchmakerMatchAssignmentResponse;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.ViewMatchmakerMatchAssignmentsRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.ViewMatchmakerMatchAssignmentsResponse;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.DeleteMatchmakerMatchResourceRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.DeleteMatchmakerMatchResourceResponse;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.GetMatchmakerMatchResourceRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.GetMatchmakerMatchResourceResponse;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.SyncMatchmakerMatchResourceRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.SyncMatchmakerMatchResourceResponse;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.UpdateMatchmakerMatchResourceStatusRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.UpdateMatchmakerMatchResourceStatusResponse;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.ViewMatchmakerMatchResourcesRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.ViewMatchmakerMatchResourcesResponse;
import com.omgservers.schema.module.matchmaker.matchmakerRequest.DeleteMatchmakerRequestRequest;
import com.omgservers.schema.module.matchmaker.matchmakerRequest.DeleteMatchmakerRequestResponse;
import com.omgservers.schema.module.matchmaker.matchmakerRequest.SyncMatchmakerRequestRequest;
import com.omgservers.schema.module.matchmaker.matchmakerRequest.SyncMatchmakerRequestResponse;
import com.omgservers.schema.module.matchmaker.matchmakerRequest.ViewMatchmakerRequestsRequest;
import com.omgservers.schema.module.matchmaker.matchmakerRequest.ViewMatchmakerRequestsResponse;
import com.omgservers.schema.module.matchmaker.matchmakerState.GetMatchmakerStateRequest;
import com.omgservers.schema.module.matchmaker.matchmakerState.GetMatchmakerStateResponse;
import com.omgservers.schema.module.matchmaker.matchmakerState.UpdateMatchmakerStateRequest;
import com.omgservers.schema.module.matchmaker.matchmakerState.UpdateMatchmakerStateResponse;
import com.omgservers.service.operation.server.HandleApiRequestOperation;
import com.omgservers.service.shard.matchmaker.impl.service.webService.WebService;
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
    MatchmakerMatchResources
     */

    @Override
    public Uni<GetMatchmakerMatchResourceResponse> execute(final GetMatchmakerMatchResourceRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<ViewMatchmakerMatchResourcesResponse> execute(final ViewMatchmakerMatchResourcesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncMatchmakerMatchResourceResponse> execute(final SyncMatchmakerMatchResourceRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<UpdateMatchmakerMatchResourceStatusResponse> execute(
            final UpdateMatchmakerMatchResourceStatusRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteMatchmakerMatchResourceResponse> execute(final DeleteMatchmakerMatchResourceRequest request) {
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
