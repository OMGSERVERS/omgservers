package com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment;

import com.omgservers.schema.model.deploymentMatchmakerAssignment.DeploymentMatchmakerAssignmentModel;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncDeploymentMatchmakerAssignmentRequest implements ShardRequest {

    @NotNull
    DeploymentMatchmakerAssignmentModel deploymentMatchmakerAssignment;

    @Override
    public String getRequestShardKey() {
        return deploymentMatchmakerAssignment.getDeploymentId().toString();
    }
}
