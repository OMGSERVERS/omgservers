package com.omgservers.schema.module.deployment.deploymentMatchmakerResource;

import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncDeploymentMatchmakerResourceRequest implements ShardedRequest {

    @NotNull
    DeploymentMatchmakerResourceModel deploymentMatchmakerResource;

    @Override
    public String getRequestShardKey() {
        return deploymentMatchmakerResource.getDeploymentId().toString();
    }
}
