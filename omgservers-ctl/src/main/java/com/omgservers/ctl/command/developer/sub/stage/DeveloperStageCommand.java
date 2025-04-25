package com.omgservers.ctl.command.developer.sub.stage;

import com.omgservers.ctl.command.ParentCommand;
import com.omgservers.ctl.command.developer.sub.stage.sub.DeveloperStageCreateAliasCommand;
import com.omgservers.ctl.command.developer.sub.stage.sub.DeveloperStageCreateStageCommand;
import com.omgservers.ctl.command.developer.sub.stage.sub.DeveloperStageDeleteStageCommand;
import com.omgservers.ctl.command.developer.sub.stage.sub.DeveloperStageGetDetailsCommand;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "stage",
        description = "Developer commands for managing stages.",
        subcommands = {
                DeveloperStageCreateStageCommand.class,
                DeveloperStageCreateAliasCommand.class,
                DeveloperStageGetDetailsCommand.class,
                DeveloperStageDeleteStageCommand.class,
        })
public class DeveloperStageCommand extends ParentCommand {
}
