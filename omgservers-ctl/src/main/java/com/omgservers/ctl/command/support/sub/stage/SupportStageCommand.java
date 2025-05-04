package com.omgservers.ctl.command.support.sub.stage;

import com.omgservers.ctl.command.ParentCommand;
import com.omgservers.ctl.command.support.sub.stage.sub.SupportStageCreateAliasCommand;
import com.omgservers.ctl.command.support.sub.stage.sub.SupportStageCreatePermissionCommand;
import com.omgservers.ctl.command.support.sub.stage.sub.SupportStageCreateStageCommand;
import com.omgservers.ctl.command.support.sub.stage.sub.SupportStageDeletePermissionCommand;
import com.omgservers.ctl.command.support.sub.stage.sub.SupportStageDeleteStageCommand;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "stage",
        description = "Manage project stages.",
        subcommands = {
                SupportStageCreateStageCommand.class,
                SupportStageCreateAliasCommand.class,
                SupportStageDeleteStageCommand.class,
                SupportStageCreatePermissionCommand.class,
                SupportStageDeletePermissionCommand.class,
        })
public class SupportStageCommand extends ParentCommand {
}
