package com.omgservers.ctl.command.developer.sub.deployment.sub;

import com.omgservers.ctl.command.InstallationCommand;
import com.omgservers.ctl.operation.command.developer.deployment.DeveloperDeploymentCreateDeploymentOperation;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "create-deployment",
        description = "Create a new deployment.")
public class DeveloperDeploymentCreateDeploymentCommand extends InstallationCommand {

    @CommandLine.Parameters(description = "Id or alias of the tenant that owns the project.")
    String tenant;

    @CommandLine.Parameters(description = "Id or alias of the project that owns the version.")
    String project;

    @CommandLine.Parameters(description = "Id or alias of the stage where the deployment will be created.")
    String stage;

    @CommandLine.Parameters(description = "Id or alias of the version for which the deployment will be created.")
    String version;

    @Inject
    DeveloperDeploymentCreateDeploymentOperation developerDeploymentCreateDeploymentOperation;

    @Override
    @SneakyThrows
    public void run() {
        developerDeploymentCreateDeploymentOperation.execute(tenant,
                project,
                stage,
                version,
                installation);
    }
}
