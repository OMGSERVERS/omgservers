package com.omgservers.schema.module.deployment.deploymentMatchmakerResource;

import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceStatusEnum;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewDeploymentMatchmakerResourcesRequest implements ShardedRequest {

    @NotNull
    Long deploymentId;

    DeploymentMatchmakerResourceStatusEnum status;

    @Override
    public String getRequestShardKey() {
        return deploymentId.toString();
    }
}
