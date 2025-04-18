package com.omgservers.schema.shard.deployment.deployment;

import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncDeploymentRequest implements ShardRequest {

    @NotNull
    DeploymentModel deployment;

    @Override
    public String getRequestShardKey() {
        return deployment.getId().toString();
    }
}
