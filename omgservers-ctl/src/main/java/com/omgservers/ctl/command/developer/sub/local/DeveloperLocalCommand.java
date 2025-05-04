package com.omgservers.ctl.command.developer.sub.local;

import com.omgservers.ctl.command.ParentCommand;
import com.omgservers.ctl.command.developer.sub.local.sub.DeveloperLocalDeleteTenantCommand;
import com.omgservers.ctl.command.developer.sub.local.sub.DeveloperLocalDeployVersionCommand;
import com.omgservers.ctl.command.developer.sub.local.sub.DeveloperLocalInitTenantCommand;
import picocli.CommandLine;

@CommandLine.Command(
        name = "local",
        description = "Developer commands for local testing.",
        subcommands = {
                DeveloperLocalInitTenantCommand.class,
                DeveloperLocalDeployVersionCommand.class,
                DeveloperLocalDeleteTenantCommand.class,
        })
public class DeveloperLocalCommand extends ParentCommand {
}
