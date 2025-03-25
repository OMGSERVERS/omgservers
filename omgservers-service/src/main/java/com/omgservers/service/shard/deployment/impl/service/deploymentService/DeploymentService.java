package com.omgservers.service.shard.deployment.impl.service.deploymentService;

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
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface DeploymentService {

    /*
    Deployment
     */

    Uni<GetDeploymentResponse> execute(@Valid GetDeploymentRequest request);

    Uni<GetDeploymentDataResponse> execute(@Valid GetDeploymentDataRequest request);

    Uni<SyncDeploymentResponse> execute(@Valid SyncDeploymentRequest request);

    Uni<SyncDeploymentResponse> executeWithIdempotency(@Valid SyncDeploymentRequest request);

    Uni<DeleteDeploymentResponse> execute(@Valid DeleteDeploymentRequest request);

    /*
    DeploymentCommand
     */

    Uni<ViewDeploymentCommandsResponse> execute(@Valid ViewDeploymentCommandsRequest request);

    Uni<SyncDeploymentCommandResponse> execute(@Valid SyncDeploymentCommandRequest request);

    Uni<SyncDeploymentCommandResponse> executeWithIdempotency(@Valid SyncDeploymentCommandRequest request);

    Uni<DeleteDeploymentCommandResponse> execute(@Valid DeleteDeploymentCommandRequest request);

    /*
    DeploymentRequest
     */

    Uni<GetDeploymentRequestResponse> execute(@Valid GetDeploymentRequestRequest request);

    Uni<FindDeploymentRequestResponse> execute(@Valid FindDeploymentRequestRequest request);

    Uni<ViewDeploymentRequestsResponse> execute(@Valid ViewDeploymentRequestsRequest request);

    Uni<SyncDeploymentRequestResponse> execute(@Valid SyncDeploymentRequestRequest request);

    Uni<SyncDeploymentRequestResponse> executeWithIdempotency(@Valid SyncDeploymentRequestRequest request);

    Uni<DeleteDeploymentRequestResponse> execute(@Valid DeleteDeploymentRequestRequest request);

    /*
    DeploymentLobbyResource
     */

    Uni<GetDeploymentLobbyResourceResponse> execute(@Valid GetDeploymentLobbyResourceRequest request);

    Uni<FindDeploymentLobbyResourceResponse> execute(@Valid FindDeploymentLobbyResourceRequest request);

    Uni<ViewDeploymentLobbyResourcesResponse> execute(@Valid ViewDeploymentLobbyResourcesRequest request);

    Uni<SyncDeploymentLobbyResourceResponse> execute(@Valid SyncDeploymentLobbyResourceRequest request);

    Uni<UpdateDeploymentLobbyResourceStatusResponse> execute(@Valid UpdateDeploymentLobbyResourceStatusRequest request);

    Uni<SyncDeploymentLobbyResourceResponse> executeWithIdempotency(@Valid SyncDeploymentLobbyResourceRequest request);

    Uni<DeleteDeploymentLobbyResourceResponse> execute(@Valid DeleteDeploymentLobbyResourceRequest request);

    /*
    DeploymentLobbyAssignment
     */

    Uni<GetDeploymentLobbyAssignmentResponse> execute(@Valid GetDeploymentLobbyAssignmentRequest request);

    Uni<FindDeploymentLobbyAssignmentResponse> execute(@Valid FindDeploymentLobbyAssignmentRequest request);

    Uni<ViewDeploymentLobbyAssignmentsResponse> execute(@Valid ViewDeploymentLobbyAssignmentsRequest request);

    Uni<SyncDeploymentLobbyAssignmentResponse> execute(@Valid SyncDeploymentLobbyAssignmentRequest request);

    Uni<DeleteDeploymentLobbyAssignmentResponse> execute(@Valid DeleteDeploymentLobbyAssignmentRequest request);

    /*
    DeploymentMatchmakerResource
     */

    Uni<GetDeploymentMatchmakerResourceResponse> execute(@Valid GetDeploymentMatchmakerResourceRequest request);

    Uni<FindDeploymentMatchmakerResourceResponse> execute(@Valid FindDeploymentMatchmakerResourceRequest request);

    Uni<ViewDeploymentMatchmakerResourcesResponse> execute(@Valid ViewDeploymentMatchmakerResourcesRequest request);

    Uni<SyncDeploymentMatchmakerResourceResponse> execute(@Valid SyncDeploymentMatchmakerResourceRequest request);

    Uni<SyncDeploymentMatchmakerResourceResponse> executeWithIdempotency(@Valid SyncDeploymentMatchmakerResourceRequest request);

    Uni<UpdateDeploymentMatchmakerResourceStatusResponse> execute(
            @Valid UpdateDeploymentMatchmakerResourceStatusRequest request);

    Uni<DeleteDeploymentMatchmakerResourceResponse> execute(@Valid DeleteDeploymentMatchmakerResourceRequest request);

    /*
    DeploymentMatchmakerAssignment
     */

    Uni<GetDeploymentMatchmakerAssignmentResponse> execute(@Valid GetDeploymentMatchmakerAssignmentRequest request);

    Uni<FindDeploymentMatchmakerAssignmentResponse> execute(@Valid FindDeploymentMatchmakerAssignmentRequest request);

    Uni<ViewDeploymentMatchmakerAssignmentsResponse> execute(@Valid ViewDeploymentMatchmakerAssignmentsRequest request);

    Uni<SyncDeploymentMatchmakerAssignmentResponse> execute(@Valid SyncDeploymentMatchmakerAssignmentRequest request);

    Uni<DeleteDeploymentMatchmakerAssignmentResponse> execute(@Valid DeleteDeploymentMatchmakerAssignmentRequest request);

    /*
    DeploymentState
     */

    Uni<GetDeploymentStateResponse> execute(@Valid GetDeploymentStateRequest request);

    Uni<UpdateDeploymentStateResponse> execute(@Valid UpdateDeploymentStateRequest request);
}
