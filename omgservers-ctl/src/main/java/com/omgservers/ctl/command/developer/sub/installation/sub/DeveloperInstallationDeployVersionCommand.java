package com.omgservers.ctl.command.developer.sub.installation.sub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.ctl.command.InstallationCommand;
import com.omgservers.ctl.operation.command.developer.installation.DeveloperInstallationDeployVersionOperation;
import com.omgservers.ctl.operation.ctl.HandleFileOptionOperation;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "deploy-version",
        description = "Deploy a new version using the current installation.")
public class DeveloperInstallationDeployVersionCommand extends InstallationCommand {

    @CommandLine.Option(names = {"-c", "--config"},
            description = "Path to a file to read config from. Use '-' to read from stdin.",
            required = true)
    String config;

    @CommandLine.Option(names = {"-i", "--image"},
            description = "Docker image to deploy.",
            required = true)
    String image;

    @CommandLine.Parameters(description = "Id or alias of the developer user.")
    String developer;

    @CommandLine.Parameters(description = "Developer user password.")
    String password;

    @CommandLine.Parameters(description = "Id or alias of the tenant that owns the project.")
    String tenant;

    @CommandLine.Parameters(description = "Id or alias of the project where the version will be created.")
    String project;

    @CommandLine.Parameters(description = "Id or alias of the stage where the deployment will be created.")
    String stage;

    @Inject
    HandleFileOptionOperation handleFileOptionOperation;

    @Inject
    ObjectMapper objectMapper;

    @Inject
    DeveloperInstallationDeployVersionOperation developerInstallationDeployVersionOperation;

    @Override
    @SneakyThrows
    public void run() {
        final var configString = handleFileOptionOperation.execute(config);
        final var config = objectMapper.readValue(configString, TenantVersionConfigDto.class);

        developerInstallationDeployVersionOperation.execute(developer,
                password,
                tenant,
                project,
                stage,
                config,
                image,
                installation);
    }
}
