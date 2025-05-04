package com.omgservers.ctl.command.developer;

import com.omgservers.ctl.command.ParentCommand;
import com.omgservers.ctl.command.developer.sub.DeveloperCreateTokenCommand;
import com.omgservers.ctl.command.developer.sub.deployment.DeveloperDeploymentCommand;
import com.omgservers.ctl.command.developer.sub.local.DeveloperLocalCommand;
import com.omgservers.ctl.command.developer.sub.project.DeveloperProjectCommand;
import com.omgservers.ctl.command.developer.sub.stage.DeveloperStageCommand;
import com.omgservers.ctl.command.developer.sub.tenant.DeveloperTenantCommand;
import com.omgservers.ctl.command.developer.sub.version.DeveloperVersionCommand;
import picocli.CommandLine;

@CommandLine.Command(
        name = "developer",
        description = "Game developer commands.",
        subcommands = {
                DeveloperCreateTokenCommand.class,
                DeveloperLocalCommand.class,
                DeveloperTenantCommand.class,
                DeveloperProjectCommand.class,
                DeveloperStageCommand.class,
                DeveloperVersionCommand.class,
                DeveloperDeploymentCommand.class,
        })
public class DeveloperCommand extends ParentCommand {
}
