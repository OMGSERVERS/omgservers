package com.omgservers.ctl.command.developer.sub.deployment.sub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.ctl.command.InstallationCommand;
import com.omgservers.ctl.operation.command.developer.deployment.DeveloperDeploymentCreateDeploymentOperation;
import com.omgservers.ctl.operation.ctl.HandleFileOptionOperation;
import com.omgservers.schema.model.deployment.DeploymentConfigDto;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

import java.util.Objects;

@Slf4j
@CommandLine.Command(
        name = "create-deployment",
        description = "Create a new deployment.")
public class DeveloperDeploymentCreateDeploymentCommand extends InstallationCommand {

    @CommandLine.Option(names = {"-s", "--spec"},
            description = "Path to a file to read deployment spec from. Use '-' to read from stdin.")
    String spec;

    @CommandLine.Parameters(description = "Id or alias of the tenant that owns the project.")
    String tenant;

    @CommandLine.Parameters(description = "Id or alias of the project that owns the version.")
    String project;

    @CommandLine.Parameters(description = "Id or alias of the stage where the deployment will be created.")
    String stage;

    @CommandLine.Parameters(description = "Id or alias of the version for which the deployment will be created.")
    String version;

    @Inject
    HandleFileOptionOperation handleFileOptionOperation;

    @Inject
    DeveloperDeploymentCreateDeploymentOperation developerDeploymentCreateDeploymentOperation;

    @Inject
    ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public void run() {
        final DeploymentConfigDto deploymentConfig;
        if (Objects.isNull(spec)) {
            deploymentConfig = new DeploymentConfigDto();
        } else {
            final var deploymentConfigString = handleFileOptionOperation.execute(spec);
            deploymentConfig = objectMapper.readValue(deploymentConfigString, DeploymentConfigDto.class);
        }

        developerDeploymentCreateDeploymentOperation.execute(tenant,
                project,
                stage,
                version,
                deploymentConfig,
                installation);
    }
}
