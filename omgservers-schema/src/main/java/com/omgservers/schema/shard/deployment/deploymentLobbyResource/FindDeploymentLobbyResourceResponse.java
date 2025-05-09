package com.omgservers.schema.shard.deployment.deploymentLobbyResource;

import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindDeploymentLobbyResourceResponse {

    DeploymentLobbyResourceModel deploymentLobbyResource;
}
