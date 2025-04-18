package com.omgservers.schema.shard.deployment.deploymentRequest;

import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDeploymentRequestRequest implements ShardRequest {

    @NotNull
    Long deploymentId;

    @NotNull
    Long id;

    @Override
    public String getRequestShardKey() {
        return deploymentId.toString();
    }
}
