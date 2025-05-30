package com.omgservers.ctl.command.support;

import com.omgservers.ctl.command.ParentCommand;
import com.omgservers.ctl.command.support.sub.developer.SupportDeveloperCommand;
import com.omgservers.ctl.command.support.sub.installation.SupportInstallationCommand;
import com.omgservers.ctl.command.support.sub.project.SupportProjectCommand;
import com.omgservers.ctl.command.support.sub.stage.SupportStageCommand;
import com.omgservers.ctl.command.support.sub.tenant.SupportTenantCommand;
import picocli.CommandLine;

@CommandLine.Command(
        name = "support",
        description = "Commands for support users.",
        subcommands = {
                SupportInstallationCommand.class,
                SupportDeveloperCommand.class,
                SupportTenantCommand.class,
                SupportProjectCommand.class,
                SupportStageCommand.class,
        })
public class SupportCommand extends ParentCommand {
}
