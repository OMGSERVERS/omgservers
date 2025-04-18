package com.omgservers.schema.shard.deployment.deploymentState;

import com.omgservers.schema.model.deploymentState.DeploymentStateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDeploymentStateResponse {

    DeploymentStateDto deploymentState;
}
