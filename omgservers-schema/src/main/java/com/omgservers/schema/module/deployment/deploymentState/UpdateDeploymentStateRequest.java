package com.omgservers.schema.module.deployment.deploymentState;

import com.omgservers.schema.model.deploymentChangeOfState.DeploymentChangeOfStateDto;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDeploymentStateRequest implements ShardedRequest {

    @NotNull
    Long deploymentId;

    @NotNull
    DeploymentChangeOfStateDto deploymentChangeOfState;

    @Override
    public String getRequestShardKey() {
        return deploymentId.toString();
    }
}
