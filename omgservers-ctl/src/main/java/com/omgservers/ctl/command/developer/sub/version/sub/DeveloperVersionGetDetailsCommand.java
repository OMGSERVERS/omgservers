package com.omgservers.ctl.command.developer.sub.version.sub;

import com.omgservers.ctl.command.InstallationCommand;
import com.omgservers.ctl.operation.command.developer.version.DeveloperVersionGetDetailsOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "get-details",
        description = "Get details of a version.")
public class DeveloperVersionGetDetailsCommand extends InstallationCommand {

    @CommandLine.Parameters(description = "Id or alias of the tenant that owns the project.")
    String tenant;

    @CommandLine.Parameters(description = "Id or alias of the project whose version details need to be retrieved.")
    String project;

    @CommandLine.Parameters(description = "Id or alias of the version to get details.")
    String version;

    @Inject
    DeveloperVersionGetDetailsOperation developerVersionGetDetailsOperation;

    @Override
    public void run() {
        developerVersionGetDetailsOperation.execute(tenant,
                project,
                version,
                installation);
    }
}
