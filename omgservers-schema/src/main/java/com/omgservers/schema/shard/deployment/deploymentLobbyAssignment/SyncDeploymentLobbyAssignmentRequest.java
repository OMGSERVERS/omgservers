package com.omgservers.schema.shard.deployment.deploymentLobbyAssignment;

import com.omgservers.schema.model.deploymentLobbyAssignment.DeploymentLobbyAssignmentModel;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncDeploymentLobbyAssignmentRequest implements ShardRequest {

    @NotNull
    DeploymentLobbyAssignmentModel deploymentLobbyAssignment;

    @Override
    public String getRequestShardKey() {
        return deploymentLobbyAssignment.getDeploymentId().toString();
    }
}
