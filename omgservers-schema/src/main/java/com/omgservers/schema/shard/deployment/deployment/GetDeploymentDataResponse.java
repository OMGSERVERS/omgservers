package com.omgservers.schema.shard.deployment.deployment;

import com.omgservers.schema.shard.deployment.deployment.dto.DeploymentDataDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDeploymentDataResponse {

    DeploymentDataDto deploymentData;
}
