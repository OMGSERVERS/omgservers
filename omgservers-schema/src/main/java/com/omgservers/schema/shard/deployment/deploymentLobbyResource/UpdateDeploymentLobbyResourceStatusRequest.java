package com.omgservers.schema.shard.deployment.deploymentLobbyResource;

import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceStatusEnum;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDeploymentLobbyResourceStatusRequest implements ShardRequest {

    @NotNull
    Long deploymentId;

    @NotNull
    Long id;

    @NotNull
    DeploymentLobbyResourceStatusEnum status;

    @Override
    public String getRequestShardKey() {
        return deploymentId.toString();
    }
}
