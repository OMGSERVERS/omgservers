package com.omgservers.ctl.command.developer.sub.stage.sub;

import com.omgservers.ctl.command.InstallationCommand;
import com.omgservers.ctl.operation.command.developer.stage.DeveloperStageCreateAliasOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "create-alias",
        description = "Assign an alias to a project.")
public class DeveloperStageCreateAliasCommand extends InstallationCommand {

    @CommandLine.Parameters(description = "Id or alias of the tenant that owns the project containing the stage.")
    String tenant;

    @CommandLine.Parameters(description = "Id of the stage to assign the alias to.")
    Long stageId;

    @CommandLine.Parameters(description = "Alias to assign to the project.")
    String alias;

    @Inject
    DeveloperStageCreateAliasOperation developerStageCreateAliasOperation;

    @Override
    public void run() {
        developerStageCreateAliasOperation.execute(tenant,
                stageId,
                alias,
                installation);
    }
}
