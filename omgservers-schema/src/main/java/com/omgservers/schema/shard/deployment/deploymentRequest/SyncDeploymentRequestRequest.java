package com.omgservers.schema.shard.deployment.deploymentRequest;

import com.omgservers.schema.model.deploymentRequest.DeploymentRequestModel;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncDeploymentRequestRequest implements ShardRequest {

    @NotNull
    DeploymentRequestModel deploymentRequest;

    @Override
    public String getRequestShardKey() {
        return deploymentRequest.getDeploymentId().toString();
    }
}
