package com.omgservers.ctl.command.support.sub.stage.sub;

import com.omgservers.ctl.command.UserCommand;
import com.omgservers.ctl.operation.command.support.stage.SupportStageCreateStageOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "create-stage",
        description = "Create a new stage.")
public class SupportStageCreateStageCommand extends UserCommand {

    @CommandLine.Parameters(description = "Id or alias of the tenant that owns the project.")
    String tenant;

    @CommandLine.Parameters(description = "Id or alias of the project to which the stage will be added.")
    String project;

    @Inject
    SupportStageCreateStageOperation supportStageCreateStageOperation;

    @Override
    public void run() {
        supportStageCreateStageOperation.execute(tenant, project, installation, user);
    }
}
