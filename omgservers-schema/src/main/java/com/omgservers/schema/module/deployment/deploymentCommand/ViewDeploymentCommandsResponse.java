package com.omgservers.schema.module.deployment.deploymentCommand;

import com.omgservers.schema.model.deploymentCommand.DeploymentCommandModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewDeploymentCommandsResponse {

    List<DeploymentCommandModel> deploymentCommands;
}
