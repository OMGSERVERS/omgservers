package com.omgservers.schema.module.deployment.deploymentLobbyAssignment;

import com.omgservers.schema.model.deploymentLobbyAssignment.DeploymentLobbyAssignmentModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncDeploymentLobbyAssignmentRequest implements ShardedRequest {

    @NotNull
    DeploymentLobbyAssignmentModel deploymentLobbyAssignment;

    @Override
    public String getRequestShardKey() {
        return deploymentLobbyAssignment.getDeploymentId().toString();
    }
}
