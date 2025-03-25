package com.omgservers.service.shard.deployment.impl.service.webService.impl.api;

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
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Deployment Shard API")
@Path("/service/v1/shard/deployment/request")
public interface DeploymentApi {

    /*
    Deployment
     */

    @POST
    @Path("/get-deployment")
    Uni<GetDeploymentResponse> execute(GetDeploymentRequest request);

    @POST
    @Path("/get-deployment-data")
    Uni<GetDeploymentDataResponse> execute(GetDeploymentDataRequest request);

    @POST
    @Path("/sync-deployment")
    Uni<SyncDeploymentResponse> execute(SyncDeploymentRequest request);

    @POST
    @Path("/delete-deployment")
    Uni<DeleteDeploymentResponse> execute(DeleteDeploymentRequest request);

    /*
    DeploymentCommand
     */

    @POST
    @Path("/view-deployment-commands")
    Uni<ViewDeploymentCommandsResponse> execute(ViewDeploymentCommandsRequest request);

    @POST
    @Path("/sync-deployment-command")
    Uni<SyncDeploymentCommandResponse> execute(SyncDeploymentCommandRequest request);

    @POST
    @Path("/delete-deployment-command")
    Uni<DeleteDeploymentCommandResponse> execute(DeleteDeploymentCommandRequest request);

    /*
    DeploymentRequest
     */

    @POST
    @Path("/get-deployment-request")
    Uni<GetDeploymentRequestResponse> execute(GetDeploymentRequestRequest request);

    @POST
    @Path("/find-deployment-request")
    Uni<FindDeploymentRequestResponse> execute(FindDeploymentRequestRequest request);

    @POST
    @Path("/view-deployment-matchmakerRequests")
    Uni<ViewDeploymentRequestsResponse> execute(ViewDeploymentRequestsRequest request);

    @POST
    @Path("/sync-deployment-request")
    Uni<SyncDeploymentRequestResponse> execute(SyncDeploymentRequestRequest request);

    @POST
    @Path("/delete-deployment-request")
    Uni<DeleteDeploymentRequestResponse> execute(DeleteDeploymentRequestRequest request);

    /*
    DeploymentLobbyResource
     */

    @POST
    @Path("/get-deployment-lobby-resource")
    Uni<GetDeploymentLobbyResourceResponse> execute(GetDeploymentLobbyResourceRequest request);

    @POST
    @Path("/find-deployment-lobby-resource")
    Uni<FindDeploymentLobbyResourceResponse> execute(FindDeploymentLobbyResourceRequest request);

    @POST
    @Path("/view-deployment-lobby-resources")
    Uni<ViewDeploymentLobbyResourcesResponse> execute(ViewDeploymentLobbyResourcesRequest request);

    @POST
    @Path("/sync-deployment-lobby-resource")
    Uni<SyncDeploymentLobbyResourceResponse> execute(SyncDeploymentLobbyResourceRequest request);

    @POST
    @Path("/update-deployment-lobby-resource-status")
    Uni<UpdateDeploymentLobbyResourceStatusResponse> execute(UpdateDeploymentLobbyResourceStatusRequest request);

    @POST
    @Path("/delete-deployment-lobby-resource")
    Uni<DeleteDeploymentLobbyResourceResponse> execute(DeleteDeploymentLobbyResourceRequest request);

    /*
    DeploymentLobbyAssignment
     */

    @POST
    @Path("/get-deployment-lobby-assignment")
    Uni<GetDeploymentLobbyAssignmentResponse> execute(GetDeploymentLobbyAssignmentRequest request);

    @POST
    @Path("/find-deployment-lobby-assignment")
    Uni<FindDeploymentLobbyAssignmentResponse> execute(FindDeploymentLobbyAssignmentRequest request);

    @POST
    @Path("/view-deployment-lobby-assignments")
    Uni<ViewDeploymentLobbyAssignmentsResponse> execute(ViewDeploymentLobbyAssignmentsRequest request);

    @POST
    @Path("/sync-deployment-lobby-assignment")
    Uni<SyncDeploymentLobbyAssignmentResponse> execute(SyncDeploymentLobbyAssignmentRequest request);

    @POST
    @Path("/delete-deployment-lobby-assignment")
    Uni<DeleteDeploymentLobbyAssignmentResponse> execute(DeleteDeploymentLobbyAssignmentRequest request);

    /*
    DeploymentMatchmakerResource
     */

    @POST
    @Path("/get-deployment-matchmaker-resource")
    Uni<GetDeploymentMatchmakerResourceResponse> execute(GetDeploymentMatchmakerResourceRequest request);

    @POST
    @Path("/find-deployment-matchmaker-resource")
    Uni<FindDeploymentMatchmakerResourceResponse> execute(FindDeploymentMatchmakerResourceRequest request);

    @POST
    @Path("/view-deployment-matchmaker-resources")
    Uni<ViewDeploymentMatchmakerResourcesResponse> execute(ViewDeploymentMatchmakerResourcesRequest request);

    @POST
    @Path("/sync-deployment-matchmaker-resource")
    Uni<SyncDeploymentMatchmakerResourceResponse> execute(SyncDeploymentMatchmakerResourceRequest request);

    @POST
    @Path("/update-deployment-matchmaker-resource-status")
    Uni<UpdateDeploymentMatchmakerResourceStatusResponse> execute(UpdateDeploymentMatchmakerResourceStatusRequest request);

    @POST
    @Path("/delete-deployment-matchmaker-resource")
    Uni<DeleteDeploymentMatchmakerResourceResponse> execute(DeleteDeploymentMatchmakerResourceRequest request);

    /*
    DeploymentMatchmakerAssignment
     */

    @POST
    @Path("/get-deployment-matchmaker-assignment")
    Uni<GetDeploymentMatchmakerAssignmentResponse> execute(GetDeploymentMatchmakerAssignmentRequest request);

    @POST
    @Path("/find-deployment-matchmaker-assignment")
    Uni<FindDeploymentMatchmakerAssignmentResponse> execute(FindDeploymentMatchmakerAssignmentRequest request);

    @POST
    @Path("/view-deployment-matchmaker-assignments")
    Uni<ViewDeploymentMatchmakerAssignmentsResponse> execute(ViewDeploymentMatchmakerAssignmentsRequest request);

    @POST
    @Path("/sync-deployment-matchmaker-assignment")
    Uni<SyncDeploymentMatchmakerAssignmentResponse> execute(SyncDeploymentMatchmakerAssignmentRequest request);

    @POST
    @Path("/delete-deployment-matchmaker-assignment")
    Uni<DeleteDeploymentMatchmakerAssignmentResponse> execute(DeleteDeploymentMatchmakerAssignmentRequest request);

    /*
    DeploymentState
     */

    @POST
    @Path("/get-deployment-state")
    Uni<GetDeploymentStateResponse> execute(GetDeploymentStateRequest request);

    @POST
    @Path("/update-deployment-state")
    Uni<UpdateDeploymentStateResponse> execute(UpdateDeploymentStateRequest request);
}
