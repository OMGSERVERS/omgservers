package com.omgservers.schema.shard.deployment.deploymentCommand;

import com.omgservers.schema.model.deploymentCommand.DeploymentCommandModel;
import com.omgservers.schema.shard.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncDeploymentCommandRequest implements ShardedRequest {

    @NotNull
    DeploymentCommandModel deploymentCommand;

    @Override
    public String getRequestShardKey() {
        return deploymentCommand.getDeploymentId().toString();
    }
}
