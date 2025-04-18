package com.omgservers.schema.shard.deployment.deploymentMatchmakerResource;

import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceStatusEnum;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewDeploymentMatchmakerResourcesRequest implements ShardRequest {

    @NotNull
    Long deploymentId;

    DeploymentMatchmakerResourceStatusEnum status;

    @Override
    public String getRequestShardKey() {
        return deploymentId.toString();
    }
}
