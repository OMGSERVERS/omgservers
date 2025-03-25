package com.omgservers.service.shard.deployment.impl.service.webService.impl.api;

import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.module.deployment.deployment.*;
import com.omgservers.schema.module.deployment.deploymentCommand.*;
import com.omgservers.schema.module.deployment.deploymentLobbyAssignment.*;
import com.omgservers.schema.module.deployment.deploymentLobbyResource.*;
import com.omgservers.schema.module.deployment.deploymentMatchmakerAssignment.*;
import com.omgservers.schema.module.deployment.deploymentMatchmakerResource.*;
import com.omgservers.schema.module.deployment.deploymentRequest.*;
import com.omgservers.schema.module.deployment.deploymentState.GetDeploymentStateRequest;
import com.omgservers.schema.module.deployment.deploymentState.GetDeploymentStateResponse;
import com.omgservers.schema.module.deployment.deploymentState.UpdateDeploymentStateRequest;
import com.omgservers.schema.module.deployment.deploymentState.UpdateDeploymentStateResponse;
import com.omgservers.service.operation.server.HandleApiRequestOperation;
import com.omgservers.service.shard.deployment.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
@RolesAllowed({UserRoleEnum.Names.SERVICE})
class DeploymentApiImpl implements DeploymentApi {

    final WebService webService;

    final HandleApiRequestOperation handleApiRequestOperation;

    /*
    Deployment
     */

    @Override
    public Uni<GetDeploymentResponse> execute(final GetDeploymentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<GetDeploymentDataResponse> execute(final GetDeploymentDataRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncDeploymentResponse> execute(final SyncDeploymentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteDeploymentResponse> execute(final DeleteDeploymentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    DeploymentCommand
     */

    @Override
    public Uni<ViewDeploymentCommandsResponse> execute(final ViewDeploymentCommandsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncDeploymentCommandResponse> execute(final SyncDeploymentCommandRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteDeploymentCommandResponse> execute(final DeleteDeploymentCommandRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    DeploymentRequest
     */

    @Override
    public Uni<GetDeploymentRequestResponse> execute(final GetDeploymentRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<FindDeploymentRequestResponse> execute(final FindDeploymentRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<ViewDeploymentRequestsResponse> execute(final ViewDeploymentRequestsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncDeploymentRequestResponse> execute(final SyncDeploymentRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteDeploymentRequestResponse> execute(final DeleteDeploymentRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    DeploymentLobbyResource
     */

    @Override
    public Uni<GetDeploymentLobbyResourceResponse> execute(final GetDeploymentLobbyResourceRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<FindDeploymentLobbyResourceResponse> execute(final FindDeploymentLobbyResourceRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<ViewDeploymentLobbyResourcesResponse> execute(final ViewDeploymentLobbyResourcesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncDeploymentLobbyResourceResponse> execute(final SyncDeploymentLobbyResourceRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<UpdateDeploymentLobbyResourceStatusResponse> execute(final UpdateDeploymentLobbyResourceStatusRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteDeploymentLobbyResourceResponse> execute(final DeleteDeploymentLobbyResourceRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    DeploymentLobbyAssignment
     */

    @Override
    public Uni<GetDeploymentLobbyAssignmentResponse> execute(final GetDeploymentLobbyAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<FindDeploymentLobbyAssignmentResponse> execute(final FindDeploymentLobbyAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<ViewDeploymentLobbyAssignmentsResponse> execute(final ViewDeploymentLobbyAssignmentsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncDeploymentLobbyAssignmentResponse> execute(final SyncDeploymentLobbyAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteDeploymentLobbyAssignmentResponse> execute(final DeleteDeploymentLobbyAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    DeploymentMatchmakerResource
     */

    @Override
    public Uni<GetDeploymentMatchmakerResourceResponse> execute(final GetDeploymentMatchmakerResourceRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<FindDeploymentMatchmakerResourceResponse> execute(final FindDeploymentMatchmakerResourceRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<ViewDeploymentMatchmakerResourcesResponse> execute(final ViewDeploymentMatchmakerResourcesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncDeploymentMatchmakerResourceResponse> execute(final SyncDeploymentMatchmakerResourceRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<UpdateDeploymentMatchmakerResourceStatusResponse> execute(
            final UpdateDeploymentMatchmakerResourceStatusRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteDeploymentMatchmakerResourceResponse> execute(final DeleteDeploymentMatchmakerResourceRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    DeploymentMatchmakerAssignment
     */

    @Override
    public Uni<GetDeploymentMatchmakerAssignmentResponse> execute(final GetDeploymentMatchmakerAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<FindDeploymentMatchmakerAssignmentResponse> execute(final FindDeploymentMatchmakerAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<ViewDeploymentMatchmakerAssignmentsResponse> execute(final ViewDeploymentMatchmakerAssignmentsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncDeploymentMatchmakerAssignmentResponse> execute(final SyncDeploymentMatchmakerAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteDeploymentMatchmakerAssignmentResponse> execute(final DeleteDeploymentMatchmakerAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    DeploymentState
     */

    @Override
    public Uni<GetDeploymentStateResponse> execute(final GetDeploymentStateRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<UpdateDeploymentStateResponse> execute(final UpdateDeploymentStateRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }
}
