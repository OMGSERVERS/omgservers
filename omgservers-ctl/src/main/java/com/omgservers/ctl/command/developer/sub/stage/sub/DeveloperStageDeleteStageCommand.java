package com.omgservers.ctl.command.developer.sub.stage.sub;

import com.omgservers.ctl.command.UserCommand;
import com.omgservers.ctl.operation.command.developer.stage.DeveloperStageDeleteStageOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "delete-stage",
        description = "Delete a stage by id or alias.")
public class DeveloperStageDeleteStageCommand extends UserCommand {

    @CommandLine.Parameters(description = "Id or alias of the tenant that owns the project.")
    String tenant;

    @CommandLine.Parameters(description = "Id or alias of the project that owns the stage.")
    String project;

    @CommandLine.Parameters(description = "Id or alias of the stage to be deleted.")
    String stage;

    @Inject
    DeveloperStageDeleteStageOperation developerStageDeleteStageOperation;

    @Override
    public void run() {
        developerStageDeleteStageOperation.execute(tenant,
                project,
                stage,
                installation,
                user);
    }
}
