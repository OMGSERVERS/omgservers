package com.omgservers.ctl.command.developer.sub.installation;

import com.omgservers.ctl.command.ParentCommand;
import com.omgservers.ctl.command.developer.sub.installation.sub.DeveloperInstallationCreateTokenCommand;
import com.omgservers.ctl.command.developer.sub.installation.sub.DeveloperInstallationDeployVersionCommand;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "installation",
        description = "Developer commands for general tasks.",
        subcommands = {
                DeveloperInstallationCreateTokenCommand.class,
                DeveloperInstallationDeployVersionCommand.class,
        })
public class DeveloperInstallationCommand extends ParentCommand {
}
