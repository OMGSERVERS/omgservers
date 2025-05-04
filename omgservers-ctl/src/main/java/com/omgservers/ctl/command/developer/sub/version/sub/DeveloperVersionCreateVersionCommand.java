package com.omgservers.ctl.command.developer.sub.version.sub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.ctl.command.UserCommand;
import com.omgservers.ctl.operation.command.developer.version.DeveloperVersionCreateVersionOperation;
import com.omgservers.ctl.operation.ctl.HandleFileOptionOperation;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "create-version",
        description = "Create a new version.")
public class DeveloperVersionCreateVersionCommand extends UserCommand {

    @CommandLine.Option(names = {"-c", "--config"},
            description = "Path to a file to read config from. Use '-' to read from stdin.",
            required = true)
    String filename;

    @CommandLine.Parameters(description = "Id or alias of the tenant that owns the project.")
    String tenant;

    @CommandLine.Parameters(description = "Id or alias of the project for which the version will be created.")
    String project;

    @Inject
    HandleFileOptionOperation handleFileOptionOperation;

    @Inject
    DeveloperVersionCreateVersionOperation developerVersionCreateVersionOperation;

    @Inject
    ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public void run() {
        final var versionConfigString = handleFileOptionOperation.execute(filename);

        final var config = objectMapper.readValue(versionConfigString, TenantVersionConfigDto.class);
        developerVersionCreateVersionOperation.execute(tenant,
                project,
                config,
                installation,
                user);
    }
}
