package com.omgservers.ctl.command.support.sub.installation;

import com.omgservers.ctl.command.ParentCommand;
import com.omgservers.ctl.command.support.sub.installation.sub.SupportInstallationCreateTokenCommand;
import com.omgservers.ctl.command.support.sub.installation.sub.SupportInstallationInitTenantCommand;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "installation",
        description = "Support commands for general tasks.",
        subcommands = {
                SupportInstallationCreateTokenCommand.class,
                SupportInstallationInitTenantCommand.class,
        })
public class SupportInstallationCommand extends ParentCommand {
}
