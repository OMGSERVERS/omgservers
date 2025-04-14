package com.omgservers.schema.shard.deployment.deploymentLobbyResource;

import com.omgservers.schema.shard.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindDeploymentLobbyResourceRequest implements ShardedRequest {

    @NotNull
    Long deploymentId;

    @NotNull
    Long lobbyId;

    @Override
    public String getRequestShardKey() {
        return deploymentId.toString();
    }
}
