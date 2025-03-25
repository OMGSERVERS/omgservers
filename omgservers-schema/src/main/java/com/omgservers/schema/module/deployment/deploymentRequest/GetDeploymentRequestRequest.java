package com.omgservers.schema.module.deployment.deploymentRequest;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDeploymentRequestRequest implements ShardedRequest {

    @NotNull
    Long deploymentId;

    @NotNull
    Long id;

    @Override
    public String getRequestShardKey() {
        return deploymentId.toString();
    }
}
