package com.omgservers.ctl.command.localtesting.sub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.ctl.operation.command.localtesting.test.LocalTestingDeployVersionOperation;
import com.omgservers.ctl.operation.ctl.HandleFileOptionOperation;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "deploy-version",
        description = "Deploy a new version for local testing.")
public class LocalTestingDeployVersionCommand implements Runnable {

    @CommandLine.Option(names = {"-c", "--config"},
            description = "Path to a file to read config from. Use '-' to read from stdin.",
            required = true)
    String config;

    @CommandLine.Option(names = {"-i", "--image"},
            description = "Image name to deploy.",
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
    LocalTestingDeployVersionOperation localTestingDeployVersionOperation;

    @Inject
    ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public void run() {
        final var configString = handleFileOptionOperation.execute(config);
        final var config = objectMapper.readValue(configString, TenantVersionConfigDto.class);

        localTestingDeployVersionOperation.execute(tenant, project, stage, config, image);
    }
}
