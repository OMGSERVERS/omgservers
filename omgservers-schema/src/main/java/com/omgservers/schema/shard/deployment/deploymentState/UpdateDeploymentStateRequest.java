package com.omgservers.schema.shard.deployment.deploymentState;

import com.omgservers.schema.model.deploymentChangeOfState.DeploymentChangeOfStateDto;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDeploymentStateRequest implements ShardRequest {

    @NotNull
    Long deploymentId;

    @NotNull
    DeploymentChangeOfStateDto deploymentChangeOfState;

    @Override
    public String getRequestShardKey() {
        return deploymentId.toString();
    }
}
