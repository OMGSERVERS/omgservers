package com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment;

import com.omgservers.schema.model.deploymentMatchmakerAssignment.DeploymentMatchmakerAssignmentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDeploymentMatchmakerAssignmentResponse {

    DeploymentMatchmakerAssignmentModel deploymentMatchmakerAssignment;
}
