package com.omgservers.schema.shard.deployment.deploymentMatchmakerResource;

import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDeploymentMatchmakerResourceResponse {

    DeploymentMatchmakerResourceModel deploymentMatchmakerResource;
}
