package com.omgservers.ctl.command.support.sub.stage.sub;

import com.omgservers.ctl.command.UserCommand;
import com.omgservers.ctl.operation.command.support.stage.SupportStageDeleteStageOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "delete-stage",
        description = "Delete a stage by id or alias.")
public class SupportStageDeleteStageCommand extends UserCommand {

    @CommandLine.Parameters(description = "Id or alias of the tenant that owns the project.")
    String tenant;

    @CommandLine.Parameters(description = "Id or alias of the project that owns the stage.")
    String project;

    @CommandLine.Parameters(description = "Id or alias of the stage to be deleted.")
    String stage;

    @Inject
    SupportStageDeleteStageOperation supportStageDeleteStageOperation;

    @Override
    public void run() {
        supportStageDeleteStageOperation.execute(tenant, project, stage, service, user);
    }
}
