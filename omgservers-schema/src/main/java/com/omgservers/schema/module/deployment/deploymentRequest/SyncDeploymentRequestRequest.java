package com.omgservers.schema.module.deployment.deploymentRequest;

import com.omgservers.schema.model.deploymentRequest.DeploymentRequestModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncDeploymentRequestRequest implements ShardedRequest {

    @NotNull
    DeploymentRequestModel deploymentRequest;

    @Override
    public String getRequestShardKey() {
        return deploymentRequest.getDeploymentId().toString();
    }
}
