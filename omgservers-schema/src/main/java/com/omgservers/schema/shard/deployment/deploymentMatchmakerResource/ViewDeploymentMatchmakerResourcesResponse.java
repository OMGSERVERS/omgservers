package com.omgservers.schema.shard.deployment.deploymentMatchmakerResource;

import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewDeploymentMatchmakerResourcesResponse {

    List<DeploymentMatchmakerResourceModel> deploymentMatchmakerResources;
}
