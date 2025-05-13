package com.omgservers.ctl.command.developer.sub.deployment.sub;

import com.omgservers.ctl.command.InstallationCommand;
import com.omgservers.ctl.operation.command.developer.deployment.DeveloperDeploymentGetDetailsOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "get-details",
        description = "Get details of a deployment.")
public class DeveloperDeploymentGetDetailsCommand extends InstallationCommand {

    @CommandLine.Parameters(description = "Id of the deployment to get details for.")
    Long deploymentId;

    @Inject
    DeveloperDeploymentGetDetailsOperation developerDeploymentGetDetailsOperation;

    @Override
    public void run() {
        developerDeploymentGetDetailsOperation.execute(deploymentId, installation);
    }
}
