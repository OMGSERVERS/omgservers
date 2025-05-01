package com.omgservers.ctl.command.developer.sub.project;

import com.omgservers.ctl.command.ParentCommand;
import com.omgservers.ctl.command.developer.sub.project.sub.DeveloperProjectCreateAliasCommand;
import com.omgservers.ctl.command.developer.sub.project.sub.DeveloperProjectCreateProjectCommand;
import com.omgservers.ctl.command.developer.sub.project.sub.DeveloperProjectDeleteProjectCommand;
import com.omgservers.ctl.command.developer.sub.project.sub.DeveloperProjectGetDetailsCommand;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "project",
        description = "Developer commands for managing projects.",
        subcommands = {
                DeveloperProjectCreateProjectCommand.class,
                DeveloperProjectCreateAliasCommand.class,
                DeveloperProjectGetDetailsCommand.class,
                DeveloperProjectDeleteProjectCommand.class,
        })
public class DeveloperProjectCommand extends ParentCommand {
}
