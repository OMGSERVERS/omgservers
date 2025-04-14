package com.omgservers.schema.shard.deployment.deployment;

import com.omgservers.schema.model.deployment.DeploymentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDeploymentResponse {

    DeploymentModel deployment;
}
