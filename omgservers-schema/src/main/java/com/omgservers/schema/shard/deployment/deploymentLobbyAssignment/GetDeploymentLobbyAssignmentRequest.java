package com.omgservers.schema.shard.deployment.deploymentLobbyAssignment;

import com.omgservers.schema.shard.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDeploymentLobbyAssignmentRequest implements ShardedRequest {

    @NotNull
    Long deploymentId;

    @NotNull
    Long id;

    @Override
    public String getRequestShardKey() {
        return deploymentId.toString();
    }
}
