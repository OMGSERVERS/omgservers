package com.omgservers.service.shard.deployment.impl.service.webService.impl;

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
import com.omgservers.service.shard.deployment.impl.service.deploymentService.DeploymentService;
import com.omgservers.service.shard.deployment.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class WebServiceImpl implements WebService {

    final DeploymentService deploymentService;

    /*
    Deployment
     */

    @Override
    public Uni<GetDeploymentResponse> execute(final GetDeploymentRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<GetDeploymentDataResponse> execute(final GetDeploymentDataRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<SyncDeploymentResponse> execute(final SyncDeploymentRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<DeleteDeploymentResponse> execute(final DeleteDeploymentRequest request) {
        return deploymentService.execute(request);
    }

    /*
    DeploymentCommand
     */

    @Override
    public Uni<ViewDeploymentCommandsResponse> execute(final ViewDeploymentCommandsRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<SyncDeploymentCommandResponse> execute(final SyncDeploymentCommandRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<DeleteDeploymentCommandResponse> execute(final DeleteDeploymentCommandRequest request) {
        return deploymentService.execute(request);
    }

    /*
    DeploymentRequest
     */

    @Override
    public Uni<GetDeploymentRequestResponse> execute(final GetDeploymentRequestRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<FindDeploymentRequestResponse> execute(final FindDeploymentRequestRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<ViewDeploymentRequestsResponse> execute(final ViewDeploymentRequestsRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<SyncDeploymentRequestResponse> execute(final SyncDeploymentRequestRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<DeleteDeploymentRequestResponse> execute(final DeleteDeploymentRequestRequest request) {
        return deploymentService.execute(request);
    }

    /*
    DeploymentLobbyResource
     */

    @Override
    public Uni<GetDeploymentLobbyResourceResponse> execute(final GetDeploymentLobbyResourceRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<FindDeploymentLobbyResourceResponse> execute(final FindDeploymentLobbyResourceRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<ViewDeploymentLobbyResourcesResponse> execute(final ViewDeploymentLobbyResourcesRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<SyncDeploymentLobbyResourceResponse> execute(final SyncDeploymentLobbyResourceRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<UpdateDeploymentLobbyResourceStatusResponse> execute(final UpdateDeploymentLobbyResourceStatusRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<DeleteDeploymentLobbyResourceResponse> execute(final DeleteDeploymentLobbyResourceRequest request) {
        return deploymentService.execute(request);
    }

    /*
    DeploymentLobbyAssignment
     */

    @Override
    public Uni<GetDeploymentLobbyAssignmentResponse> execute(final GetDeploymentLobbyAssignmentRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<FindDeploymentLobbyAssignmentResponse> execute(final FindDeploymentLobbyAssignmentRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<ViewDeploymentLobbyAssignmentsResponse> execute(final ViewDeploymentLobbyAssignmentsRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<SyncDeploymentLobbyAssignmentResponse> execute(final SyncDeploymentLobbyAssignmentRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<DeleteDeploymentLobbyAssignmentResponse> execute(final DeleteDeploymentLobbyAssignmentRequest request) {
        return deploymentService.execute(request);
    }

    /*
    DeploymentMatchmakerResource
     */

    @Override
    public Uni<GetDeploymentMatchmakerResourceResponse> execute(final GetDeploymentMatchmakerResourceRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<FindDeploymentMatchmakerResourceResponse> execute(final FindDeploymentMatchmakerResourceRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<ViewDeploymentMatchmakerResourcesResponse> execute(
            final ViewDeploymentMatchmakerResourcesRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<SyncDeploymentMatchmakerResourceResponse> execute(
            final SyncDeploymentMatchmakerResourceRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<UpdateDeploymentMatchmakerResourceStatusResponse> execute(
            final UpdateDeploymentMatchmakerResourceStatusRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<DeleteDeploymentMatchmakerResourceResponse> execute(
            final DeleteDeploymentMatchmakerResourceRequest request) {
        return deploymentService.execute(request);
    }

    /*
    DeploymentMatchmakerAssignment
     */

    @Override
    public Uni<GetDeploymentMatchmakerAssignmentResponse> execute(final GetDeploymentMatchmakerAssignmentRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<FindDeploymentMatchmakerAssignmentResponse> execute(final FindDeploymentMatchmakerAssignmentRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<ViewDeploymentMatchmakerAssignmentsResponse> execute(final ViewDeploymentMatchmakerAssignmentsRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<SyncDeploymentMatchmakerAssignmentResponse> execute(final SyncDeploymentMatchmakerAssignmentRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<DeleteDeploymentMatchmakerAssignmentResponse> execute(final DeleteDeploymentMatchmakerAssignmentRequest request) {
        return deploymentService.execute(request);
    }

    /*
    DeploymentState
     */

    @Override
    public Uni<GetDeploymentStateResponse> execute(final GetDeploymentStateRequest request) {
        return deploymentService.execute(request);
    }

    @Override
    public Uni<UpdateDeploymentStateResponse> execute(final UpdateDeploymentStateRequest request) {
        return deploymentService.execute(request);
    }
}
