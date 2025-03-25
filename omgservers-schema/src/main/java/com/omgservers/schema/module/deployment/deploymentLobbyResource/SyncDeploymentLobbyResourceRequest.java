package com.omgservers.schema.module.deployment.deploymentLobbyResource;

import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncDeploymentLobbyResourceRequest implements ShardedRequest {

    @NotNull
    DeploymentLobbyResourceModel deploymentLobbyResource;

    @Override
    public String getRequestShardKey() {
        return deploymentLobbyResource.getDeploymentId().toString();
    }
}
