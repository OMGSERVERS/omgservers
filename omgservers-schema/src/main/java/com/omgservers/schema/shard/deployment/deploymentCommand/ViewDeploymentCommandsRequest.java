package com.omgservers.schema.shard.deployment.deploymentCommand;

import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewDeploymentCommandsRequest implements ShardRequest {

    @NotNull
    Long deploymentId;

    @Override
    public String getRequestShardKey() {
        return deploymentId.toString();
    }
}
