package com.omgservers.ctl.command.developer.sub.deployment;

import com.omgservers.ctl.command.ParentCommand;
import com.omgservers.ctl.command.developer.sub.deployment.sub.DeveloperDeploymentCreateDeploymentCommand;
import com.omgservers.ctl.command.developer.sub.deployment.sub.DeveloperDeploymentDeleteDeploymentCommand;
import com.omgservers.ctl.command.developer.sub.deployment.sub.DeveloperDeploymentGetDetailsCommand;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "deployment",
        description = "Developer commands for managing deployments.",
        subcommands = {
                DeveloperDeploymentCreateDeploymentCommand.class,
                DeveloperDeploymentGetDetailsCommand.class,
                DeveloperDeploymentDeleteDeploymentCommand.class,
        })
public class DeveloperDeploymentCommand extends ParentCommand {
}
