package com.omgservers.ctl.command.config;

import com.omgservers.ctl.command.ParentCommand;
import com.omgservers.ctl.command.config.sub.ConfigPurgeWalCommand;
import com.omgservers.ctl.command.config.sub.installation.ConfigInstallationCommand;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "config",
        description = "Commands for CTL configuration.",
        subcommands = {
                ConfigPurgeWalCommand.class,
                ConfigInstallationCommand.class,
        })
public class ConfigCommand extends ParentCommand {
}
