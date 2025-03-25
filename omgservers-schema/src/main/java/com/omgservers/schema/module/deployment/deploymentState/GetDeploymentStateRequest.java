package com.omgservers.schema.module.deployment.deploymentState;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDeploymentStateRequest implements ShardedRequest {

    @NotNull
    Long deploymentId;

    @Override
    public String getRequestShardKey() {
        return deploymentId.toString();
    }
}
