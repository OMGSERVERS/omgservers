package com.omgservers.ctl.command.support.sub.stage.sub;

import com.omgservers.ctl.command.UserCommand;
import com.omgservers.ctl.operation.command.support.stage.SupportStageCreateAliasOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "create-alias",
        description = "Assign an alias to a stage.")
public class SupportStageCreateAliasCommand extends UserCommand {

    @CommandLine.Parameters(description = "Id or alias of the tenant that owns the project containing the stage.")
    String tenant;

    @CommandLine.Parameters(description = "Id of the stage to assign the alias to.")
    Long stageId;

    @CommandLine.Parameters(description = "Alias to assign to the project.")
    String alias;

    @Inject
    SupportStageCreateAliasOperation supportStageCreateAliasOperation;

    @Override
    public void run() {
        supportStageCreateAliasOperation.execute(tenant,
                stageId,
                alias,
                installation,
                user);
    }
}
