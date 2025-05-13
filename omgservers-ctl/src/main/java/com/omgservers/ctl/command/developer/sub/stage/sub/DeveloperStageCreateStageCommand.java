package com.omgservers.ctl.command.developer.sub.stage.sub;

import com.omgservers.ctl.command.InstallationCommand;
import com.omgservers.ctl.operation.command.developer.stage.DeveloperStageCreateStageOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "create-stage",
        description = "Create a new stage.")
public class DeveloperStageCreateStageCommand extends InstallationCommand {

    @CommandLine.Parameters(description = "Id or alias of the tenant that owns the project.")
    String tenant;

    @CommandLine.Parameters(description = "Id or alias of the project to which the stage will be added.")
    String project;

    @Inject
    DeveloperStageCreateStageOperation developerStageCreateStageOperation;

    @Override
    public void run() {
        developerStageCreateStageOperation.execute(tenant,
                project,
                installation);
    }
}
