package com.omgservers.schema.shard.deployment.deploymentLobbyAssignment;

import com.omgservers.schema.model.deploymentLobbyAssignment.DeploymentLobbyAssignmentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewDeploymentLobbyAssignmentsResponse {

    List<DeploymentLobbyAssignmentModel> deploymentLobbyAssignments;
}
