package com.omgservers.ctl.command.support.sub.project;

import com.omgservers.ctl.command.ParentCommand;
import com.omgservers.ctl.command.support.sub.project.sub.SupportProjectCreateAliasCommand;
import com.omgservers.ctl.command.support.sub.project.sub.SupportProjectCreatePermissionCommand;
import com.omgservers.ctl.command.support.sub.project.sub.SupportProjectCreateProjectCommand;
import com.omgservers.ctl.command.support.sub.project.sub.SupportProjectDeletePermissionCommand;
import com.omgservers.ctl.command.support.sub.project.sub.SupportProjectDeleteProjectCommand;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "project",
        description = "Support commands for managing projects.",
        subcommands = {
                SupportProjectCreateProjectCommand.class,
                SupportProjectCreateAliasCommand.class,
                SupportProjectDeleteProjectCommand.class,
                SupportProjectCreatePermissionCommand.class,
                SupportProjectDeletePermissionCommand.class,
        })
public class SupportProjectCommand extends ParentCommand {
}
