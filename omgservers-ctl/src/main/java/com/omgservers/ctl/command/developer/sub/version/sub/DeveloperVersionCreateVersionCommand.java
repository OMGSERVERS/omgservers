package com.omgservers.ctl.command.developer.sub.version.sub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.ctl.command.UserCommand;
import com.omgservers.ctl.operation.command.developer.version.DeveloperVersionCreateVersionOperation;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Slf4j
@CommandLine.Command(
        name = "create-version",
        description = "Create a new version.")
public class DeveloperVersionCreateVersionCommand extends UserCommand {

    @CommandLine.Option(names = {"-f", "--filename"},
            description = "Path to a file to read config from. Use '-' to read from stdin.",
            required = true)
    protected String filename;

    @CommandLine.Parameters(description = "Id or alias of the tenant that owns the project.")
    String tenant;

    @CommandLine.Parameters(description = "Id or alias of the project for which the version will be created.")
    String project;

    @Inject
    DeveloperVersionCreateVersionOperation developerVersionCreateVersionOperation;

    @Inject
    ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public void run() {
        final InputStream inputStream;
        if ("-".equals(filename)) {
            inputStream = System.in;
        } else {
            inputStream = new FileInputStream(filename);
        }

        final var versionConfigString = new BufferedReader(new InputStreamReader(inputStream))
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));

        final var config = objectMapper.readValue(versionConfigString, TenantVersionConfigDto.class);
        developerVersionCreateVersionOperation.execute(tenant,
                project,
                config,
                service,
                user);
    }
}
