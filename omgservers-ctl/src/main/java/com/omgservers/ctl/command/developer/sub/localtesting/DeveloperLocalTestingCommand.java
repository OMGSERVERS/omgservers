package com.omgservers.ctl.command.developer.sub.localtesting;

import com.omgservers.ctl.command.ParentCommand;
import com.omgservers.ctl.command.developer.sub.localtesting.sub.DeveloperLocalTestingDeleteTenantCommand;
import com.omgservers.ctl.command.developer.sub.localtesting.sub.DeveloperLocalTestingDeployVersionCommand;
import com.omgservers.ctl.command.developer.sub.localtesting.sub.DeveloperLocalTestingInitTenantCommand;
import picocli.CommandLine;

@CommandLine.Command(
        name = "localtesting",
        description = "Developer commands for local testing.",
        subcommands = {
                DeveloperLocalTestingInitTenantCommand.class,
                DeveloperLocalTestingDeployVersionCommand.class,
                DeveloperLocalTestingDeleteTenantCommand.class,
        })
public class DeveloperLocalTestingCommand extends ParentCommand {
}
