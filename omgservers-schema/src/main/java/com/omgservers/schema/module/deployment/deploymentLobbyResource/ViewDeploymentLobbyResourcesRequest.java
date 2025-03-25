package com.omgservers.schema.module.deployment.deploymentLobbyResource;

import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceStatusEnum;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewDeploymentLobbyResourcesRequest implements ShardedRequest {

    @NotNull
    Long deploymentId;

    DeploymentLobbyResourceStatusEnum status;

    @Override
    public String getRequestShardKey() {
        return deploymentId.toString();
    }
}
