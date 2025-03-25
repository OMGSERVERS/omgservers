package com.omgservers.schema.module.deployment.deployment;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDeploymentDataRequest implements ShardedRequest {

    @NotNull
    Long deploymentId;

    @Override
    public String getRequestShardKey() {
        return deploymentId.toString();
    }
}
