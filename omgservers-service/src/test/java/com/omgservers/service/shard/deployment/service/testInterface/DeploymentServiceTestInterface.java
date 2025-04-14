package com.omgservers.service.shard.deployment.service.testInterface;


import com.omgservers.schema.shard.deployment.deployment.*;
import com.omgservers.schema.shard.deployment.deploymentCommand.*;
import com.omgservers.schema.shard.deployment.deploymentLobbyAssignment.*;
import com.omgservers.schema.shard.deployment.deploymentLobbyResource.*;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.*;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerResource.*;
import com.omgservers.schema.shard.deployment.deploymentRequest.*;
import com.omgservers.schema.shard.deployment.deploymentState.GetDeploymentStateRequest;
import com.omgservers.schema.shard.deployment.deploymentState.GetDeploymentStateResponse;
import com.omgservers.schema.shard.deployment.deploymentState.UpdateDeploymentStateRequest;
import com.omgservers.schema.shard.deployment.deploymentState.UpdateDeploymentStateResponse;
import com.omgservers.service.shard.deployment.impl.service.deploymentService.DeploymentService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DeploymentServiceTestInterface {
    private static final long TIMEOUT = 1L;

    final DeploymentService deploymentService;

    /*
    Deployment
     */

    public GetDeploymentResponse execute(final GetDeploymentRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetDeploymentDataResponse execute(final GetDeploymentDataRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncDeploymentResponse execute(final SyncDeploymentRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncDeploymentResponse executeWithIdempotency(final SyncDeploymentRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteDeploymentResponse execute(final DeleteDeploymentRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    DeploymentCommand
     */

    public ViewDeploymentCommandsResponse execute(final ViewDeploymentCommandsRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncDeploymentCommandResponse execute(final SyncDeploymentCommandRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteDeploymentCommandResponse execute(final DeleteDeploymentCommandRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    DeploymentRequest
     */

    public GetDeploymentRequestResponse execute(final GetDeploymentRequestRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindDeploymentRequestResponse execute(final FindDeploymentRequestRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewDeploymentRequestsResponse execute(final ViewDeploymentRequestsRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncDeploymentRequestResponse execute(final SyncDeploymentRequestRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncDeploymentRequestResponse executeWithIdempotency(final SyncDeploymentRequestRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteDeploymentRequestResponse execute(final DeleteDeploymentRequestRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    DeploymentLobbyResource
     */

    public GetDeploymentLobbyResourceResponse execute(final GetDeploymentLobbyResourceRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindDeploymentLobbyResourceResponse execute(final FindDeploymentLobbyResourceRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewDeploymentLobbyResourcesResponse execute(final ViewDeploymentLobbyResourcesRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncDeploymentLobbyResourceResponse execute(final SyncDeploymentLobbyResourceRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public UpdateDeploymentLobbyResourceStatusResponse execute(final UpdateDeploymentLobbyResourceStatusRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncDeploymentLobbyResourceResponse executeWithIdempotency(final SyncDeploymentLobbyResourceRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteDeploymentLobbyResourceResponse execute(final DeleteDeploymentLobbyResourceRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    DeploymentLobbyAssignment
     */

    public GetDeploymentLobbyAssignmentResponse execute(final GetDeploymentLobbyAssignmentRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindDeploymentLobbyAssignmentResponse execute(final FindDeploymentLobbyAssignmentRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewDeploymentLobbyAssignmentsResponse execute(final ViewDeploymentLobbyAssignmentsRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncDeploymentLobbyAssignmentResponse execute(final SyncDeploymentLobbyAssignmentRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteDeploymentLobbyAssignmentResponse execute(final DeleteDeploymentLobbyAssignmentRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    DeploymentMatchmakerResource
     */

    public GetDeploymentMatchmakerResourceResponse execute(final GetDeploymentMatchmakerResourceRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindDeploymentMatchmakerResourceResponse execute(final FindDeploymentMatchmakerResourceRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewDeploymentMatchmakerResourcesResponse execute(final ViewDeploymentMatchmakerResourcesRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncDeploymentMatchmakerResourceResponse execute(final SyncDeploymentMatchmakerResourceRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncDeploymentMatchmakerResourceResponse executeWithIdempotency(final SyncDeploymentMatchmakerResourceRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public UpdateDeploymentMatchmakerResourceStatusResponse execute(final UpdateDeploymentMatchmakerResourceStatusRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteDeploymentMatchmakerResourceResponse execute(final DeleteDeploymentMatchmakerResourceRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    DeploymentMatchmakerAssignment
     */

    public GetDeploymentMatchmakerAssignmentResponse execute(final GetDeploymentMatchmakerAssignmentRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindDeploymentMatchmakerAssignmentResponse execute(final FindDeploymentMatchmakerAssignmentRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewDeploymentMatchmakerAssignmentsResponse execute(final ViewDeploymentMatchmakerAssignmentsRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncDeploymentMatchmakerAssignmentResponse execute(final SyncDeploymentMatchmakerAssignmentRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteDeploymentMatchmakerAssignmentResponse execute(final DeleteDeploymentMatchmakerAssignmentRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    DeploymentState
     */

    public GetDeploymentStateResponse execute(final GetDeploymentStateRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public UpdateDeploymentStateResponse execute(final UpdateDeploymentStateRequest request) {
        return deploymentService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
