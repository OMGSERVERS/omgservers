package com.omgservers.ctl.command.developer.sub.version.sub;

import com.omgservers.ctl.command.UserCommand;
import com.omgservers.ctl.operation.command.developer.version.DeveloperVersionDeleteVersionOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "delete-version",
        description = "Delete a version by id or alias.")
public class DeveloperVersionDeleteVersionCommand extends UserCommand {

    @CommandLine.Parameters(description = "Id or alias of the tenant that owns the project.")
    String tenant;

    @CommandLine.Parameters(description = "Id or alias of the project whose version needs to be deleted.")
    String project;

    @CommandLine.Parameters(description = "Id or alias of the version to be deleted.")
    String version;

    @Inject
    DeveloperVersionDeleteVersionOperation developerVersionDeleteVersionOperation;

    @Override
    public void run() {
        developerVersionDeleteVersionOperation.execute(tenant,
                project,
                version,
                service,
                user);
    }
}
