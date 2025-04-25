package com.omgservers.ctl.command.developer.sub.stage.sub;

import com.omgservers.ctl.command.UserCommand;
import com.omgservers.ctl.operation.command.developer.stage.DeveloperStageGetDetailsOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "get-details",
        description = "Get details of a stage.")
public class DeveloperStageGetDetailsCommand extends UserCommand {

    @CommandLine.Parameters(description = "Id or alias of the tenant that owns the project.")
    String tenant;

    @CommandLine.Parameters(description = "Id or alias of the project that owns the stage.")
    String project;

    @CommandLine.Parameters(description = "Id or alias of the stage to get details.")
    String stage;

    @Inject
    DeveloperStageGetDetailsOperation developerStageGetDetailsOperation;

    @Override
    public void run() {
        developerStageGetDetailsOperation.execute(tenant, project, stage, service, user, prettyPrint);
    }
}
