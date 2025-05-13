package com.omgservers.ctl.command.developer.sub.deployment.sub;

import com.omgservers.ctl.command.InstallationCommand;
import com.omgservers.ctl.operation.command.developer.deployment.DeveloperDeploymentDeleteDeploymentOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "delete-deployment",
        description = "Delete a deployment by id or alias.")
public class DeveloperDeploymentDeleteDeploymentCommand extends InstallationCommand {

    @CommandLine.Parameters(description = "Id of the deployment to be deleted.")
    Long deploymentId;

    @Inject
    DeveloperDeploymentDeleteDeploymentOperation developerDeploymentDeleteDeploymentOperation;

    @Override
    public void run() {
        developerDeploymentDeleteDeploymentOperation.execute(deploymentId, installation);
    }
}
