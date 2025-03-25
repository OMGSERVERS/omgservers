package com.omgservers.schema.model.deploymentState;

import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.schema.model.deploymentCommand.DeploymentCommandModel;
import com.omgservers.schema.model.deploymentLobbyAssignment.DeploymentLobbyAssignmentModel;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.schema.model.deploymentMatchmakerAssignment.DeploymentMatchmakerAssignmentModel;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.schema.model.deploymentRequest.DeploymentRequestModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeploymentStateDto {

    @NotNull
    DeploymentModel deployment;

    @NotNull
    List<DeploymentCommandModel> deploymentCommands;

    @NotNull
    List<DeploymentRequestModel> deploymentRequests;

    @NotNull
    List<DeploymentLobbyResourceModel> deploymentLobbyResources;

    @NotNull
    List<DeploymentLobbyAssignmentModel> deploymentLobbyAssignments;

    @NotNull
    List<DeploymentMatchmakerResourceModel> deploymentMatchmakerResources;

    @NotNull
    List<DeploymentMatchmakerAssignmentModel> deploymentMatchmakerAssignments;
}
