package com.omgservers.ctl.command.ctl.sub.installation;

import com.omgservers.ctl.command.ParentCommand;
import com.omgservers.ctl.command.ctl.sub.installation.sub.CtlInstallationUseCloudCommand;
import com.omgservers.ctl.command.ctl.sub.installation.sub.CtlInstallationUseCustomCommand;
import com.omgservers.ctl.command.ctl.sub.installation.sub.CtlInstallationUseLocalCommand;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "installation",
        description = "Set the installation to use.",
        subcommands = {
                CtlInstallationUseLocalCommand.class,
                CtlInstallationUseCloudCommand.class,
                CtlInstallationUseCustomCommand.class,
        })
public class CtlInstallationCommand extends ParentCommand {
}
