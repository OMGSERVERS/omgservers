package com.omgservers.schema.shard.deployment.deploymentLobbyAssignment;

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
