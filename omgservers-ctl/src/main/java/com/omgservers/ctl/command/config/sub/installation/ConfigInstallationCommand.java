package com.omgservers.ctl.command.config.sub.installation;

import com.omgservers.ctl.command.ParentCommand;
import com.omgservers.ctl.command.config.sub.installation.sub.ConfigInstallationUseCloudCommand;
import com.omgservers.ctl.command.config.sub.installation.sub.ConfigInstallationUseCustomCommand;
import com.omgservers.ctl.command.config.sub.installation.sub.ConfigInstallationUseLocalCommand;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "installation",
        description = "Set the installation to use.",
        subcommands = {
                ConfigInstallationUseLocalCommand.class,
                ConfigInstallationUseCloudCommand.class,
                ConfigInstallationUseCustomCommand.class,
        })
public class ConfigInstallationCommand extends ParentCommand {
}
