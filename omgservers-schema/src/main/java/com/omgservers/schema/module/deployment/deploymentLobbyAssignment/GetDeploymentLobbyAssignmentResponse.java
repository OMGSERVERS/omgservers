package com.omgservers.schema.module.deployment.deploymentLobbyAssignment;

import com.omgservers.schema.model.deploymentLobbyAssignment.DeploymentLobbyAssignmentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDeploymentLobbyAssignmentResponse {

    DeploymentLobbyAssignmentModel deploymentLobbyAssignment;
}
