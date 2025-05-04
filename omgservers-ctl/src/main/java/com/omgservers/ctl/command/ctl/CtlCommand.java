package com.omgservers.ctl.command.ctl;

import com.omgservers.ctl.command.ParentCommand;
import com.omgservers.ctl.command.ctl.sub.CtlPurgeWalCommand;
import com.omgservers.ctl.command.ctl.sub.installation.CtlInstallationCommand;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "ctl",
        description = "Manage CTL.",
        subcommands = {
                CtlPurgeWalCommand.class,
                CtlInstallationCommand.class,
        })
public class CtlCommand extends ParentCommand {
}
