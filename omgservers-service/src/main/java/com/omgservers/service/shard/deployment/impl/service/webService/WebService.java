package com.omgservers.service.shard.deployment.impl.service.webService;

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

public interface WebService {

    /*
    Deployment
     */

    Uni<GetDeploymentResponse> execute(GetDeploymentRequest request);

    Uni<GetDeploymentDataResponse> execute(GetDeploymentDataRequest request);

    Uni<SyncDeploymentResponse> execute(SyncDeploymentRequest request);

    Uni<DeleteDeploymentResponse> execute(DeleteDeploymentRequest request);

    /*
    DeploymentCommand
     */

    Uni<ViewDeploymentCommandsResponse> execute(ViewDeploymentCommandsRequest request);

    Uni<SyncDeploymentCommandResponse> execute(SyncDeploymentCommandRequest request);

    Uni<DeleteDeploymentCommandResponse> execute(DeleteDeploymentCommandRequest request);

    /*
    DeploymentRequest
     */

    Uni<GetDeploymentRequestResponse> execute(GetDeploymentRequestRequest request);

    Uni<FindDeploymentRequestResponse> execute(FindDeploymentRequestRequest request);

    Uni<ViewDeploymentRequestsResponse> execute(ViewDeploymentRequestsRequest request);

    Uni<SyncDeploymentRequestResponse> execute(SyncDeploymentRequestRequest request);

    Uni<DeleteDeploymentRequestResponse> execute(DeleteDeploymentRequestRequest request);

    /*
    DeploymentLobbyResource
     */

    Uni<GetDeploymentLobbyResourceResponse> execute(GetDeploymentLobbyResourceRequest request);

    Uni<FindDeploymentLobbyResourceResponse> execute(FindDeploymentLobbyResourceRequest request);

    Uni<ViewDeploymentLobbyResourcesResponse> execute(ViewDeploymentLobbyResourcesRequest request);

    Uni<SyncDeploymentLobbyResourceResponse> execute(SyncDeploymentLobbyResourceRequest request);

    Uni<UpdateDeploymentLobbyResourceStatusResponse> execute(UpdateDeploymentLobbyResourceStatusRequest request);

    Uni<DeleteDeploymentLobbyResourceResponse> execute(DeleteDeploymentLobbyResourceRequest request);

    /*
    DeploymentLobbyAssignment
     */

    Uni<GetDeploymentLobbyAssignmentResponse> execute(GetDeploymentLobbyAssignmentRequest request);

    Uni<FindDeploymentLobbyAssignmentResponse> execute(FindDeploymentLobbyAssignmentRequest request);

    Uni<ViewDeploymentLobbyAssignmentsResponse> execute(ViewDeploymentLobbyAssignmentsRequest request);

    Uni<SyncDeploymentLobbyAssignmentResponse> execute(SyncDeploymentLobbyAssignmentRequest request);

    Uni<DeleteDeploymentLobbyAssignmentResponse> execute(DeleteDeploymentLobbyAssignmentRequest request);

    /*
    DeploymentMatchmakerResource
     */

    Uni<GetDeploymentMatchmakerResourceResponse> execute(GetDeploymentMatchmakerResourceRequest request);

    Uni<FindDeploymentMatchmakerResourceResponse> execute(FindDeploymentMatchmakerResourceRequest request);

    Uni<ViewDeploymentMatchmakerResourcesResponse> execute(ViewDeploymentMatchmakerResourcesRequest request);

    Uni<SyncDeploymentMatchmakerResourceResponse> execute(SyncDeploymentMatchmakerResourceRequest request);

    Uni<UpdateDeploymentMatchmakerResourceStatusResponse> execute(UpdateDeploymentMatchmakerResourceStatusRequest request);

    Uni<DeleteDeploymentMatchmakerResourceResponse> execute(DeleteDeploymentMatchmakerResourceRequest request);

    /*
    DeploymentMatchmakerAssignment
     */

    Uni<GetDeploymentMatchmakerAssignmentResponse> execute(GetDeploymentMatchmakerAssignmentRequest request);

    Uni<FindDeploymentMatchmakerAssignmentResponse> execute(FindDeploymentMatchmakerAssignmentRequest request);

    Uni<ViewDeploymentMatchmakerAssignmentsResponse> execute(ViewDeploymentMatchmakerAssignmentsRequest request);

    Uni<SyncDeploymentMatchmakerAssignmentResponse> execute(SyncDeploymentMatchmakerAssignmentRequest request);

    Uni<DeleteDeploymentMatchmakerAssignmentResponse> execute(DeleteDeploymentMatchmakerAssignmentRequest request);

    /*
    DeploymentState
     */

    Uni<GetDeploymentStateResponse> execute(GetDeploymentStateRequest request);

    Uni<UpdateDeploymentStateResponse> execute(UpdateDeploymentStateRequest request);
}
