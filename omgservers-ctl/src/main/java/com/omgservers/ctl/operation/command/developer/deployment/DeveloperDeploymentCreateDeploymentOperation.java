package com.omgservers.ctl.operation.command.developer.deployment;

import com.omgservers.schema.model.deployment.DeploymentConfigDto;

public interface DeveloperDeploymentCreateDeploymentOperation {

    void execute(String tenant,
                 String project,
                 String stage,
                 String version,
                 DeploymentConfigDto deploymentConfig,
                 String installation);
}
