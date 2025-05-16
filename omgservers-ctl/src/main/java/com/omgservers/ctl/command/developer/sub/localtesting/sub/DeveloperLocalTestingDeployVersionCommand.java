package com.omgservers.ctl.command.developer.sub.localtesting.sub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.ctl.operation.command.developer.localtesting.DeveloperLocalTestingDeployVersionOperation;
import com.omgservers.ctl.operation.ctl.HandleFileOptionOperation;
import com.omgservers.schema.model.deployment.DeploymentConfigDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

import java.util.Objects;

@Slf4j
@CommandLine.Command(
        name = "deploy-version",
        description = "Deploy a new version for local testing.")
public class DeveloperLocalTestingDeployVersionCommand implements Runnable {

    @CommandLine.Option(names = {"-c", "--config"},
            description = "Path to a file to read config from. Use '-' to read from stdin.",
            required = true)
    String config;

    @CommandLine.Option(names = {"-s", "--spec"},
            description = "Path to a file to read deployment spec from. Use '-' to read from stdin.")
    String spec;

    @CommandLine.Option(names = {"-i", "--image"},
            description = "Docker image to deploy.",
            required = true)
    String image;

    @CommandLine.Parameters(description = "Alias of the tenant to be used for local testing. Default is \"${DEFAULT-VALUE}\"",
            defaultValue = "omgservers")
    String tenant;

    @CommandLine.Parameters(description = "Alias of the project to be used for local testing. Default is \"${DEFAULT-VALUE}\"",
            defaultValue = "localtesting")
    String project;

    @CommandLine.Parameters(description = "Alias of the stage to be used for local testing. Default is \"${DEFAULT-VALUE}\"",
            defaultValue = "default")
    String stage;

    @Inject
    HandleFileOptionOperation handleFileOptionOperation;

    @Inject
    DeveloperLocalTestingDeployVersionOperation developerLocalTestingDeployVersionOperation;

    @Inject
    ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public void run() {
        final var versionConfigString = handleFileOptionOperation.execute(config);
        final var versionConfig = objectMapper.readValue(versionConfigString, TenantVersionConfigDto.class);

        final DeploymentConfigDto deploymentConfig;
        if (Objects.isNull(spec)) {
            deploymentConfig = new DeploymentConfigDto();
        } else {
            final var deploymentConfigString = handleFileOptionOperation.execute(spec);
            deploymentConfig = objectMapper.readValue(deploymentConfigString, DeploymentConfigDto.class);
        }

        developerLocalTestingDeployVersionOperation.execute(tenant,
                project,
                stage,
                image,
                versionConfig,
                deploymentConfig);
    }
}
