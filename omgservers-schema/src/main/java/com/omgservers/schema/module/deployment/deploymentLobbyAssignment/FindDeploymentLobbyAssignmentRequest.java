package com.omgservers.schema.module.deployment.deploymentLobbyAssignment;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindDeploymentLobbyAssignmentRequest implements ShardedRequest {

    @NotNull
    Long deploymentId;

    @NotNull
    Long clientId;

    @Override
    public String getRequestShardKey() {
        return deploymentId.toString();
    }
}
